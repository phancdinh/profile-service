package org.ht.id.profileapi.facade;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ht.id.account.bizservice.ActivationBizService;
import org.ht.id.account.bizservice.InvitationBizService;
import org.ht.id.profile.bizservice.ContactInfoBizService;
import org.ht.id.profile.data.exception.DataNotExistingException;
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

    public InvitationCreateResponse createInvitation(InvitationCreateRequest invitationCreateRequest) {

        return Optional.of(invitationCreateRequest)
                .map(requestDto -> invitationConverter.convertToEntity(requestDto))
                .filter(not(invitation -> checkEmailHasRegistered(invitation.getMainContact())))
                .map(invitation -> invitationBizService.create(invitation))
                .map(invitation -> invitationConverter.convertToInvitationCreateResponse(invitation, invitationBizService.generateInvitationLink(invitation)))
                .orElseThrow(() -> new DataNotExistingException(String.format("Invitation is not existed with id: %s", invitationCreateRequest.getHtId())));
    }

    public InvitationUpdateResponse updateInvitation(InvitationUpdateRequest invitationUpdateRequest) {

        return Optional.of(invitationUpdateRequest)
                .map(invitationRequest -> invitationBizService.findById(invitationUpdateRequest.getId()))
                .filter(invitation -> !checkEmailHasRegistered(invitation.getMainContact()) && invitationBizService.isMatchValueCheck(invitation, invitationUpdateRequest.getValue()))
                .map(invitation -> invitationBizService.update(invitation))
                .map(invitation -> invitationConverter.convertToInvitationUpdateResponse(invitation))
                .orElseThrow(() -> new DataNotExistingException(String.format("Invitation is not existed with id: %s", invitationUpdateRequest.getId())));
    }

    public boolean checkEmailHasRegistered(String email) {
        return contactInfoBizService.existByEmailAndStatusActive(email) || activationBizService.existedActivation(email);
    }

}
