package org.ht.profileapi.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class ProfileApiProperties {
    @Value("${profile.web-client}")
    private String WebClient;
}
