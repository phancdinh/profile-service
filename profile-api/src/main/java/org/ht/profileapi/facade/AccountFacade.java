package org.ht.profileapi.facade;

import lombok.extern.slf4j.Slf4j;
import org.ht.account.bizservice.AccountBizService;
import org.ht.account.bizservice.ManageLinkBizService;
import org.ht.account.bizservice.UserIdentityBizService;
import org.ht.account.data.model.Account;
import org.ht.account.dto.response.ResponseData;
import org.ht.account.dto.response.ResponseStatus;
import org.ht.profile.bizservice.IdGeneratorBizService;
import org.ht.profile.bizservice.ProfileBizService;
import org.ht.profileapi.config.ProfileApiProperties;
import org.ht.profileapi.dto.request.AccountCreationRequest;
import org.ht.profileapi.dto.response.AccountResponse;
import org.ht.profileapi.facade.converter.AccountConverter;
import org.ht.profileapi.facade.converter.ProfileInfoConverter;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
@Slf4j
public class AccountFacade {
    private final AccountBizService accountBizService;
    private final ProfileBizService profileBizService;
    private final AccountConverter accountConverter;
    private final ProfileInfoConverter profileInfoConverter;
    private final IdGeneratorBizService idGeneratorBizService;
    private final UserIdentityBizService userIdentityBizService;
    private final ManageLinkBizService manageLinkBizService;
    private final ProfileApiProperties profileApiProperties;

    public AccountFacade(AccountBizService accountBizService,
                         ProfileBizService profileBizService,
                         AccountConverter accountConverter,
                         ProfileInfoConverter profileInfoConverter,
                         IdGeneratorBizService idGeneratorBizService,
                         UserIdentityBizService userIdentityBizService,
                         ManageLinkBizService manageLinkBizService,
                         ProfileApiProperties profileApiProperties) {
        this.accountBizService = accountBizService;
        this.profileBizService = profileBizService;
        this.accountConverter = accountConverter;
        this.profileInfoConverter = profileInfoConverter;
        this.idGeneratorBizService = idGeneratorBizService;
        this.userIdentityBizService = userIdentityBizService;
        this.manageLinkBizService = manageLinkBizService;
        this.profileApiProperties = profileApiProperties;
    }

    public AccountResponse create(AccountCreationRequest creationRequest) {
        var htId = idGeneratorBizService.generateId();

        // 1. Create a profile mapping
        var profileCreated = profileBizService.create(htId, creationRequest.getEmail(), null, creationRequest.getLeadSource());

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

        accountResponse.setLeadSource(profileCreated.getLeadSource());
        return accountResponse;
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
