package org.ht.profile.bizservice;

import lombok.extern.slf4j.Slf4j;
import org.ht.profile.data.model.Profile;
import org.ht.profile.data.service.BasicInfoDataService;
import org.ht.profile.data.service.ProfileDataService;
import org.ht.profile.dto.request.BasicInfoCreateRequest;
import org.ht.profile.dto.response.BasicInfoResponse;
import org.ht.profile.helper.ProfileConverterHelper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Component
@Slf4j
public class ProfileBizService {
    private final ProfileDataService profileDataService;
    private final BasicInfoDataService basicInfoDataService;
    private final ProfileConverterHelper profileConverterHelper;

    public ProfileBizService(ProfileDataService profileDataService,
                             BasicInfoDataService basicInfoDataService, ProfileConverterHelper profileConverterHelper) {
        this.profileDataService = profileDataService;
        this.basicInfoDataService = basicInfoDataService;
        this.profileConverterHelper = profileConverterHelper;
    }

    public BasicInfoResponse create(String htId, BasicInfoCreateRequest profileRequest) {
        // because we don't have api to create ht_id so I try to create ht_id for case creating basic info.
        // I think this business should be removed.
        // todo remove this business when we have api create ht id.
        Profile profile = profileDataService
                .findByHtId(htId)
                .orElseGet(() -> {
                    log.info("try to create profile when create basic info.");
                    return profileDataService.create(htId);
                });

        if (basicInfoDataService.existsByProfileId(profile.getId())) {
            String error = String.format("Basic info is already existed with %s", htId);
            throw new ResponseStatusException(HttpStatus.CONFLICT, error);
        }

        return Optional.of(profileConverterHelper.convert(profileRequest, profile.getId()))
                .map(basicInfoDataService::create)
                .map(basicInfo -> profileConverterHelper.convert(basicInfo, htId))
                .orElseThrow();
    }

    public BasicInfoResponse find(String htId) {

        Optional<Profile> profileOptional = profileDataService.findByHtId(htId);
        if (profileOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Profile is not existed with htId: %s", htId));
        }

        return profileOptional
                .flatMap(profile -> basicInfoDataService.findByProfileId(profile.getId()))
                .map(basicInfo -> profileConverterHelper.convert(basicInfo, htId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Basic Info is not existed."));
    }
}
