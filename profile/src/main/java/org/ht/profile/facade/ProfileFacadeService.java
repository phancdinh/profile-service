package org.ht.profile.facade;

import lombok.extern.slf4j.Slf4j;
import org.ht.profile.bizservice.ProfileBizService;
import org.ht.profile.data.exception.DataConflictingException;
import org.ht.profile.data.exception.DataNotExistingException;
import org.ht.profile.dto.BasicInfoDto;
import org.ht.profile.facade.exception.ProfileConflictingException;
import org.ht.profile.facade.exception.ProfileNotExistingException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ProfileFacadeService {
    private final ProfileBizService profileBizService;

    public ProfileFacadeService(ProfileBizService profileBizService) {
        this.profileBizService = profileBizService;
    }

    public BasicInfoDto create(String htId, BasicInfoDto basicInfoDto) {
        try {
            return profileBizService.create(htId, basicInfoDto);
        } catch (DataConflictingException e) {
            throw new ProfileConflictingException(e);
        }
    }

    public BasicInfoDto find(String htId) {
        try {
            return profileBizService.find(htId);
        } catch (DataNotExistingException e) {
            throw new ProfileNotExistingException(e);
        }
    }
}
