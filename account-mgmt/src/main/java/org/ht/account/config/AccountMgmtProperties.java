package org.ht.account.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Component
@Getter
public class AccountMgmtProperties {
    
    @Value("${account-mgmt.configuration.activation.default-link}")
    private String accountActivationLink;

    @Value("${account-mgmt.configuration.activation.expire-period-days}")
    private int activationExpirePeriodDays;
    
    @Value("${account-mgmt.configuration.invitation.default-link}")
    private String accountInvitationLink;
    
}
