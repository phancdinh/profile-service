package org.ht.id.profile.bizservice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.ht.id.common.exception.DataConflictingException;
import org.ht.id.common.exception.DataNotExistingException;
import org.ht.id.common.exception.WrongInputException;
import org.ht.id.common.constant.UserStatus;
import org.ht.id.profile.config.ProfileMgtMessageProperties;
import org.ht.id.profile.data.model.BasicInfo;
import org.ht.id.profile.data.model.Profile;
import org.ht.id.profile.data.service.BasicInfoDataService;
import org.ht.id.profile.data.service.ContactInfoDataService;
import org.ht.id.profile.data.service.ProfileDataService;
import org.ht.id.profile.helper.ProfileConverterHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProfileBizService {
    private final ProfileDataService profileDataService;
    private final BasicInfoDataService basicInfoDataService;
    private final ProfileConverterHelper profileConverterHelper;
    private final ContactInfoDataService contactInfoDataService;
    private final ProfileMgtMessageProperties profileMgtMessageProperties;

    public BasicInfo createBasicInfo(String htId, BasicInfo basicInfo) throws DataConflictingException {
        ObjectId htCode = profileDataService.findByHtId(htId)
                .map(Profile::getHtCode)
                .orElseThrow(() -> {
                    String error = profileMgtMessageProperties.getMessageWithArgs("validation.profile.isNotExisted", htId);
                    log.error(error);
                    return new DataNotExistingException(error);
                });

        if (basicInfoDataService.existsByHtCode(htCode)) {
            String error = profileMgtMessageProperties.getMessageWithArgs("validation.basicInfo.isExisted", htId);
            throw new DataConflictingException(error);
        }

        return Optional.of(profileConverterHelper.convert(basicInfo, htCode))
                .map(basicInfoDataService::create)
                .orElseThrow();
    }

    public BasicInfo findBasicInfo(String htId) throws DataNotExistingException {
        Optional<Profile> profileOptional = profileDataService.findByHtId(htId);
        if (profileOptional.isEmpty()) {
            throw new DataNotExistingException(profileMgtMessageProperties.getMessageWithArgs("validation.profile.isNotExisted", htId));
        }

        return profileOptional
                .flatMap(profile -> basicInfoDataService.findByHtCode(profile.getHtCode()))
                .orElseThrow(() -> new DataNotExistingException(profileMgtMessageProperties.getMessage("validation.basicInfo.isNotExisted")));
    }

    public Profile findProfile(String htId) throws DataNotExistingException {
        Optional<Profile> profileOptional = profileDataService.findByHtId(htId);
        if (profileOptional.isEmpty()) {
            throw new DataNotExistingException(profileMgtMessageProperties.getMessageWithArgs("validation.profile.isNotExisted", htId));
        }
        return profileOptional.get();
    }

    public boolean existsByHtId(String htId) {
        return profileDataService.existsByHtId(htId);
    }

    public Profile create(String htId, String leadSource) throws DataConflictingException {
        return create(htId, leadSource, null, null, null);
    }

    public Profile create(String htId, String leadSource, String primaryEmail, String primaryPhone, String password) {
        if (profileDataService.existsByHtId(htId)) {
            throw new DataConflictingException(profileMgtMessageProperties.getMessageWithArgs("validation.htId.isExisted", htId));
        }

        if (StringUtils.isEmpty(primaryEmail) && StringUtils.isEmpty(primaryPhone)) {
            throw new WrongInputException("Register primary email or primary phone must be entered");
        }

        return Optional.of(htId)
                .map(id -> profileDataService.create(id, leadSource, primaryEmail, primaryPhone, password))
                .map(t -> {
                    contactInfoDataService.create(t.getHtCode(), primaryEmail, primaryPhone);
                    return t;
                })
                .orElseThrow();
    }

    public void deleteProfile(String htId) {
        if (!profileDataService.existsByHtId(htId)) {
            throw new DataNotExistingException(profileMgtMessageProperties.getMessageWithArgs("validation.profile.isNotExisted", htId));
        }
        profileDataService.deleteProfileByHtId(htId);
    }

    public Profile updateStatus(String htId, UserStatus status) {
        return updateStatus(htId, status, null, null, null);
    }

    public Profile updateStatus(String htId, UserStatus status, String email, String phone, String password) {
        return profileDataService.updateStatus(htId, status, email, phone, password);
    }
}
