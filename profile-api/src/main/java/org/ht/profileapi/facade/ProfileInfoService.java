package org.ht.profileapi.facade;

import lombok.extern.slf4j.Slf4j;
import org.ht.profile.bizservice.ProfileBizService;
import org.ht.profile.data.exception.DataConflictingException;
import org.ht.profile.data.exception.DataNotExistingException;
import org.ht.profile.data.model.BasicInfo;
import org.ht.profileapi.dto.request.BasicInfoCreateRequest;
import org.ht.profileapi.dto.response.BasicInfoResponse;
import org.ht.profileapi.facade.converter.ProfileInfoConverter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Component
@Slf4j
public class ProfileInfoService {

    private final ProfileBizService profileBizService;
    private final ProfileInfoConverter profileInfoConverter;

    public ProfileInfoService(ProfileBizService profileBizService, ProfileInfoConverter profileInfoConverter) {
        this.profileBizService = profileBizService;
        this.profileInfoConverter = profileInfoConverter;
    }

    public BasicInfoResponse create(String htId, BasicInfoCreateRequest profileRequest) {
        try {
            return Optional.of(profileRequest)
                    .map(info -> profileInfoConverter.convertToEntity(profileRequest, BasicInfo.class))
                    .map(profileDto -> profileBizService.create(htId, profileDto))
                    .map(profileDto -> profileInfoConverter.convertToResponse(profileDto, htId)).orElse(null);
        } catch (DataConflictingException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    public BasicInfoResponse find(String htId) {
        try {
            return profileInfoConverter.convertToResponse(profileBizService.find(htId), htId);
        } catch (DataNotExistingException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
