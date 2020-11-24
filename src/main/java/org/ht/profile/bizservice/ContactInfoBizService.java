package org.ht.profile.bizservice;

import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.ht.profile.data.model.Profile;
import org.ht.profile.data.service.ProfileDataService;
import org.ht.profile.dto.request.ContactInfoCreateRequest;
import org.ht.profile.dto.response.ContactInfoResponse;
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


    public ContactInfoBizService(ProfileDataService profileDataService, ContactInfoDataService contactInfoDataService) {
        this.profileDataService = profileDataService;
        this.contactInfoDataService = contactInfoDataService;
    }

    public ContactInfoResponse create(String htId, ContactInfoCreateRequest contactInfoCreateRequest) {
        ObjectId profileId = profileDataService.findByHtId(htId)
                .map(Profile::getId)
                .orElseThrow(() -> {
                    String error = String.format("Profile is not existed with htId: %s", htId);
                    log.error(error);
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, error);
                });

        if (contactInfoDataService.existsByProfileId(profileId)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, String.format("Contact info is already existed with %s", htId));
        }

        return Optional.of(ProfileConverterHelper.convert(contactInfoCreateRequest, profileId))
                .map(contactInfoDataService::create)
                .map(contactInfo -> ProfileConverterHelper.convert(contactInfo, htId))
                .orElseThrow();
    }

    public ContactInfoResponse findByHtId(String htId) {

        Optional<Profile> profileOptional = profileDataService.findByHtId(htId);
        if (profileOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Profile is not existed with htId: %s", htId));
        }

        return profileOptional
                .flatMap(existingProfile ->
                        contactInfoDataService.findByProfileId(existingProfile.getId()))
                .map(contactInfo -> ProfileConverterHelper.convert(contactInfo, htId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer Info is not existed."));
    }
}
