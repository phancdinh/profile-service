package org.ht.profileapi.facade;

import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.ht.account.bizservice.ManageLinkBizService;
import org.ht.account.data.model.Account;
import org.ht.common.constant.UserStatus;
import org.ht.profile.bizservice.ContactInfoBizService;
import org.ht.account.exception.DataNotExistingException;
import org.ht.profile.bizservice.ProfileBizService;
import org.ht.profile.data.model.ContactInfo;
import org.ht.profileapi.dto.response.ActivationResponse;
import org.ht.profileapi.dto.response.InvitationResponse;
import org.ht.profileapi.facade.converter.ActivationConverter;
import org.ht.profileapi.facade.converter.InvitationConverter;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class AccountManageLinkFacade {

    private final ManageLinkBizService manageLinkBizService;
    private final ContactInfoBizService contactInfoBizService;
    private final InvitationConverter invitationConverter;
    private final ActivationConverter activationConverter;
    private final ProfileBizService profileBizService;

    public ActivationResponse generateActivationLink(String htId) {
        return Optional.of(manageLinkBizService.generateActivationLink(htId))
                .map(r -> activationConverter.convertToActivationReponse(htId, r)).orElseThrow();
    }

    public ActivationResponse activateAccount(String htId, String valid) {
        Account account = manageLinkBizService.validateActivationLink(htId, valid);
        profileBizService.updateStatus(htId, UserStatus.ACTIVE);
        return activationConverter.convertToActivationReponse(account);
    }

    public InvitationResponse generateInvitationLink(String htId, String contact) throws DataNotExistingException {
        // Check contact info is existed on profile or not?
        ContactInfo contactInfo = contactInfoBizService.findByHtId(htId);
        if (contactInfo == null
                || !contactInfo.getEmails().stream().anyMatch(p -> contact.equalsIgnoreCase(p.getValue()))) {
            String error = String.format("Contact Info does not existed with htId: %s", htId);
            log.error(error);
            throw new DataNotExistingException(error);
        }
        return Optional.of(manageLinkBizService.generateInvitationLink(htId, contact))
                .map(r -> invitationConverter.convertToInvitationReponse(r, htId, contact)).orElseThrow();

    }

    public InvitationResponse getInvitationLink(String htId, String valid) {
        return Optional.of(manageLinkBizService.getInvitationLink(htId, valid))
                .map(r -> invitationConverter.convertToInvitationReponse("", htId, r.getMainContact())).orElseThrow();
    }

}
