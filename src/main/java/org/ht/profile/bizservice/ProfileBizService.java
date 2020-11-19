package org.ht.profile.bizservice;

import lombok.extern.slf4j.Slf4j;
import org.ht.profile.data.model.BasicInfo;
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

    public ProfileBizService(ProfileDataService profileDataService,
                             BasicInfoDataService basicInfoDataService) {
        this.profileDataService = profileDataService;
        this.basicInfoDataService = basicInfoDataService;
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

        return Optional.of(ProfileConverterHelper.convert(profileRequest, profile.getId()))
                .map(basicInfoDataService::create)
                .map(basicInfo -> new BasicInfoResponse(basicInfo, htId))
                .orElseThrow();
    }

    public BasicInfoResponse find(String htId) {
        return profileDataService.findByHtId(htId)
                .flatMap(profile -> basicInfoDataService.findByProfileId(profile.getId()))
                .map(basicInfo -> new BasicInfoResponse(basicInfo, htId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Profile is not existed."));
    }
}
