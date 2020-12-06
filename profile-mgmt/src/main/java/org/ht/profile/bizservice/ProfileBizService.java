package org.ht.profile.bizservice;

import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.ht.profile.data.exception.DataConflictingException;
import org.ht.profile.data.exception.DataNotExistingException;
import org.ht.profile.data.model.BasicInfo;
import org.ht.profile.data.model.Profile;
import org.ht.profile.data.service.BasicInfoDataService;
import org.ht.profile.data.service.ContactInfoDataService;
import org.ht.profile.data.service.ProfileDataService;
import org.ht.profile.helper.ProfileConverterHelper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class ProfileBizService {
    private final ProfileDataService profileDataService;
    private final BasicInfoDataService basicInfoDataService;
    private final ProfileConverterHelper profileConverterHelper;
    private final ContactInfoDataService contactInfoDataService;

    public ProfileBizService(ProfileDataService profileDataService,
                             BasicInfoDataService basicInfoDataService,
                             ProfileConverterHelper profileConverterHelper,
                             ContactInfoDataService contactInfoDataService) {
        this.profileDataService = profileDataService;
        this.basicInfoDataService = basicInfoDataService;
        this.profileConverterHelper = profileConverterHelper;
        this.contactInfoDataService = contactInfoDataService;
    }

    public Profile create(String htId, String primaryEmail, String primaryPhone, String leadSource) {

        Profile createdProfile = profileDataService
                .findByHtId(htId)
                .orElseGet(() -> profileDataService.create(htId, leadSource));

        contactInfoDataService.create(createdProfile.getHtCode(), primaryEmail, primaryPhone);
        return createdProfile;
    }

    public BasicInfo create(String htId, BasicInfo basicInfo) throws DataConflictingException {
        ObjectId htCode = profileDataService.findByHtId(htId)
                .map(Profile::getHtCode)
                .orElseThrow(() -> {
                    String error = String.format("Profile is not existed with htId: %s", htId);
                    log.error(error);
                    return new DataNotExistingException(error);
                });

        if (basicInfoDataService.existsByHtCode(htCode)) {
            String error = String.format("Basic info is already existed with %s", htId);
            throw new DataConflictingException(error);
        }

        return Optional.of(profileConverterHelper.convert(basicInfo, htCode))
                .map(basicInfoDataService::create)
                .orElseThrow();
    }

    public BasicInfo findBasicInfo(String htId) throws DataNotExistingException {
        Optional<Profile> profileOptional = profileDataService.findByHtId(htId);
        if (profileOptional.isEmpty()) {
            throw new DataNotExistingException(String.format("Profile is not existed with htId: %s", htId));
        }

        return profileOptional
                .flatMap(profile -> basicInfoDataService.findByHtCode(profile.getHtCode()))
                .orElseThrow(() -> new DataNotExistingException("Basic Info is not existed."));
    }

    public Profile findProfile(String htId) throws DataNotExistingException {
        Optional<Profile> profileOptional = profileDataService.findByHtId(htId);
        if (profileOptional.isEmpty()) {
            throw new DataNotExistingException(String.format("Profile is not existed with htId: %s", htId));
        }
        return profileOptional.get();
    }

    public boolean existsByHtId(String htId) {
        return profileDataService.existsByHtId(htId);
    }


    public Profile create(String hungthinhId, String leadSource) throws DataConflictingException {
        if (profileDataService.existsByHtId(hungthinhId)) {
            throw new DataConflictingException("HtID is already existed with value " + hungthinhId);
        }
        return Optional.of(hungthinhId)
                .map(htId -> profileDataService.create(htId, leadSource))
                .orElseThrow();
    }

    public void deleteProfile(String htId) {
        if (!profileDataService.existsByHtId(htId)) {
            throw new DataNotExistingException(String.format("Profile is not existed with htId: %s", htId));
        }
        profileDataService.deleteProfileByHtId(htId);
    }
}
