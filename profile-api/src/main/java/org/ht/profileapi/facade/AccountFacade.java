package org.ht.profileapi.facade;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ht.account.bizservice.AccountBizService;
import org.ht.account.bizservice.ManageLinkBizService;
import org.ht.account.bizservice.UserIdentityBizService;
import org.ht.account.data.model.Account;
import org.ht.account.dto.response.ResponseData;
import org.ht.account.dto.response.ResponseStatus;
import org.ht.profile.bizservice.ContactInfoBizService;
import org.ht.profile.bizservice.IdGeneratorBizService;
import org.ht.profile.bizservice.ProfileBizService;
import org.ht.profile.data.exception.DataNotExistingException;
import org.ht.profile.data.model.Profile;
import org.ht.profileapi.config.ProfileApiProperties;
import org.ht.profileapi.dto.request.AccountCreationRequest;
import org.ht.profileapi.dto.response.AccountResponse;
import org.ht.profileapi.facade.converter.AccountConverter;
import org.ht.profileapi.facade.converter.ProfileInfoConverter;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor
public class AccountFacade {
    private final AccountBizService accountBizService;
    private final ProfileBizService profileBizService;
    private final ContactInfoBizService contactInfoBizService;
    private final AccountConverter accountConverter;
    private final ProfileInfoConverter profileInfoConverter;
    private final IdGeneratorBizService idGeneratorBizService;
    private final UserIdentityBizService userIdentityBizService;
    private final ManageLinkBizService manageLinkBizService;
    private final ProfileApiProperties profileApiProperties;

    public AccountResponse create(AccountCreationRequest creationRequest) {
        // 1. Create a profile mapping or check ht_id
        Pair<String, Profile> stringProfilePair = createOrCheckHtId(creationRequest);
        var htId = stringProfilePair.getFirst();
        var profile = stringProfilePair.getSecond();
        // 2. Create a identity user account in IS
        userIdentityBizService.createUser(htId, creationRequest.getPassword(), creationRequest.getEmail());

        // 3. Create a account
        var accountResponse = Optional.of(creationRequest)
                .map(account -> accountConverter.convertToEntity(creationRequest, Account.class))
                .map(account -> accountBizService.create(htId, account))
                .map(account -> accountConverter.convertToResponse(account, AccountResponse.class))
                .orElseThrow();

        // TODO: At now skip for sending activation email, still here bug on AB#407
        // var activationLinkResponse = manageLinkBizService.getActLink(htId, profileApiProperties.getWebClient());

        accountResponse.setLeadSource(profile.getLeadSource());
        return accountResponse;
    }


    private Pair<String, Profile> createOrCheckHtId(AccountCreationRequest creationRequest) {
        var htId = creationRequest.getHtId();
        Profile profile;
        if (StringUtils.isEmpty(htId)) {
            htId = idGeneratorBizService.generateId();
            profile = profileBizService.create(htId, creationRequest.getEmail(), null, creationRequest.getLeadSource());
        } else {
            //htId is existed then we check and link account to htId
            try {
                profile = profileBizService.findProfile(htId);
                contactInfoBizService.updatePrimaryEmail(profile.getHtCode(), creationRequest.getEmail());
            } catch (DataNotExistingException e) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
            }

        }
        return Pair.of(htId, profile);
    }


    public ResponseData getActLink(String htId, String prefixUrl) {
        return manageLinkBizService.getActLink(htId, prefixUrl);
    }

    public ResponseData verifyActLink(String htId, String url) {
        return manageLinkBizService.verifyActLink(htId, url);
    }

    public ResponseData getInvtLink(String htId, String prefixUrl, String contact) {
        ResponseData response = new ResponseData();

        if (profileBizService.existsByHtId(htId)) {
            response.setStatus(ResponseStatus.FAILURE);
            response.setMessage("Doest not existed Profile!");
            return response;
        }

        return manageLinkBizService.getInvtLink(htId, prefixUrl, contact);
    }

    public ResponseData verifyInvtLink(String htId, String url, String contact) {
        return manageLinkBizService.verifyInvtLink(htId, url, contact);
    }
}
