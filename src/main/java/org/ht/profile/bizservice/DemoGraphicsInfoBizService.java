package org.ht.profile.bizservice;

import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.ht.profile.constants.DemoGraphicsInfoAttribute;
import org.ht.profile.data.model.Profile;
import org.ht.profile.data.service.DemoGraphicsInfoDataService;
import org.ht.profile.data.service.ProfileDataService;
import org.ht.profile.dto.request.DemoGraphicsInfoCreateRequest;
import org.ht.profile.dto.request.DemoGraphicsInfoUpdateRequest;
import org.ht.profile.dto.response.DemoGraphicsInfoResponse;
import org.ht.profile.helper.ProfileConverterHelper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static java.lang.String.format;

@Component
@Slf4j
public class DemoGraphicsInfoBizService {
    private final DemoGraphicsInfoDataService demoGraphicsInfoDataService;
    private final ProfileDataService profileDataService;

    public DemoGraphicsInfoBizService(DemoGraphicsInfoDataService demoGraphicsInfoDataService,
                                      ProfileDataService profileDataService) {
        this.demoGraphicsInfoDataService = demoGraphicsInfoDataService;
        this.profileDataService = profileDataService;
    }

    public DemoGraphicsInfoResponse create(String htId,
                                           DemoGraphicsInfoAttribute demoGraphicsInfoAttribute,
                                           DemoGraphicsInfoCreateRequest demoGraphicsInfoCreateRequest) {
        ObjectId profileId = profileDataService.findByHtId(htId)
                .map(Profile::getId)
                .orElseThrow(() -> {
                    String error = String.format("Profile is  not existed with htId: %s", htId);
                    log.error(error);
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, error);
                });
        boolean isExisting = demoGraphicsInfoDataService.existingDemoGraphicsInfo(profileId, demoGraphicsInfoAttribute);
        if (isExisting) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, format("Value %s is existed!", demoGraphicsInfoAttribute.toString()));
        }

        return Optional.of(demoGraphicsInfoCreateRequest)
                .map(ProfileConverterHelper::convert)
                .map(demoGraphicsInfo -> {
                    demoGraphicsInfo.setProfileId(profileId);
                    demoGraphicsInfo.setAttribute(demoGraphicsInfoAttribute);
                    return demoGraphicsInfo;
                })
                .map(demoGraphicsInfoDataService::insert)
                .map(ProfileConverterHelper::convert)
                .map(response -> {
                    response.setHtId(htId);
                    return response;
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not existed"));
    }

    public DemoGraphicsInfoResponse findByHtIdAndAttribute(String htId,
                                                           DemoGraphicsInfoAttribute demoGraphicsInfoAttribute) {

        return profileDataService.findByHtId(htId)
                .flatMap(existingProfile ->
                        demoGraphicsInfoDataService.findByHtIdAndAttribute(existingProfile.getId(), demoGraphicsInfoAttribute))
                .map(ProfileConverterHelper::convert)
                .map(response -> {
                    response.setHtId(htId);
                    return response;
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not existed"));
    }

    public DemoGraphicsInfoResponse update(String htId,
                                           DemoGraphicsInfoAttribute demoGraphicsInfoAttribute,
                                           DemoGraphicsInfoUpdateRequest updateRequest) {
        return profileDataService.findByHtId(htId)
                .flatMap(existingProfile ->
                        demoGraphicsInfoDataService.findByHtIdAndAttribute(existingProfile.getId(), demoGraphicsInfoAttribute))
                .map(demoGraphicsInfo -> {
                    demoGraphicsInfo.setValue(updateRequest.getValue());
                    return demoGraphicsInfo;
                })
                .map(demoGraphicsInfoDataService::save)
                .map(ProfileConverterHelper::convert)
                .map(response -> {
                    response.setHtId(htId);
                    return response;
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        format("Value %s is not existed for Update!", demoGraphicsInfoAttribute.toString())));
    }

    public void delete(String htId,
                       DemoGraphicsInfoAttribute demoGraphicsInfoAttribute) {
        profileDataService.findByHtId(htId)
                .flatMap(existingProfile ->
                        demoGraphicsInfoDataService.findByHtIdAndAttribute(existingProfile.getId(), demoGraphicsInfoAttribute))
                .ifPresent(demoGraphicsInfoDataService::delete);
    }
}
