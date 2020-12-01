package org.ht.profile.facade;

import lombok.extern.slf4j.Slf4j;
import org.ht.profile.bizservice.DemoGraphicsInfoBizService;
import org.ht.profile.data.exception.DataConflictingException;
import org.ht.profile.data.exception.DataNotExistingException;
import org.ht.profile.dto.DemoGraphicsInfoDto;
import org.ht.profile.facade.exception.ProfileConflictingException;
import org.ht.profile.facade.exception.ProfileNotExistingException;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DemoGraphicsInfoFacadeService {
    private final DemoGraphicsInfoBizService demoGraphicsInfoBizService;

    public DemoGraphicsInfoFacadeService(DemoGraphicsInfoBizService demoGraphicsInfoBizService) {
        this.demoGraphicsInfoBizService = demoGraphicsInfoBizService;
    }


    public DemoGraphicsInfoDto create(String htId,
                                      DemoGraphicsInfoDto demoGraphicsInfoDto) {
        try {
            return demoGraphicsInfoBizService.create(htId, demoGraphicsInfoDto);
        } catch (DataConflictingException e) {
            throw new ProfileConflictingException(e);
        } catch (DataNotExistingException e) {
            throw new ProfileNotExistingException(e);
        }
    }

    public DemoGraphicsInfoDto findByHtIdAndAttribute(String htId, String attribute) {
        try {
            return demoGraphicsInfoBizService.findByHtIdAndAttribute(htId, attribute);
        } catch (DataNotExistingException e) {
            throw new ProfileNotExistingException(e);
        }
    }

    public DemoGraphicsInfoDto update(String htId, DemoGraphicsInfoDto demoGraphicsInfoDto) {
        try {
            return demoGraphicsInfoBizService.update(htId, demoGraphicsInfoDto);
        } catch (DataNotExistingException e) {
            throw new ProfileNotExistingException(e);
        }
    }

    public void delete(String htId,
                       String attribute) {
        try {
            demoGraphicsInfoBizService.delete(htId, attribute);
        } catch (DataNotExistingException e) {
            throw new ProfileNotExistingException(e);
        }

    }
}
