package org.ht.profile.facade;

import lombok.extern.slf4j.Slf4j;
import org.ht.profile.bizservice.ContactInfoBizService;
import org.ht.profile.data.exception.DataConflictingException;
import org.ht.profile.data.exception.DataNotExistingException;
import org.ht.profile.dto.ContactInfoDto;
import org.ht.profile.facade.exception.ProfileConflictingException;
import org.ht.profile.facade.exception.ProfileNotExistingException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ContactInfoFacadeService {
    private final ContactInfoBizService contactInfoBizService;

    public ContactInfoFacadeService(ContactInfoBizService contactInfoBizService) {
        this.contactInfoBizService = contactInfoBizService;
    }


    public ContactInfoDto create(String htId, ContactInfoDto contactInfoDto) {
        try {
            return contactInfoBizService.create(htId, contactInfoDto);
        } catch (DataConflictingException e) {
            throw new ProfileConflictingException(e);
        } catch (DataNotExistingException e) {
            throw new ProfileNotExistingException(e);
        }
    }

    public ContactInfoDto findByHtId(String htId) {
        try {
            return contactInfoBizService.findByHtId(htId);
        } catch (DataNotExistingException e) {
            throw new ProfileNotExistingException(e);
        }
    }
}
