package org.ht.account.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import feign.auth.BasicAuthRequestInterceptor;
import feign.okhttp.OkHttpClient;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserIdentityClientConfiguration {

    @Value("${account-mgmt.identity.mgt-user.id}")
    private String managementUserId;

    @Value("${account-mgmt.identity.mgt-user.password}")
    private String managementUserPassword;

    @Bean
    public OkHttpClient client() {
        return new OkHttpClient();
    }

    @Bean
    public BasicAuthRequestInterceptor basicAuthRequestInterceptor() {

        log.debug(String.format("Identity Server:: %s / %s", managementUserId, managementUserPassword));
        return new BasicAuthRequestInterceptor(managementUserId, managementUserPassword);
    }
}