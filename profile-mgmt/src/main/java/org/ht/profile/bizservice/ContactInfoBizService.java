package org.ht.profile.bizservice;

import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.ht.profile.data.exception.DataConflictingException;
import org.ht.profile.data.exception.DataNotExistingException;
import org.ht.profile.data.model.ContactInfo;
import org.ht.profile.data.model.Profile;
import org.ht.profile.data.service.ProfileDataService;
import org.ht.profile.helper.ProfileConverterHelper;
import org.ht.profile.data.service.ContactInfoDataService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

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

        Optional<Profile> profileOptional = profileDataService.findByHtId(htId);
        if (profileOptional.isEmpty()) {
            throw new DataNotExistingException(String.format("Profile is not existed with htId: %s", htId));
        }

        return profileOptional
                .flatMap(existingProfile ->
                        contactInfoDataService.findByHtCode(existingProfile.getHtCode()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer Info is not existed."));
    }
}
