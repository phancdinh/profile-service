package org.ht.id.profileapi.facade;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ht.id.account.bizservice.ActivationBizService;
import org.ht.id.account.bizservice.InvitationBizService;
import org.ht.id.common.exception.DataNotExistingException;
import org.ht.id.profile.bizservice.ContactInfoBizService;
import org.ht.id.profileapi.config.MessageApiProperties;
import org.ht.id.profileapi.dto.request.InvitationCreateRequest;
import org.ht.id.profileapi.dto.request.InvitationUpdateRequest;
import org.ht.id.profileapi.dto.response.InvitationCreateResponse;
import org.ht.id.profileapi.dto.response.InvitationUpdateResponse;
import org.ht.id.profileapi.facade.converter.InvitationConverter;
import org.springframework.stereotype.Component;
import java.util.Optional;
import static java.util.function.Predicate.not;

@Component
@Slf4j
@RequiredArgsConstructor
public class InvitationFacade {

    private final InvitationBizService invitationBizService;
    private final ActivationBizService activationBizService;
    private final InvitationConverter invitationConverter;
    private final ContactInfoBizService contactInfoBizService;
    private final MessageApiProperties messageApiProperties;

    public InvitationCreateResponse createInvitation(InvitationCreateRequest invitationCreateRequest) {

        return Optional.of(invitationCreateRequest)
                .map(invitationConverter::convertToEntity)
                .filter(not(invitation -> checkEmailAndHtid(invitation.getMainContact(), invitation.getHtId())))
                .map(invitationBizService::create)
                .map(invitation -> invitationConverter.convertToInvitationCreateResponse(invitation, invitationBizService.generateInvitationLink(invitation)))
                .orElseThrow(() -> new DataNotExistingException(messageApiProperties.getMessageWithArgs("validation.invitation.not.created", invitationCreateRequest.getHtId())));
    }

    public InvitationUpdateResponse updateInvitation(InvitationUpdateRequest invitationUpdateRequest) {

        return Optional.of(invitationUpdateRequest)
                .map(invitationRequest -> invitationBizService.findById(invitationUpdateRequest.getId()))
                .filter(invitation -> !checkEmailHasRegistered(invitation.getMainContact()) && invitationBizService.isMatchValueCheck(invitation, invitationUpdateRequest.getValue()))
                .map(invitationBizService::update)
                .map(invitationConverter::convertToInvitationUpdateResponse)
                .orElseThrow(() -> new DataNotExistingException(messageApiProperties.getMessageWithArgs("validation.activation.not.existed", invitationUpdateRequest.getId())));
    }

    private boolean checkEmailAndHtid(String email, String htId) {
        return !checkEmailHasRegistered(email) && existedHtidWithEmail(htId, email);
    }

    private boolean existedHtidWithEmail(String htId, String email) {
        return contactInfoBizService.isEmailExistedWithHtId(htId, email);

    }

    private boolean checkEmailHasRegistered(String email) {
        return contactInfoBizService.existByEmailAndStatusActive(email) || activationBizService.existedActivation(email);
    }

}
