package org.ht.id.profile.bizservice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.ht.id.common.constant.UserStatus;
import org.ht.id.common.exception.DataConflictingException;
import org.ht.id.common.exception.DataNotExistingException;
import org.ht.id.profile.config.ProfileMgtMessageProperties;
import org.ht.id.profile.helper.ProfileConverterHelper;
import org.ht.id.profile.data.model.ContactInfo;
import org.ht.id.profile.data.model.Profile;
import org.ht.id.profile.data.model.internal.HierarchyContact;
import org.ht.id.profile.data.service.ContactInfoDataService;
import org.ht.id.profile.data.service.ProfileDataService;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Slf4j
@RequiredArgsConstructor
public class ContactInfoBizService {

    private final ProfileDataService profileDataService;
    private final ContactInfoDataService contactInfoDataService;
    private final ProfileConverterHelper profileConverterHelper;
    private final ProfileMgtMessageProperties profileMgtMessageProperties;

    public ContactInfo create(String htId, ContactInfo contactInfo) throws DataConflictingException, DataNotExistingException {
        ObjectId htCode = profileDataService.findByHtId(htId)
                .map(Profile::getHtCode)
                .orElseThrow(() -> {
                    String error = profileMgtMessageProperties.getMessageWithArgs("validation.profile.isNotExisted", htId);
                    log.error(error);
                    return new DataNotExistingException(error);
                });

        if (contactInfoDataService.existsByHtCode(htCode)) {
            throw new DataConflictingException(profileMgtMessageProperties.getMessageWithArgs("validation.contact.existed", htId));
        }
        return Optional.of(profileConverterHelper.convert(contactInfo, htCode))
                .map(contactInfoDataService::create)
                .orElseThrow();
    }

    public ContactInfo findByHtId(String htId) throws DataNotExistingException {

        Profile profile = profileDataService.findByHtId(htId).orElseThrow(() -> {
            throw new DataNotExistingException(profileMgtMessageProperties.getMessageWithArgs("validation.profile.isNotExisted", htId));
        });

        return findByHtCode(profile.getHtCode());
    }

    private ContactInfo findByHtCode(ObjectId htCode) {
        return Optional.of(htCode)
                .flatMap(contactInfoDataService::findByHtCode)
                .orElseThrow(() -> new DataNotExistingException(profileMgtMessageProperties.getMessageWithArgs("validation.contact.isNotExist", htCode.toString())));
    }

    public void updatePrimaryEmail(ObjectId htCode, String email) {
        contactInfoDataService.updatePrimaryEmail(htCode, email);
    }

    public boolean existByEmailAndStatusActive(String email) {
        List<ContactInfo> listContactInfo = contactInfoDataService.findByEmailAndPrimary(email);

        if (listContactInfo.isEmpty()) {
            return false;
        }

        List<ObjectId> listHtCodes = listContactInfo.stream().map(ContactInfo::getHtCode).collect(Collectors.toList());
        return profileDataService.existsByHtCodesAndStatus(listHtCodes, UserStatus.ACTIVE);
    }

    public HierarchyContact createContactEmail(ObjectId htCode, String email) {

        ContactInfo contactInfo = findByHtCode(htCode);
        if (contactInfo.getEmails().stream().anyMatch(e -> e.getValue().equalsIgnoreCase(email))) {
            log.error(profileMgtMessageProperties.getMessageWithArgs("validation.contact.existed", htCode.toString()));
            throw new DataConflictingException(profileMgtMessageProperties.getMessage("validation.contact.emailRegistered"));
        }
        return contactInfoDataService.createContactEmail(htCode, email).orElseThrow();
    }
}
