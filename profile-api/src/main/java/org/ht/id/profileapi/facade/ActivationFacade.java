package org.ht.id.profileapi.facade;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ht.id.account.bizservice.ActivationBizService;
import org.ht.id.common.constant.UserStatus;
import org.ht.id.common.exception.DataNotExistingException;
import org.ht.id.profile.bizservice.ContactInfoBizService;
import org.ht.id.profile.bizservice.ProfileBizService;
import org.ht.id.profileapi.config.MessageApiProperties;
import org.ht.id.profileapi.dto.request.ActivationCreateRequest;
import org.ht.id.profileapi.dto.request.ActivationUpdateRequest;
import org.ht.id.profileapi.dto.response.ActivationCreateResponse;
import org.ht.id.profileapi.dto.response.ActivationUpdateResponse;
import org.ht.id.profileapi.facade.converter.ActivationConverter;
import org.springframework.stereotype.Component;
import java.util.Optional;
import static java.util.function.Predicate.not;

@Component
@Slf4j
@RequiredArgsConstructor
public class ActivationFacade {

    private final ActivationBizService activationBizService;
    private final ActivationConverter activationConverter;
    private final ContactInfoBizService contactInfoBizService;
    private final ProfileBizService profileBizService;
    private final MessageApiProperties messageApiProperties;

    public ActivationCreateResponse createActivation(ActivationCreateRequest activationCreateRequest) {

        return Optional.of(activationCreateRequest).map(activationConverter::convertToEntity)
                .filter(activation -> isValidCreateActivation(activation.getEmail(), activation.getHtId()))
                .map(activationBizService::create)
                .map(activation -> activationConverter.convertToActivationCreateResponse(activation,
                        activationBizService.generateActivationLink(activation)))
                .orElseThrow(() -> new DataNotExistingException(messageApiProperties.getMessage("validation.activation.not.created", activationCreateRequest.getHtId())));
    }

    public ActivationUpdateResponse updateActivation(ActivationUpdateRequest activationUpdateRequest) {

        return Optional.of(activationUpdateRequest)
                .map(activationRequest -> activationBizService.findById(activationRequest.getId()))
                .filter(activation -> activationBizService.isValidActivation(activation, activationUpdateRequest.getValue()))
                .filter(not (activation -> contactInfoBizService.existByEmailAndStatusActive(activation.getEmail())))
                .map(activationBizService::update)
                .filter(not(activation -> profileBizService.updateStatus(activation.getHtId(), UserStatus.ACTIVE).isInactivated()))
                .map(activationConverter::convertToActivationUpdateResponse)
                .orElseThrow(() -> new DataNotExistingException(messageApiProperties.getMessage("validation.activation.not.existed", activationUpdateRequest.getId())));
    }

    private boolean isValidCreateActivation(String email, String htId) {
        if (activationBizService.anyMatch(email)
                || contactInfoBizService.existByEmailAndStatusActive(email)
                || !contactInfoBizService.anyEmailWithHtId(htId, email)) {
            return false;
        }
        return true;
    }
}
