package org.ht.id.profileapi.facade;

import java.util.Optional;

import org.ht.id.account.bizservice.ActivationBizService;
import org.ht.id.profile.bizservice.ContactInfoBizService;
import org.ht.id.profile.bizservice.IdGeneratorBizService;
import org.ht.id.profile.bizservice.LegalInfoBizService;
import org.ht.id.profile.bizservice.ProfileBizService;
import org.ht.id.profile.data.exception.DataConflictingException;
import org.ht.id.profile.data.exception.DataNotExistingException;
import org.ht.id.profile.data.model.BasicInfo;
import org.ht.id.profile.data.model.ContactInfo;
import org.ht.id.profile.data.model.LegalInfo;
import org.ht.id.profile.data.model.Profile;
import org.ht.id.profileapi.config.MessageApiProperties;
import org.ht.id.profileapi.dto.request.BasicInfoCreateRequest;
import org.ht.id.profileapi.dto.request.ProfileCreateRequest;
import org.ht.id.profileapi.dto.request.internal.HierarchyContactRequest;
import org.ht.id.profileapi.dto.response.BasicInfoResponse;
import org.ht.id.profileapi.dto.response.ProfileResponse;
import org.ht.id.profileapi.facade.converter.ProfileInfoConverter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class ProfileInfoFacade {

    private final ProfileBizService profileBizService;
    private final ContactInfoBizService contactInfoBizService;
    private final LegalInfoBizService legalInfoBizService;
    private final ProfileInfoConverter profileInfoConverter;
    private final ActivationBizService activationBizService;
    private final IdGeneratorBizService idGeneratorBizService;
    private final MessageApiProperties messageApiProperties;

    public BasicInfoResponse create(String htId, BasicInfoCreateRequest profileRequest) {
        try {
            return Optional.of(profileRequest)
                    .map(info -> profileInfoConverter.convertToEntity(profileRequest))
                    .map(profileDto -> profileBizService.createBasicInfo(htId, profileDto))
                    .map(profileDto -> profileInfoConverter.convertToResponse(profileDto, htId)).orElse(null);
        } catch (DataConflictingException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        } catch (DataNotExistingException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    public BasicInfoResponse find(String htId) {
        try {
            return profileInfoConverter.convertToResponse(profileBizService.findBasicInfo(htId), htId);
        } catch (DataNotExistingException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }


    public ProfileResponse createProfileFromCertainInfo(ProfileCreateRequest request) {
        return Optional.of(request).map(r -> {
            validateWhenAddProfile(r);
            try {
                return Optional.of(r)
                        .map(this::createProfile).orElse(null);
            } catch (DataConflictingException e) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
            }
        }).orElse(null);
    }

    private ProfileResponse createProfile(ProfileCreateRequest profileRequest) {
        String htId = idGeneratorBizService.generateId();
        BasicInfo basicInfo = profileInfoConverter.convertToEntity(profileRequest.getBasicInfo());
        ContactInfo contactInfo = profileInfoConverter.convertToEntity(profileRequest.getContactInfo());
        LegalInfo legalInfo = profileInfoConverter.convertToEntity(profileRequest.getLegalInfo());
        Profile profile = profileBizService.create(htId, profileRequest.getLeadSource());
        profileBizService.createBasicInfo(htId, basicInfo);
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

    private void validateWhenAddProfile(ProfileCreateRequest creationRequest) {
        HierarchyContactRequest primaryEmail = creationRequest.getContactInfo().getEmails()
                .stream()
                .filter(HierarchyContactRequest::isPrimary)
                .findFirst().orElse(new HierarchyContactRequest());
        String email = primaryEmail.getValue();
        if (contactInfoBizService.existByEmailAndStatusActive(email) || activationBizService.existedActivation(email)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, messageApiProperties.getMessage("validation.register.mailRegistered"));
        }
    }

}
