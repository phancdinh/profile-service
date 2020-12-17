package org.ht.id.profileapi.facade.converter;

import org.ht.id.account.data.model.Account;
import org.ht.id.profileapi.dto.response.ActivationResponse;
import org.springframework.stereotype.Component;

@Component
public class ActivationConverter {


    public ActivationResponse convertToActivationReponse(String htId, String url) {
        ActivationResponse activationResponse = new ActivationResponse();
        activationResponse.setHtId(htId);
        activationResponse.setUrl(url);
        return activationResponse;
    }

    public ActivationResponse convertToActivationReponse(Account account) {
        ActivationResponse activationResponse = new ActivationResponse();
        activationResponse.setHtId(account.getHtId());
        activationResponse.setContact(account.getEmail());
        return activationResponse;
    }

}
