package org.ht.id.profileapi.facade;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ht.id.account.bizservice.AccountBizService;
import org.ht.id.account.bizservice.ActivationBizService;
import org.ht.id.account.config.AccountMgmtProperties;
import org.ht.id.account.data.model.Account;
import org.ht.id.account.data.model.Activation;
import org.ht.id.common.constant.UserStatus;
import org.ht.id.common.exception.EmailAlreadyRegisteredException;
import org.ht.id.profile.bizservice.ContactInfoBizService;
import org.ht.id.profile.bizservice.IdGeneratorBizService;
import org.ht.id.profile.bizservice.ProfileBizService;
import org.ht.id.profile.data.model.Profile;
import org.ht.id.profileapi.config.MessageApiProperties;
import org.ht.id.profileapi.config.ProfileApiProperties;
import org.ht.id.profileapi.dto.request.AccountCreationRequest;
import org.ht.id.profileapi.dto.response.AccountResponse;
import org.ht.id.profileapi.dto.response.ResetPasswordResponse;
import org.ht.id.profileapi.facade.converter.AccountConverter;
import org.ht.id.profileapi.facade.converter.ActivationConverter;
import org.ht.id.profileapi.service.AppEmailService;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import java.util.Optional;
import static java.util.function.Predicate.not;

@Component
@Slf4j
@RequiredArgsConstructor
public class AccountFacade {
    private final AccountBizService accountBizService;
    private final ProfileBizService profileBizService;
    private final ContactInfoBizService contactInfoBizService;
    private final ActivationBizService activationBizService;
    private final IdGeneratorBizService idGeneratorBizService;
    private final AccountConverter accountConverter;
    private final ActivationConverter activationConverter;
    private final ProfileApiProperties profileApiProperties;
    private final AccountMgmtProperties accountApiProperties;
    private final MessageApiProperties messageApiProperties;
    private final AppEmailService appEmailService;

    public AccountResponse create(AccountCreationRequest creationRequest) {
        validateWhenRegister(creationRequest);

        if (StringUtils.isEmpty(creationRequest.getLeadSource())) {
            creationRequest.setLeadSource(profileApiProperties.getDefaultLeadSource());
        }

        // 1. Create a profile mapping or check ht_id
        Pair<String, Profile> stringProfilePair = createHtIdOrUpdateStatus(creationRequest);
        var htId = stringProfilePair.getFirst();
        var profile = stringProfilePair.getSecond();

        // 2. Create a account, this will be check
        var accountResponse = Optional.of(creationRequest)
                .map(account -> accountConverter.convertToEntity(creationRequest, Account.class))
                .map(account -> accountBizService.createOrUpdate(htId, account))
                .map(account -> accountConverter.convertToResponse(account, AccountResponse.class))
                .orElseThrow();

        //3.Generate activation and send activation link to email
        Activation activation = Optional.of(creationRequest)
                .map(request -> activationConverter.convertToEntity(request, htId))
                .filter(not(a -> activationBizService.existedActivation(a.getEmail())
                        || contactInfoBizService.existByEmailAndStatusActive(a.getEmail())))
                .map(activationBizService::create)
                .orElseThrow();

        String activationLink = activationBizService.generateActivationLink(activation);
        appEmailService.sendActivationEmail(activationLink, creationRequest.getEmail());

        accountResponse.setLeadSource(profile.getLeadSource());
        return accountResponse;
    }

    private Pair<String, Profile> createHtIdOrUpdateStatus(AccountCreationRequest creationRequest) {
        var htId = creationRequest.getHtId();
        Profile profile;
        if (StringUtils.isEmpty(htId)) {
            htId = idGeneratorBizService.generateId();
            profile = profileBizService.create(htId, creationRequest.getLeadSource(), creationRequest.getEmail(), null, creationRequest.getPassword());
            return Pair.of(htId, profile);
        }

        // htId is existed then we check and link account to htId
        profile = profileBizService.findProfile(htId);
        contactInfoBizService.updatePrimaryEmail(profile.getHtCode(), creationRequest.getEmail());
        profileBizService.updateStatus(htId, UserStatus.IN_ACTIVE, creationRequest.getEmail(), null, creationRequest.getPassword());
        return Pair.of(htId, profile);
    }

    private void validateWhenRegister(AccountCreationRequest creationRequest) {
        String email = creationRequest.getEmail();
        if (checkEmailHasRegistered(email)) {
            throw new EmailAlreadyRegisteredException(messageApiProperties.getMessage("validation.register.mailRegistered"));
        }
    }

    public boolean checkEmailHasRegistered(String email) {
        return contactInfoBizService.existByEmailAndStatusActive(email) || activationBizService.existedActivation(email);
    }

    public ResetPasswordResponse resetPassword(String customerEmail) {
        boolean isValidEmail = validEmailForResetPassword(customerEmail);
        if (isValidEmail) {
            appEmailService.sendResetPasswordEmail(customerEmail, accountApiProperties.getResetPasswordLink());
        }
        return new ResetPasswordResponse(isValidEmail);
    }

    private boolean validEmailForResetPassword(String customerEmail) {
        return contactInfoBizService.existByEmailAndStatusActive(customerEmail);
    }
}
