package org.ht.profileapi.service;

import lombok.extern.slf4j.Slf4j;
import org.ht.profileapi.dto.request.BasicInfoCreateRequest;
import org.ht.profileapi.dto.response.BasicInfoResponse;
import org.ht.profileapi.helper.ProfileInfoConverterHelper;
import org.ht.profile.dto.BasicInfoDto;
import org.ht.profile.facade.ProfileFacadeService;
import org.ht.profile.facade.exception.ProfileConflictingException;
import org.ht.profile.facade.exception.ProfileNotExistingException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Component
@Slf4j
public class ProfileInfoService {

    private final ProfileFacadeService profileFacadeService;
    private final ProfileInfoConverterHelper profileInfoConverterHelper;

    public ProfileInfoService(ProfileFacadeService profileFacadeService, ProfileInfoConverterHelper profileInfoConverterHelper) {
        this.profileFacadeService = profileFacadeService;
        this.profileInfoConverterHelper = profileInfoConverterHelper;
    }

    public BasicInfoResponse create(String htId, BasicInfoCreateRequest profileRequest) {
        try {
            return Optional.of(profileRequest)
                    .map(info -> profileInfoConverterHelper.convertToDto(profileRequest, BasicInfoDto.class))
                    .map(profileDto -> profileFacadeService.create(htId, profileDto))
                    .map(profileDto -> profileInfoConverterHelper.convertToResponse(profileDto, BasicInfoResponse.class)).orElse(null);
        } catch (ProfileConflictingException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    public BasicInfoResponse find(String htId) {
        try {
            return profileInfoConverterHelper.convertToResponse(profileFacadeService.find(htId), BasicInfoResponse.class);
        } catch (ProfileNotExistingException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
