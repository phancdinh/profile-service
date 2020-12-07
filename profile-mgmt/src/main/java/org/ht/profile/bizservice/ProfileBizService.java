package org.ht.profile.bizservice;

import lombok.extern.slf4j.Slf4j;
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
        // because we don't have api to create ht_id so I try to create ht_id for case creating basic info.
        // I think this business should be removed.
        // todo remove this business when we have api create ht id.
        Profile profile = profileDataService
                .findByHtId(htId)
                .orElseGet(() -> {
                    log.info("try to create profile when create basic info.");
                    return profileDataService.create(htId, "");
                });

        if (basicInfoDataService.existsByHtCode(profile.getHtCode())) {
            String error = String.format("Basic info is already existed with %s", htId);
            throw new DataConflictingException(error);
        }

        return Optional.of(profileConverterHelper.convert(basicInfo, profile.getHtCode()))
                .map(basicInfoDataService::create)
                .orElseThrow();
    }

    public BasicInfo find(String htId) throws DataNotExistingException {
        Optional<Profile> profileOptional = profileDataService.findByHtId(htId);
        if (profileOptional.isEmpty()) {
            throw new DataNotExistingException(String.format("Profile is not existed with htId: %s", htId));
        }

        return profileOptional
                .flatMap(profile -> basicInfoDataService.findByHtCode(profile.getHtCode()))
                .orElseThrow(() -> new DataNotExistingException("Basic Info is not existed."));
    }
    
    public boolean existsByHtId(String htId) {
    	return profileDataService.existsByHtId(htId);
    }
    

    
}
