package org.ht.profileapi.facade.converter;

import org.ht.account.data.model.Account;
import org.ht.profileapi.dto.response.ActivationResponse;
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