package org.ht.account.config;

import org.springframework.context.annotation.Bean;

public class ShortenLinkClientConfiguration {
    
    @Bean
    public ShortenLinkRequestInterceptor shortenLinkRequestInterceptor() {
        return new ShortenLinkRequestInterceptor();
    }
}