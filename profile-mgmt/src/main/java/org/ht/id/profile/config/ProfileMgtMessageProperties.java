package org.ht.id.profile.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@RequiredArgsConstructor
public class ProfileMgtMessageProperties {

    private final MessageSource messageSource;

    public String getMessageWithArgs(String message, String param){
        return message.replace("{0}", param);
    }

    public String getMessage(String message){
        return messageSource.getMessage(message, null, Locale.getDefault());
    }
}
