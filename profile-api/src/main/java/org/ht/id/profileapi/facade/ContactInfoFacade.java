package org.ht.id.profileapi.facade;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ht.id.profile.bizservice.ContactInfoBizService;
import org.ht.id.profile.bizservice.ProfileBizService;
import org.ht.id.profile.data.exception.DataConflictingException;
import org.ht.id.profile.data.exception.DataNotExistingException;
import org.ht.id.profileapi.dto.request.ContactInfoCreateRequest;
import org.ht.id.profileapi.dto.response.ContactInfoResponse;
import org.ht.id.profileapi.dto.response.internal.HierarchyContactResponse;
import org.ht.id.profileapi.facade.converter.ProfileInfoConverter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor
public class ContactInfoFacade {

    private final ContactInfoBizService contactInfoBizService;
    private final ProfileInfoConverter profileInfoConverter;
    private final ProfileBizService profileBizService;

    public ContactInfoResponse create(String htId, ContactInfoCreateRequest createRequest) {
        try {
            return Optional.of(createRequest)
                    .map(info -> profileInfoConverter.convertToEntity(info))
                    .map(contactInfoDto -> contactInfoBizService.create(htId, contactInfoDto))
                    .map(contactInfoDto -> profileInfoConverter.convertToResponse(contactInfoDto, htId)).orElse(null);
        } catch (DataConflictingException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        } catch (DataNotExistingException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    public ContactInfoResponse findByHtId(String htId) {
        try {
            return profileInfoConverter.convertToResponse(contactInfoBizService.findByHtId(htId), htId);
        } catch (DataNotExistingException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    public HierarchyContactResponse createContactEmail(String htId, String email) {
        try {
            var userProfile = profileBizService.findProfile(htId);
            if (userProfile.isInactivated()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Account has not activated yet");
            }

            var contactEmail = contactInfoBizService.createContactEmail(userProfile.getHtCode(), email);
            return profileInfoConverter.convertToResponse(contactEmail);
        } catch (DataNotExistingException ex1) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex1.getMessage());
        } catch (DataConflictingException ex2) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, ex2.getMessage());
        }
    }
}
