package org.ht.profile.bizservice;

import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.ht.profile.data.exception.DataConflictingException;
import org.ht.profile.data.exception.DataNotExistingException;
import org.ht.profile.data.model.LegalInfo;
import org.ht.profile.data.model.Profile;
import org.ht.profile.data.service.LegalInfoDataService;
import org.ht.profile.data.service.ProfileDataService;
import org.ht.profile.helper.ProfileConverterHelper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Component
@Slf4j
public class LegalInfoBizService {
    private final ProfileDataService profileDataService;
    private final LegalInfoDataService legalInfoDataService;
    private final ProfileConverterHelper profileConverterHelper;

    public LegalInfoBizService(ProfileDataService profileDataService, LegalInfoDataService legalInfoDataService, ProfileConverterHelper profileConverterHelper) {
        this.profileDataService = profileDataService;
        this.legalInfoDataService = legalInfoDataService;
        this.profileConverterHelper = profileConverterHelper;
    }

    public LegalInfo create(String htId, LegalInfo createdInfo) throws DataNotExistingException, DataConflictingException {
        ObjectId htCode = profileDataService.findByHtId(htId)
                .map(Profile::getHtCode)
                .orElseThrow(() -> {
                    String error = String.format("Profile is not existed with htId: %s", htId);
                    log.error(error);
                    return new DataNotExistingException(error);
                });

        if (legalInfoDataService.existsByHtCode(htCode)) {
            throw new DataConflictingException(String.format("Legal info is already existed with %s", htId));
        }
        return Optional.of(profileConverterHelper.convert(createdInfo, htCode))
                .map(legalInfoDataService::create)
                .orElseThrow();
    }

    public LegalInfo findByHtId(String htId) throws DataNotExistingException {
        Optional<Profile> profileOptional = profileDataService.findByHtId(htId);
        if (profileOptional.isEmpty()) {
            throw new DataNotExistingException(String.format("Profile is not existed with htId: %s", htId));
        }

        return profileOptional
                .flatMap(existingProfile -> legalInfoDataService.findByHtCode(existingProfile.getHtCode()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Legal Info is not existed."));
    }
}
