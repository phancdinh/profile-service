package org.ht.id.profileapi.facade;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ht.id.common.constant.DemoGraphicsAttribute;
import org.ht.id.profile.bizservice.DemoGraphicsInfoBizService;
import org.ht.id.profileapi.dto.request.DemoGraphicsInfoCreateRequest;
import org.ht.id.profileapi.dto.request.DemoGraphicsInfoUpdateRequest;
import org.ht.id.profileapi.dto.response.DemoGraphicsInfoResponse;
import org.ht.id.profileapi.facade.converter.ProfileInfoConverter;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor
public class DemoGraphicsInfoFacade {
    private final DemoGraphicsInfoBizService demoGraphicsInfoBizService;
    private final ProfileInfoConverter profileInfoConverter;

    public DemoGraphicsInfoResponse create(String htId,
                                           DemoGraphicsAttribute demoGraphicsInfoAttribute,
                                           DemoGraphicsInfoCreateRequest demoGraphicsInfoCreateRequest) {
        return Optional.of(demoGraphicsInfoCreateRequest)
                .map(info -> profileInfoConverter.convertToEntity(info, demoGraphicsInfoAttribute))
                .map(graphicsInfo -> demoGraphicsInfoBizService.create(htId, graphicsInfo))
                .map(graphicsInfo -> profileInfoConverter.convertToResponse(graphicsInfo, htId)).orElse(null);
    }

    public DemoGraphicsInfoResponse findByHtIdAndAttribute(String htId,
                                                           DemoGraphicsAttribute demoGraphicsInfoAttribute) {
        return profileInfoConverter.convertToResponse(
                demoGraphicsInfoBizService.findByHtIdAndAttribute(htId, demoGraphicsInfoAttribute), htId);
    }

    public DemoGraphicsInfoResponse update(String htId,
                                           DemoGraphicsAttribute demoGraphicsInfoAttribute,
                                           DemoGraphicsInfoUpdateRequest updateRequest) {
        return Optional.of(updateRequest)
                .map(info -> profileInfoConverter.convertToEntity(updateRequest, demoGraphicsInfoAttribute))
                .map(graphicsInfoDto -> demoGraphicsInfoBizService.update(htId, graphicsInfoDto))
                .map(graphicsInfoDto -> profileInfoConverter.convertToResponse(graphicsInfoDto, htId))
                .orElse(null);
    }

    public void delete(String htId, DemoGraphicsAttribute demoGraphicsInfoAttribute) {
        demoGraphicsInfoBizService.delete(htId, demoGraphicsInfoAttribute);
    }
}
