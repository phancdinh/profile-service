package org.ht.profileapi.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class ProfileApiProperties {
    @Value("${profile.web-client}")
    private String webClient;

    @Value("${profile.mail.host}")
    private String mailHost;

    @Value("${profile.mail.port}")
    private String mailPort;

    @Value("${profile.mail.username}")
    private String mailUsername;

    @Value("${profile.mail.password}")
    private String mailPassword;

    @Value("${profile.mail.from}")
    private String mailFrom;

    @Value("${account-mgmt.configuration.activation.email-subject}")
    private String activationEmailSubject;

    @Value("${profile.default-lead-source}")
    private String defaultLeadSource;
}
