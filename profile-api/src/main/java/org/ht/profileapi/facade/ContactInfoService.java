package org.ht.profileapi.facade;

import lombok.extern.slf4j.Slf4j;
import org.ht.profile.bizservice.ContactInfoBizService;
import org.ht.profile.data.exception.DataConflictingException;
import org.ht.profile.data.exception.DataNotExistingException;
import org.ht.profile.data.model.ContactInfo;
import org.ht.profileapi.dto.request.ContactInfoCreateRequest;
import org.ht.profileapi.dto.response.ContactInfoResponse;
import org.ht.profileapi.facade.converter.ProfileInfoConverter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Component
@Slf4j
public class ContactInfoService {

    private final ContactInfoBizService contactInfoBizService;
    private final ProfileInfoConverter profileInfoConverter;

    public ContactInfoService(ContactInfoBizService contactInfoBizService, ProfileInfoConverter profileInfoConverter) {
        this.contactInfoBizService = contactInfoBizService;
        this.profileInfoConverter = profileInfoConverter;
    }

    public ContactInfoResponse create(String htId, ContactInfoCreateRequest createRequest) {
        try {
            return Optional.of(createRequest)
                    .map(info -> profileInfoConverter.convertToEntity(info, ContactInfo.class))
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
}
