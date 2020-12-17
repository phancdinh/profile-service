package org.ht.id.profile.bizservice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.ht.id.common.constant.DemoGraphicsAttribute;
import org.ht.id.common.exception.DataConflictingException;
import org.ht.id.common.exception.DataNotExistingException;
import org.ht.id.profile.config.ProfileMgtMessageProperties;
import org.ht.id.profile.data.model.DemoGraphicsInfo;
import org.ht.id.profile.data.model.Profile;
import org.ht.id.profile.data.service.DemoGraphicsInfoDataService;
import org.ht.id.profile.data.service.ProfileDataService;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor
public class DemoGraphicsInfoBizService {
    private final DemoGraphicsInfoDataService demoGraphicsInfoDataService;
    private final ProfileDataService profileDataService;
    private final ProfileMgtMessageProperties profileMgtMessageProperties;

    public DemoGraphicsInfo create(String htId,
                                   DemoGraphicsInfo demoGraphicsInfo) throws DataNotExistingException, DataConflictingException {
        ObjectId htCode = profileDataService.findByHtId(htId)
                .map(Profile::getHtCode)
                .orElseThrow(() -> {
                    String error = profileMgtMessageProperties.getMessageWithArgs("validation.profile.isNotExisted", htId);
                    log.error(error);
                    return new DataNotExistingException(error);
                });
        DemoGraphicsAttribute demoGraphicsInfoAttribute = demoGraphicsInfo.getAttribute();
        boolean isExisting = demoGraphicsInfoDataService.existingDemoGraphicsInfo(htCode, demoGraphicsInfoAttribute);
        if (isExisting) {
            throw new DataConflictingException(profileMgtMessageProperties.getMessageWithArgs("validation.demographics.isExisted", demoGraphicsInfo.getAttribute().toString()));
        }
        return Optional.of(demoGraphicsInfo)
                .map(info -> {
                    info.setHtCode(htCode);
                    info.setAttribute(demoGraphicsInfoAttribute);
                    return demoGraphicsInfo;
                })
                .map(demoGraphicsInfoDataService::insert)
                .orElseThrow(() -> new DataNotExistingException(profileMgtMessageProperties.getMessageWithArgs("validation.demographics.isNotExisted", demoGraphicsInfoAttribute.toString())));
    }

    public DemoGraphicsInfo findByHtIdAndAttribute(String htId, DemoGraphicsAttribute demoGraphicsInfoAttribute) {
        Optional<Profile> profileOptional = profileDataService.findByHtId(htId);
        if (profileOptional.isEmpty()) {
            throw new DataNotExistingException(profileMgtMessageProperties.getMessageWithArgs("validation.profile.isNotExisted", htId));
        }
        return profileOptional
                .flatMap(existingProfile ->
                        demoGraphicsInfoDataService.findByHtIdAndAttribute(existingProfile.getHtCode(), demoGraphicsInfoAttribute))
                .orElseThrow(() -> new DataNotExistingException(profileMgtMessageProperties.getMessageWithArgs("validation.demographics.isNotExisted", demoGraphicsInfoAttribute.toString())));
    }

    public DemoGraphicsInfo update(String htId,
                                   DemoGraphicsInfo updateRequest) {

        Optional<Profile> profileOptional = profileDataService.findByHtId(htId);
        if (profileOptional.isEmpty()) {
            throw new DataNotExistingException(profileMgtMessageProperties.getMessageWithArgs("validation.profile.isNotExisted", htId));
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
                .orElseThrow(() -> new DataNotExistingException(
                        profileMgtMessageProperties.getMessageWithArgs("validation.demographics.isNotExistedOrUpdate", demoGraphicsInfoAttribute.toString())));
    }

    public void delete(String htId,
                       DemoGraphicsAttribute attribute) throws DataNotExistingException {

        Optional<Profile> profileOptional = profileDataService.findByHtId(htId);
        if (profileOptional.isEmpty()) {
            throw new DataNotExistingException(profileMgtMessageProperties.getMessageWithArgs("validation.profile.isNotExisted", htId));
        }

        profileOptional
                .flatMap(existingProfile ->
                        demoGraphicsInfoDataService.findByHtIdAndAttribute(existingProfile.getHtCode(), attribute))
                .ifPresent(demoGraphicsInfoDataService::delete);
    }

}
