package org.ht.id.profileapi.facade.converter;

import org.ht.id.account.data.model.Invitation;
import org.ht.id.account.data.model.Invitation.InvitationBuilder;
import org.ht.id.profileapi.dto.request.InvitationCreateRequest;
import org.ht.id.profileapi.dto.response.InvitationCreateResponse;
import org.ht.id.profileapi.dto.response.InvitationCreateResponse.InvitationCreateResponseBuilder;
import org.ht.id.profileapi.dto.response.InvitationUpdateResponse;
import org.ht.id.profileapi.dto.response.InvitationUpdateResponse.InvitationUpdateResponseBuilder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class InvitationConverter {

    public Invitation convertToEntity(InvitationCreateRequest invitationCreateRequest) {
        return Optional.ofNullable(invitationCreateRequest).map(r -> {
            InvitationBuilder builder = Invitation.builder();
            return builder.htId(r.getHtId()).mainContact(r.getContact()).build();
        }).orElseThrow();
    }

    public InvitationCreateResponse convertToInvitationCreateResponse(Invitation invitation, String url) {
        return Optional.ofNullable(invitation).map(i -> {
            InvitationCreateResponseBuilder builder = InvitationCreateResponse.builder();
            return builder.htId(i.getHtId()).url(url).createDate(i.getCreatedAt()).build();
        }).orElseThrow();
    }

    public InvitationUpdateResponse convertToInvitationUpdateResponse(Invitation invitation) {
        return Optional.ofNullable(invitation).map(a -> {
            InvitationUpdateResponseBuilder builder = InvitationUpdateResponse.builder();
            return builder.mainContact(invitation.getMainContact()).htId(invitation.getHtId()).createDate(invitation.getCreatedAt()).build();
        }).orElseThrow();
    }

}