package org.ht.profile.serviceFacade.impl;

import org.bson.types.ObjectId;
import org.ht.profile.dto.response.DemoGraphicsInfoResponse;
import org.ht.profile.dto.request.DemoGraphicsInfoCreateRequest;
import org.ht.profile.dto.request.DemoGraphicsInfoUpdateRequest;
import org.ht.profile.helper.ProfileConverterHelper;
import org.ht.profile.model.DemoGraphicsInfo;
import org.ht.profile.constants.DemoGraphicsInfoAttribute;
import org.ht.profile.model.Profile;
import org.ht.profile.service.DemoGraphicsInfoService;
import org.ht.profile.service.ProfileService;
import org.ht.profile.serviceFacade.DemoGraphicsInfoServiceFacade;
import org.springframework.stereotype.Component;

@Component
public class DemoGraphicsInfoServiceFacadeImpl implements DemoGraphicsInfoServiceFacade {
    private final DemoGraphicsInfoService demoGraphicsInfoService;
    private final ProfileService profileService;

    public DemoGraphicsInfoServiceFacadeImpl(DemoGraphicsInfoService demoGraphicsInfoService, ProfileService profileService) {
        this.demoGraphicsInfoService = demoGraphicsInfoService;
        this.profileService = profileService;
    }

    @Override
    public DemoGraphicsInfoResponse create(String htId, DemoGraphicsInfoAttribute demoGraphicsInfoAttribute, DemoGraphicsInfoCreateRequest demoGraphicsInfoCreateRequest) {
        Profile existingProfile = profileService.findByHtId(htId);
        ObjectId profileId = existingProfile.getId();

        DemoGraphicsInfo data = demoGraphicsInfoService.create(profileId, demoGraphicsInfoAttribute, demoGraphicsInfoCreateRequest);
        DemoGraphicsInfoResponse dataResponse = ProfileConverterHelper.convert(data);
        dataResponse.setHtId(htId);

        return dataResponse;
    }

    @Override
    public DemoGraphicsInfoResponse findByHtIdAndAttribute(String htId, DemoGraphicsInfoAttribute demoGraphicsInfoAttribute) {
        Profile existingProfile = profileService.findByHtId(htId);
        ObjectId profileId = existingProfile.getId();

        DemoGraphicsInfo data = demoGraphicsInfoService.findByHtIdAndAttribute(profileId, demoGraphicsInfoAttribute);
        DemoGraphicsInfoResponse dataResponse = ProfileConverterHelper.convert(data);
        dataResponse.setHtId(htId);

        return dataResponse;
    }

    @Override
    public DemoGraphicsInfoResponse update(String htId, DemoGraphicsInfoAttribute demoGraphicsInfoAttribute, DemoGraphicsInfoUpdateRequest updateRequest) {
        Profile existingProfile = profileService.findByHtId(htId);
        ObjectId profileId = existingProfile.getId();

        DemoGraphicsInfo data = demoGraphicsInfoService.update(profileId, demoGraphicsInfoAttribute, updateRequest);
        DemoGraphicsInfoResponse dataResponse = ProfileConverterHelper.convert(data);
        dataResponse.setHtId(htId);

        return dataResponse;
    }

    @Override
    public void delete(String htId, DemoGraphicsInfoAttribute demoGraphicsInfoAttribute) {
        Profile existingProfile = profileService.findByHtId(htId);
        ObjectId profileId = existingProfile.getId();
        demoGraphicsInfoService.delete(profileId, demoGraphicsInfoAttribute);
    }
}
