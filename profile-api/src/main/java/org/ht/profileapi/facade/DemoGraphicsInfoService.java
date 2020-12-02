package org.ht.profileapi.facade;

import lombok.extern.slf4j.Slf4j;
import org.ht.common.constant.DemoGraphicsAttribute;
import org.ht.profile.bizservice.DemoGraphicsInfoBizService;
import org.ht.profile.data.exception.DataConflictingException;
import org.ht.profile.data.exception.DataNotExistingException;
import org.ht.profileapi.dto.request.DemoGraphicsInfoCreateRequest;
import org.ht.profileapi.dto.request.DemoGraphicsInfoUpdateRequest;
import org.ht.profileapi.dto.response.DemoGraphicsInfoResponse;
import org.ht.profileapi.facade.converter.ProfileInfoConverter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Component
@Slf4j
public class DemoGraphicsInfoService {
    private final DemoGraphicsInfoBizService demoGraphicsInfoBizService;
    private final ProfileInfoConverter profileInfoConverter;

    public DemoGraphicsInfoService(DemoGraphicsInfoBizService demoGraphicsInfoBizService, ProfileInfoConverter profileInfoConverter) {
        this.demoGraphicsInfoBizService = demoGraphicsInfoBizService;
        this.profileInfoConverter = profileInfoConverter;
    }


    public DemoGraphicsInfoResponse create(String htId,
                                           DemoGraphicsAttribute demoGraphicsInfoAttribute,
                                           DemoGraphicsInfoCreateRequest demoGraphicsInfoCreateRequest) {
        try {
            return Optional.of(demoGraphicsInfoCreateRequest)
                    .map(info -> profileInfoConverter.convert(info, demoGraphicsInfoAttribute))
                    .map(graphicsInfo -> demoGraphicsInfoBizService.create(htId, graphicsInfo))
                    .map(graphicsInfo -> profileInfoConverter.convertToResponse(graphicsInfo, htId)).orElse(null);
        } catch (DataConflictingException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        } catch (DataNotExistingException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    public DemoGraphicsInfoResponse findByHtIdAndAttribute(String htId,
                                                           DemoGraphicsAttribute demoGraphicsInfoAttribute) {
        try {
            return profileInfoConverter.convertToResponse(demoGraphicsInfoBizService.findByHtIdAndAttribute(htId, demoGraphicsInfoAttribute), htId);
        } catch (DataNotExistingException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    public DemoGraphicsInfoResponse update(String htId,
                                           DemoGraphicsAttribute demoGraphicsInfoAttribute,
                                           DemoGraphicsInfoUpdateRequest updateRequest) {
        try {
            return Optional.of(updateRequest)
                    .map(info -> profileInfoConverter.convert(updateRequest, demoGraphicsInfoAttribute))
                    .map(graphicsInfoDto -> demoGraphicsInfoBizService.update(htId, graphicsInfoDto))
                    .map(graphicsInfoDto -> profileInfoConverter.convertToResponse(graphicsInfoDto, htId)).orElse(null);
        } catch (DataConflictingException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        } catch (DataNotExistingException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    public void delete(String htId,
                       DemoGraphicsAttribute demoGraphicsInfoAttribute) {
        try {
            demoGraphicsInfoBizService.delete(htId, demoGraphicsInfoAttribute);
        } catch (DataNotExistingException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
