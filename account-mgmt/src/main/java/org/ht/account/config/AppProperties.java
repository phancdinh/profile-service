package org.ht.account.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class AppProperties {
    @Value("${account-mgmt.identity.mgt-user.id}")
    private String IdentityManagementUserId;

    @Value("${account-mgmt.identity.mgt-user.password}")
    private String IdentityManagementUserPassword;
}
