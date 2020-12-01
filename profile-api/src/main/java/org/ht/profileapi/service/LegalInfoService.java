package org.ht.profileapi.service;

import lombok.extern.slf4j.Slf4j;
import org.ht.profileapi.dto.request.LegalInfoCreateRequest;
import org.ht.profileapi.dto.response.LegalInfoResponse;
import org.ht.profileapi.helper.ProfileInfoConverterHelper;
import org.ht.profile.dto.LegalInfoDto;
import org.ht.profile.facade.LegalInfoFacadeService;
import org.ht.profile.facade.exception.ProfileConflictingException;
import org.ht.profile.facade.exception.ProfileNotExistingException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Component
@Slf4j
public class LegalInfoService {
    
    private final LegalInfoFacadeService legalInfoFacadeService;
    private final ProfileInfoConverterHelper profileInfoConverterHelper;

    public LegalInfoService(LegalInfoFacadeService legalInfoFacadeService, ProfileInfoConverterHelper profileInfoConverterHelper) {
        this.legalInfoFacadeService = legalInfoFacadeService;
        this.profileInfoConverterHelper = profileInfoConverterHelper;
    }

    public LegalInfoResponse create(String htId, LegalInfoCreateRequest createRequest) {
        try {
            return Optional.of(createRequest)
                    .map(info -> profileInfoConverterHelper.convertToDto(info, LegalInfoDto.class))
                    .map(legalDto -> legalInfoFacadeService.create(htId, legalDto))
                    .map(legalDto -> profileInfoConverterHelper.convertToResponse(legalDto, LegalInfoResponse.class)).orElse(null);
        } catch (ProfileConflictingException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        } catch (ProfileNotExistingException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    public LegalInfoResponse findByHtId(String htId) {
        try {
            return profileInfoConverterHelper.convertToResponse(legalInfoFacadeService.findByHtId(htId), LegalInfoResponse.class);
        } catch (ProfileNotExistingException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
