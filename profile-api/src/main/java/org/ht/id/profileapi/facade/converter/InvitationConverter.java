package org.ht.id.profileapi.facade.converter;

import org.ht.id.profileapi.dto.response.InvitationResponse;
import org.springframework.stereotype.Component;

@Component
public class InvitationConverter {

    public InvitationResponse convertToInvitationReponse(String url, String htId, String mainContact) {
        InvitationResponse invitationResponse = new InvitationResponse();
        invitationResponse.setHtId(htId);
        invitationResponse.setUrl(url);
        invitationResponse.setMainContact(mainContact);
        return invitationResponse;
    }

}
