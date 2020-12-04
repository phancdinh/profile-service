package org.ht.profile.bizservice;

import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.ht.common.constant.DemoGraphicsAttribute;
import org.ht.profile.data.exception.DataConflictingException;
import org.ht.profile.data.exception.DataNotExistingException;
import org.ht.profile.data.model.DemoGraphicsInfo;
import org.ht.profile.data.model.Profile;
import org.ht.profile.data.service.DemoGraphicsInfoDataService;
import org.ht.profile.data.service.ProfileDataService;
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
                                      ProfileDataService profileDataService, ProfileConverterHelper profileConverterHelper) {
        this.demoGraphicsInfoDataService = demoGraphicsInfoDataService;
        this.profileDataService = profileDataService;
    }

    public DemoGraphicsInfo create(String htId,
                                   DemoGraphicsInfo demoGraphicsInfo) throws DataNotExistingException, DataConflictingException {
        ObjectId htCode = profileDataService.findByHtId(htId)
                .map(Profile::getHtCode)
                .orElseThrow(() -> {
                    String error = String.format("Profile is  not existed with htId: %s", htId);
                    log.error(error);
                    return new DataNotExistingException(error);
                });
        DemoGraphicsAttribute demoGraphicsInfoAttribute = demoGraphicsInfo.getAttribute();
        boolean isExisting = demoGraphicsInfoDataService.existingDemoGraphicsInfo(htCode, demoGraphicsInfoAttribute);
        if (isExisting) {
            throw new DataConflictingException(format("Value %s is existed!", demoGraphicsInfo.getAttribute()));
        }
        return Optional.of(demoGraphicsInfo)
                .map(info -> {
                    info.setHtCode(htCode);
                    info.setAttribute(demoGraphicsInfoAttribute);
                    return demoGraphicsInfo;
                })
                .map(demoGraphicsInfoDataService::insert)
                .orElseThrow(() -> new DataNotExistingException(String.format("%s is not existed.", demoGraphicsInfoAttribute)));
    }

    public DemoGraphicsInfo findByHtIdAndAttribute(String htId, DemoGraphicsAttribute demoGraphicsInfoAttribute) {
        Optional<Profile> profileOptional = profileDataService.findByHtId(htId);
        if (profileOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Profile is not existed with htId: %s", htId));
        }
        return profileOptional
                .flatMap(existingProfile ->
                        demoGraphicsInfoDataService.findByHtIdAndAttribute(existingProfile.getHtCode(), demoGraphicsInfoAttribute))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("%s is not existed.", demoGraphicsInfoAttribute)));
    }

    public DemoGraphicsInfo update(String htId,
                                      DemoGraphicsInfo updateRequest) {

        Optional<Profile> profileOptional = profileDataService.findByHtId(htId);
        if (profileOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Profile is not existed with htId: %s", htId));
        }
        DemoGraphicsAttribute demoGraphicsInfoAttribute = updateRequest.getAttribute();
        return profileOptional
                .flatMap(profile ->
                        demoGraphicsInfoDataService.findByHtIdAndAttribute(profile.getHtCode(), demoGraphicsInfoAttribute))
                .map(demoGraphicsInfo -> {
                    demoGraphicsInfo.setValue(updateRequest.getValue());
                    return demoGraphicsInfo;
                })
                .map(demoGraphicsInfoDataService::save)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        format("Value %s is not existed for Update!", demoGraphicsInfoAttribute.toString())));
    }

    public void delete(String htId,
                       DemoGraphicsAttribute attribute) throws DataNotExistingException {

        Optional<Profile> profileOptional = profileDataService.findByHtId(htId);
        if (profileOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Profile is not existed with htId: %s", htId));
        }

        profileOptional
                .flatMap(existingProfile ->
                        demoGraphicsInfoDataService.findByHtIdAndAttribute(existingProfile.getHtCode(), attribute))
                .ifPresent(demoGraphicsInfoDataService::delete);
    }

}
