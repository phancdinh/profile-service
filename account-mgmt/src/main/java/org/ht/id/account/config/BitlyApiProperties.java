package org.ht.id.account.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class BitlyApiProperties {

    @Value("${account-mgmt.bitly.token}")
    private String token;

    @Value("${account-mgmt.bitly.name}")
    private String serviceName;

    private String groupName;

}

