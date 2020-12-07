package org.ht.profileapi.facade;

import lombok.extern.slf4j.Slf4j;
import org.ht.profile.bizservice.LegalInfoBizService;
import org.ht.profile.data.exception.DataConflictingException;
import org.ht.profile.data.exception.DataNotExistingException;
import org.ht.profile.data.model.LegalInfo;
import org.ht.profileapi.dto.request.LegalInfoCreateRequest;
import org.ht.profileapi.dto.response.LegalInfoResponse;
import org.ht.profileapi.facade.converter.ProfileInfoConverter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Component
@Slf4j
public class LegalInfoFacade {
    
    private final LegalInfoBizService legalInfoFacadeService;
    private final ProfileInfoConverter profileInfoConverter;

    public LegalInfoFacade(LegalInfoBizService legalInfoFacadeService, ProfileInfoConverter profileInfoConverter) {
        this.legalInfoFacadeService = legalInfoFacadeService;
        this.profileInfoConverter = profileInfoConverter;
    }

    public LegalInfoResponse create(String htId, LegalInfoCreateRequest createRequest) {
        try {
            return Optional.of(createRequest)
                    .map(info -> profileInfoConverter.convertToEntity(info, LegalInfo.class))
                    .map(legalInfo -> legalInfoFacadeService.create(htId, legalInfo))
                    .map(legalInfo -> profileInfoConverter.convertToResponse(legalInfo, htId)).orElse(null);
        } catch (DataConflictingException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        } catch (DataNotExistingException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    public LegalInfoResponse findByHtId(String htId) {
        try {
            return profileInfoConverter.convertToResponse(legalInfoFacadeService.findByHtId(htId), htId);
        } catch (DataNotExistingException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
