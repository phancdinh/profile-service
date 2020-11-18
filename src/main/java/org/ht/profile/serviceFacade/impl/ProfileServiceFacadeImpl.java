package org.ht.profile.serviceFacade.impl;

import lombok.extern.slf4j.Slf4j;
import org.ht.profile.helper.ProfileConverterHelper;
import org.ht.profile.dto.response.BasicInfoResponse;
import org.ht.profile.dto.request.BasicInfoCreateRequest;
import org.ht.profile.model.BasicInfo;
import org.ht.profile.model.Profile;
import org.ht.profile.service.BasicInfoService;
import org.ht.profile.service.ProfileService;
import org.ht.profile.serviceFacade.ProfileServiceFacade;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
@Slf4j
public class ProfileServiceFacadeImpl implements ProfileServiceFacade {

    private final ProfileService profileService;
    private final BasicInfoService basicInfoService;

    public ProfileServiceFacadeImpl(ProfileService profileService, BasicInfoService basicInfoService) {
        this.profileService = profileService;
        this.basicInfoService = basicInfoService;
    }

    @Override
    public BasicInfoResponse create(BasicInfoCreateRequest profileRequest) {
        String htId = profileRequest.getHtId();
        Profile profile;
        // because we don't have api to create ht_id so I try to create ht_id for case creating basic info.
        // I think this business should be removed.
        // todo remove this business when we have api create ht id.
        try {
            profile = profileService.findByHtId(htId);
        } catch (ResponseStatusException e) {
            log.info("try to create profile when create basic info.");
            profile = profileService.create(htId);
        }
        BasicInfo basicInfo = basicInfoService.create(profile.getId(), profileRequest);
        BasicInfoResponse basicInfoResponse = ProfileConverterHelper.convert(basicInfo);
        basicInfoResponse.setHtId(htId);
        return basicInfoResponse;
    }

    @Override
    public BasicInfoResponse find(String htId) {
        Profile profile = profileService.findByHtId(htId);
        BasicInfo basicInfo = basicInfoService.findByProfileId(profile.getId());
        BasicInfoResponse response = ProfileConverterHelper.convert(basicInfo);
        response.setHtId(htId);
        return response;
    }
}
