package org.ht.profile.bizservice;

import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.ht.profile.constants.DemoGraphicsInfoAttribute;
import org.ht.profile.data.exception.DataConflictingException;
import org.ht.profile.data.exception.DataNotExistingException;
import org.ht.profile.data.model.Profile;
import org.ht.profile.data.service.DemoGraphicsInfoDataService;
import org.ht.profile.data.service.ProfileDataService;
import org.ht.profile.dto.DemoGraphicsInfoDto;
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
    private final ProfileConverterHelper profileConverterHelper;


    public DemoGraphicsInfoBizService(DemoGraphicsInfoDataService demoGraphicsInfoDataService,
                                      ProfileDataService profileDataService, ProfileConverterHelper profileConverterHelper) {
        this.demoGraphicsInfoDataService = demoGraphicsInfoDataService;
        this.profileDataService = profileDataService;
        this.profileConverterHelper = profileConverterHelper;
    }

    public DemoGraphicsInfoDto create(String htId,
                                      DemoGraphicsInfoDto demoGraphicsInfoDto) throws DataNotExistingException, DataConflictingException {
        ObjectId profileId = profileDataService.findByHtId(htId)
                .map(Profile::getId)
                .orElseThrow(() -> {
                    String error = String.format("Profile is  not existed with htId: %s", htId);
                    log.error(error);
                    return new DataNotExistingException(error);
                });
        DemoGraphicsInfoAttribute demoGraphicsInfoAttribute = convertDemoGraphicsAttribute(demoGraphicsInfoDto.getAttribute());
        boolean isExisting = demoGraphicsInfoDataService.existingDemoGraphicsInfo(profileId, demoGraphicsInfoAttribute);
        if (isExisting) {
            throw new DataConflictingException(format("Value %s is existed!", demoGraphicsInfoDto.getAttribute()));
        }
        return Optional.of(demoGraphicsInfoDto)
                .map(profileConverterHelper::convert)
                .map(demoGraphicsInfo -> {
                    demoGraphicsInfo.setProfileId(profileId);
                    demoGraphicsInfo.setAttribute(demoGraphicsInfoAttribute);
                    return demoGraphicsInfo;
                })
                .map(demoGraphicsInfoDataService::insert)
                .map(demoGraphicsInfo -> profileConverterHelper.convert(demoGraphicsInfo, htId))
                .orElseThrow(() -> new DataNotExistingException(String.format("%s is not existed.", demoGraphicsInfoAttribute)));
    }

    public DemoGraphicsInfoDto findByHtIdAndAttribute(String htId, String attribute) throws DataNotExistingException {
        Optional<Profile> profileOptional = profileDataService.findByHtId(htId);
        if (profileOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Profile is not existed with htId: %s", htId));
        }
        DemoGraphicsInfoAttribute demoGraphicsInfoAttribute = convertDemoGraphicsAttribute(attribute);
        return profileOptional
                .flatMap(existingProfile ->
                        demoGraphicsInfoDataService.findByHtIdAndAttribute(existingProfile.getId(), demoGraphicsInfoAttribute))
                .map(demoGraphicsInfo -> profileConverterHelper.convert(demoGraphicsInfo, htId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("%s is not existed.", demoGraphicsInfoAttribute)));
    }

    public DemoGraphicsInfoDto update(String htId,
                                      DemoGraphicsInfoDto updateRequest) throws DataNotExistingException {

        Optional<Profile> profileOptional = profileDataService.findByHtId(htId);
        if (profileOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Profile is not existed with htId: %s", htId));
        }
        DemoGraphicsInfoAttribute demoGraphicsInfoAttribute = convertDemoGraphicsAttribute(updateRequest.getAttribute());

        return profileOptional
                .flatMap(profile ->
                        demoGraphicsInfoDataService.findByHtIdAndAttribute(profile.getId(), demoGraphicsInfoAttribute))
                .map(demoGraphicsInfo -> {
                    demoGraphicsInfo.setValue(updateRequest.getValue());
                    return demoGraphicsInfo;
                })
                .map(demoGraphicsInfoDataService::save)
                .map(demoGraphicsInfo -> profileConverterHelper.convert(demoGraphicsInfo, htId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        format("Value %s is not existed for Update!", demoGraphicsInfoAttribute.toString())));
    }

    public void delete(String htId,
                       String attribute) throws DataNotExistingException {

        Optional<Profile> profileOptional = profileDataService.findByHtId(htId);
        if (profileOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Profile is not existed with htId: %s", htId));
        }
        DemoGraphicsInfoAttribute demoGraphicsInfoAttribute = convertDemoGraphicsAttribute(attribute);

        profileOptional
                .flatMap(existingProfile ->
                        demoGraphicsInfoDataService.findByHtIdAndAttribute(existingProfile.getId(), demoGraphicsInfoAttribute))
                .ifPresent(demoGraphicsInfoDataService::delete);
    }


    public DemoGraphicsInfoAttribute convertDemoGraphicsAttribute(String attribute) throws DataNotExistingException {
        try {
            return DemoGraphicsInfoAttribute.valueOf(attribute);
        } catch (IllegalArgumentException e) {
            throw new DataNotExistingException(String.format("%s info does not exists", attribute));
        }
    }
}
