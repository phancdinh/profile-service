package org.ht.id.account.config;

import org.springframework.beans.factory.annotation.Autowired;

import feign.RequestInterceptor;
import feign.RequestTemplate;

public class ShortenLinkRequestInterceptor implements RequestInterceptor{

    @Autowired
    private BitlyApiProperties bitlyApiProperties;

    @Override
    public void apply(RequestTemplate template) {
            String auth = "Bearer " + bitlyApiProperties.getToken();
            template.header("Accept", "*/*");
            template.header("Authorization", auth);
    }
}
