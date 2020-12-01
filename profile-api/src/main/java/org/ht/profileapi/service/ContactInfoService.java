package org.ht.profileapi.service;

import lombok.extern.slf4j.Slf4j;
import org.ht.profileapi.dto.request.ContactInfoCreateRequest;
import org.ht.profileapi.dto.response.ContactInfoResponse;
import org.ht.profileapi.helper.ProfileInfoConverterHelper;
import org.ht.profile.dto.ContactInfoDto;
import org.ht.profile.facade.ContactInfoFacadeService;
import org.ht.profile.facade.exception.ProfileConflictingException;
import org.ht.profile.facade.exception.ProfileNotExistingException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Component
@Slf4j
public class ContactInfoService {

    private final ContactInfoFacadeService contactInfoFacadeService;
    private final ProfileInfoConverterHelper profileInfoConverterHelper;

    public ContactInfoService(ContactInfoFacadeService contactInfoFacadeService, ProfileInfoConverterHelper profileInfoConverterHelper) {
        this.contactInfoFacadeService = contactInfoFacadeService;
        this.profileInfoConverterHelper = profileInfoConverterHelper;
    }

    public ContactInfoResponse create(String htId, ContactInfoCreateRequest createRequest) {
        try {
            return Optional.of(createRequest)
                    .map(info -> profileInfoConverterHelper.convertToDto(info, ContactInfoDto.class))
                    .map(contactInfoDto -> contactInfoFacadeService.create(htId, contactInfoDto))
                    .map(contactInfoDto -> profileInfoConverterHelper.convertToResponse(contactInfoDto, ContactInfoResponse.class)).orElse(null);
        } catch (ProfileConflictingException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        } catch (ProfileNotExistingException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    public ContactInfoResponse findByHtId(String htId) {
        try {
            return profileInfoConverterHelper.convertToResponse(contactInfoFacadeService.findByHtId(htId), ContactInfoResponse.class);
        } catch (ProfileNotExistingException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
