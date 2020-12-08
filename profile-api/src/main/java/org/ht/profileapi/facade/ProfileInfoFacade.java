package org.ht.profileapi.facade;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ht.profile.bizservice.ContactInfoBizService;
import org.ht.profile.bizservice.IdGeneratorBizService;
import org.ht.profile.bizservice.LegalInfoBizService;
import org.ht.profile.bizservice.ProfileBizService;
import org.ht.profile.data.exception.DataConflictingException;
import org.ht.profile.data.exception.DataNotExistingException;
import org.ht.profile.data.model.BasicInfo;
import org.ht.profile.data.model.ContactInfo;
import org.ht.profile.data.model.LegalInfo;
import org.ht.profile.data.model.Profile;
import org.ht.profileapi.dto.request.BasicInfoCreateRequest;
import org.ht.profileapi.dto.request.ProfileCreateRequest;
import org.ht.profileapi.dto.response.BasicInfoResponse;
import org.ht.profileapi.dto.response.ProfileResponse;
import org.ht.profileapi.facade.converter.ProfileInfoConverter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor
public class ProfileInfoFacade {

    private final ProfileBizService profileBizService;
    private final ContactInfoBizService contactInfoBizService;
    private final LegalInfoBizService legalInfoBizService;
    private final ProfileInfoConverter profileInfoConverter;
    private final IdGeneratorBizService idGeneratorBizService;

    public BasicInfoResponse create(String htId, BasicInfoCreateRequest profileRequest) {
        try {
            return Optional.of(profileRequest)
                    .map(info -> profileInfoConverter.convertToEntity(profileRequest))
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


    public ProfileResponse createProfileFromCertainInfo(ProfileCreateRequest request) {
        // TODO: 07/12/2020 try to validate contact primary email and phone and existed, ref #363
        try {
            return Optional.of(request)
                    .map(this::createProfile).orElse(null);
        } catch (DataConflictingException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    private ProfileResponse createProfile(ProfileCreateRequest profileRequest) {
        String htId = idGeneratorBizService.generateId();
        BasicInfo basicInfo = profileInfoConverter.convertToEntity(profileRequest.getBasicInfo());
        ContactInfo contactInfo = profileInfoConverter.convertToEntity(profileRequest.getContactInfo());
        LegalInfo legalInfo = profileInfoConverter.convertToEntity(profileRequest.getLegalInfo());
        Profile profile = profileBizService.create(htId, profileRequest.getLeadSource());
        profileBizService.create(htId, basicInfo);
        contactInfoBizService.create(htId, contactInfo);
        legalInfoBizService.create(htId, legalInfo);
        return profileInfoConverter.convertToResponse(profile);
    }

    public void deleteProfile(String htId) {
        try {
            profileBizService.deleteProfile(htId);
        } catch (DataNotExistingException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
