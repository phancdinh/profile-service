package org.ht.profile.facade;

import lombok.extern.slf4j.Slf4j;
import org.ht.profile.bizservice.LegalInfoBizService;
import org.ht.profile.data.exception.DataConflictingException;
import org.ht.profile.data.exception.DataNotExistingException;
import org.ht.profile.dto.LegalInfoDto;
import org.ht.profile.facade.exception.ProfileConflictingException;
import org.ht.profile.facade.exception.ProfileNotExistingException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class LegalInfoFacadeService {
    private final LegalInfoBizService legalInfoBizService;

    public LegalInfoFacadeService(LegalInfoBizService legalInfoBizService) {
        this.legalInfoBizService = legalInfoBizService;
    }


    public LegalInfoDto create(String htId, LegalInfoDto contactInfoDto) {
        try {
            return legalInfoBizService.create(htId, contactInfoDto);
        } catch (DataConflictingException e) {
            throw new ProfileConflictingException(e);
        } catch (DataNotExistingException e) {
            throw new ProfileNotExistingException(e);
        }
    }

    public LegalInfoDto findByHtId(String htId) {
        try {
            return legalInfoBizService.findByHtId(htId);
        } catch (DataNotExistingException e) {
            throw new ProfileNotExistingException(e);
        }
    }
}
