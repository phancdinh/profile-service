package org.ht.id.profileapi.facade.converter;

import org.ht.id.account.data.model.Activation;
import org.ht.id.account.data.model.Activation.ActivationBuilder;
import org.ht.id.profileapi.dto.request.AccountCreationRequest;
import org.ht.id.profileapi.dto.request.ActivationCreateRequest;
import org.ht.id.profileapi.dto.response.ActivationCreateResponse;
import org.ht.id.profileapi.dto.response.ActivationCreateResponse.ActivationCreateResponseBuilder;
import org.ht.id.profileapi.dto.response.ActivationUpdateResponse;
import org.ht.id.profileapi.dto.response.ActivationUpdateResponse.ActivationUpdateResponseBuilder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ActivationConverter {

    public Activation convertToEntity(ActivationCreateRequest activationCreateRequest) {
        return Optional.ofNullable(activationCreateRequest).map(r -> {
            ActivationBuilder builder = Activation.builder();
            return builder.email(r.getEmail()).htId(r.getHtId()).phone(r.getPhone()).build();
        }).orElseThrow();
    }

    public Activation convertToEntity(AccountCreationRequest accountCreationRequest, String htId) {
        return Optional.ofNullable(accountCreationRequest).map(r -> {
            ActivationBuilder builder = Activation.builder();
            return builder.email(r.getEmail()).htId(htId).build();
        }).orElseThrow();
    }

    public ActivationCreateResponse convertToActivationCreateResponse(Activation activation, String url) {
        return Optional.ofNullable(activation).map(a -> {
            ActivationCreateResponseBuilder builder = ActivationCreateResponse.builder();
            return builder.id(a.getId().toString()).htId(a.getHtId()).url(url).createDate(activation.getCreatedAt()).build();
        }).orElseThrow();
    }

    public ActivationUpdateResponse convertToActivationUpdateResponse(Activation activation) {
        return Optional.ofNullable(activation).map(a -> {
            ActivationUpdateResponseBuilder builder = ActivationUpdateResponse.builder();
            return builder.id(activation.getId().toString()).htId(activation.getHtId()).contact(activation.getEmail()).createDate(activation.getCreatedAt()).build();
        }).orElseThrow();
    }
}