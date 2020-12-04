package org.ht.account.config;

import feign.auth.BasicAuthRequestInterceptor;
import feign.okhttp.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
        return new BasicAuthRequestInterceptor(appProperties.getIdentityManagementUserId(), appProperties.getIdentityManagementUserPassword());
    }
}
