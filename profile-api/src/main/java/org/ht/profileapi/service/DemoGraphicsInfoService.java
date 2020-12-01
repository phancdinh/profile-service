package org.ht.profileapi.service;

import lombok.extern.slf4j.Slf4j;
import org.ht.profileapi.constants.DemoGraphicsAttribute;
import org.ht.profileapi.dto.request.DemoGraphicsInfoCreateRequest;
import org.ht.profileapi.dto.request.DemoGraphicsInfoUpdateRequest;
import org.ht.profileapi.dto.response.DemoGraphicsInfoResponse;
import org.ht.profileapi.helper.ProfileInfoConverterHelper;
import org.ht.profile.facade.DemoGraphicsInfoFacadeService;
import org.ht.profile.facade.exception.ProfileConflictingException;
import org.ht.profile.facade.exception.ProfileNotExistingException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Component
@Slf4j
public class DemoGraphicsInfoService {
    private final DemoGraphicsInfoFacadeService demoGraphicsInfoDataService;
    private final ProfileInfoConverterHelper profileInfoConverterHelper;

    public DemoGraphicsInfoService(DemoGraphicsInfoFacadeService demoGraphicsInfoDataService, ProfileInfoConverterHelper profileInfoConverterHelper) {
        this.demoGraphicsInfoDataService = demoGraphicsInfoDataService;
        this.profileInfoConverterHelper = profileInfoConverterHelper;
    }


    public DemoGraphicsInfoResponse create(String htId,
                                           DemoGraphicsAttribute demoGraphicsInfoAttribute,
                                           DemoGraphicsInfoCreateRequest demoGraphicsInfoCreateRequest) {
        try {
            return Optional.of(demoGraphicsInfoCreateRequest)
                    .map(info -> profileInfoConverterHelper.convert(info, demoGraphicsInfoAttribute))
                    .map(graphicsInfoDto -> demoGraphicsInfoDataService.create(htId, graphicsInfoDto))
                    .map(graphicsInfoDto -> profileInfoConverterHelper.convertToResponse(graphicsInfoDto, DemoGraphicsInfoResponse.class)).orElse(null);
        } catch (ProfileConflictingException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        } catch (ProfileNotExistingException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    public DemoGraphicsInfoResponse findByHtIdAndAttribute(String htId,
                                                           DemoGraphicsAttribute demoGraphicsInfoAttribute) {
        try {
            return profileInfoConverterHelper.convertToResponse(demoGraphicsInfoDataService.findByHtIdAndAttribute(htId, demoGraphicsInfoAttribute.name()), DemoGraphicsInfoResponse.class);
        } catch (ProfileNotExistingException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    public DemoGraphicsInfoResponse update(String htId,
                                           DemoGraphicsAttribute demoGraphicsInfoAttribute,
                                           DemoGraphicsInfoUpdateRequest updateRequest) {
        try {
            return Optional.of(updateRequest)
                    .map(info -> profileInfoConverterHelper.convert(info, demoGraphicsInfoAttribute))
                    .map(graphicsInfoDto -> demoGraphicsInfoDataService.update(htId, graphicsInfoDto))
                    .map(graphicsInfoDto -> profileInfoConverterHelper.convertToResponse(graphicsInfoDto, DemoGraphicsInfoResponse.class)).orElse(null);
        } catch (ProfileConflictingException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        } catch (ProfileNotExistingException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    public void delete(String htId,
                       DemoGraphicsAttribute demoGraphicsInfoAttribute) {
        try {
            demoGraphicsInfoDataService.delete(htId, demoGraphicsInfoAttribute.name());
        } catch (ProfileNotExistingException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
