package org.ht.id.profileapi.facade;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ht.id.profile.bizservice.LegalInfoBizService;
import org.ht.id.profileapi.dto.request.LegalInfoCreateRequest;
import org.ht.id.profileapi.dto.response.LegalInfoResponse;
import org.ht.id.profileapi.facade.converter.ProfileInfoConverter;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor
public class LegalInfoFacade {

    private final LegalInfoBizService legalInfoFacadeService;
    private final ProfileInfoConverter profileInfoConverter;

    public LegalInfoResponse create(String htId, LegalInfoCreateRequest createRequest) {
        return Optional.of(createRequest)
                .map(profileInfoConverter::convertToEntity)
                .map(legalInfo -> legalInfoFacadeService.create(htId, legalInfo))
                .map(legalInfo -> profileInfoConverter.convertToResponse(legalInfo, htId)).orElse(null);
    }

    public LegalInfoResponse findByHtId(String htId) {
        return profileInfoConverter.convertToResponse(legalInfoFacadeService.findByHtId(htId), htId);
    }
}
