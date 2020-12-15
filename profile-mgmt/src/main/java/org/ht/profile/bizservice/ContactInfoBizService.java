package org.ht.profile.bizservice;

import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.ht.common.constant.UserStatus;
import org.ht.profile.data.exception.DataConflictingException;
import org.ht.profile.data.exception.DataNotExistingException;
import org.ht.profile.data.model.ContactInfo;
import org.ht.profile.data.model.Profile;
import org.ht.profile.data.model.internal.HierarchyContact;
import org.ht.profile.data.service.ContactInfoDataService;
import org.ht.profile.data.service.ProfileDataService;
import org.ht.profile.helper.ProfileConverterHelper;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Slf4j
public class ContactInfoBizService {

    private final ProfileDataService profileDataService;
    private final ContactInfoDataService contactInfoDataService;
    private final ProfileConverterHelper profileConverterHelper;

    public ContactInfoBizService(ProfileDataService profileDataService, ContactInfoDataService contactInfoDataService, ProfileConverterHelper profileConverterHelper) {
        this.profileDataService = profileDataService;
        this.contactInfoDataService = contactInfoDataService;
        this.profileConverterHelper = profileConverterHelper;
    }

    public ContactInfo create(String htId, ContactInfo contactInfo) throws DataConflictingException, DataNotExistingException {
        ObjectId htCode = profileDataService.findByHtId(htId)
                .map(Profile::getHtCode)
                .orElseThrow(() -> {
                    String error = String.format("Profile is not existed with htId: %s", htId);
                    log.error(error);
                    return new DataNotExistingException(error);
                });

        if (contactInfoDataService.existsByHtCode(htCode)) {
            throw new DataConflictingException(String.format("Contact info is already existed with %s", htId));
        }
        return Optional.of(profileConverterHelper.convert(contactInfo, htCode))
                .map(contactInfoDataService::create)
                .orElseThrow();
    }

    public ContactInfo findByHtId(String htId) throws DataNotExistingException {

        Profile profile = profileDataService.findByHtId(htId).orElseThrow(() -> {
            throw new DataNotExistingException(String.format("Profile is not existed with htId: %s", htId));
        });

        return findByHtCode(profile.getHtCode());
    }

    private ContactInfo findByHtCode(ObjectId htCode) {
        return Optional.of(htCode)
                .flatMap(contactInfoDataService::findByHtCode)
                .orElseThrow(() -> new DataNotExistingException(String.format("Contact info is not existed htCode: %s", htCode)));
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
            log.error(String.format("Email %s has already existed in the current contact htCode=%s", email, htCode));
            throw new DataConflictingException("Email has already existed");
        }
        return contactInfoDataService.createContactEmail(htCode, email).orElseThrow();
    }
}
