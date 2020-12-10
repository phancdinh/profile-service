package org.ht.account.config;

import feign.auth.BasicAuthRequestInterceptor;
import feign.okhttp.OkHttpClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class UserIdentityClientConfiguration {

    private final AppProperties appProperties;

    public UserIdentityClientConfiguration(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    @Bean
    public OkHttpClient client() {
        return new OkHttpClient();
    }

    @Bean
    public BasicAuthRequestInterceptor basicAuthRequestInterceptor() {
        log.debug(String.format("%s :: %s", appProperties.getIdentityManagementUserId(), appProperties.getIdentityManagementUserPassword()));
        return new BasicAuthRequestInterceptor(appProperties.getIdentityManagementUserId(), appProperties.getIdentityManagementUserPassword());
    }
}
