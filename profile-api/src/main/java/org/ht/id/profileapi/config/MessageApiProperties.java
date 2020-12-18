package org.ht.id.profileapi.config;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class MessageApiProperties {

    private final MessageSource messageSource;

    public MessageApiProperties(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String getCustomMessage(String message) {
        return messageSource.getMessage(message, null, Locale.getDefault());
    }
}
