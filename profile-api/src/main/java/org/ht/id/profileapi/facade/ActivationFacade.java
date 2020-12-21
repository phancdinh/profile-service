package org.ht.id.profileapi.facade;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ht.id.account.bizservice.ActivationBizService;
import org.ht.id.account.exception.DataNotExistingException;
import org.ht.id.common.constant.UserStatus;
import org.ht.id.profile.bizservice.ContactInfoBizService;
import org.ht.id.profile.bizservice.ProfileBizService;
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

    public ActivationCreateResponse createActivation(ActivationCreateRequest activationCreateRequest) {

        return Optional.of(activationCreateRequest).map(requestDto -> activationConverter.convertToEntity(requestDto))
                .filter(not(activation -> activationBizService.existedActivation(activation.getEmail())
                        || contactInfoBizService.existByEmailAndStatusActive(activation.getEmail())))
                .map(activation -> activationBizService.create(activation))
                .map(activation -> activationConverter.convertToActivationCreateResponse(activation,
                        activationBizService.generateActivationLink(activation)))
                .orElseThrow(() -> new DataNotExistingException());
    }

    public ActivationUpdateResponse updateActivation(ActivationUpdateRequest activationUpdateRequest) {

        return Optional.of(activationUpdateRequest)
                .map(activationRequest -> activationBizService.findById(activationRequest.getId()))
                .filter(activation -> activationBizService.isValidActivation(activation, activationUpdateRequest.getValue()))
                .map(activation -> activationBizService.update(activation))
                .filter(not(activation -> profileBizService.updateStatus(activation.getHtId(), UserStatus.ACTIVE).isInactivated()))
                .map(activation -> activationConverter.convertToActivationUpdateResponse(activation))
                .orElseThrow(() -> new DataNotExistingException());
    }
}
