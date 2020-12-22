package org.ht.id.account.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountMgtMessageProperties {

    private final MessageSource messageSource;

    public String getMessage(String key, String... params){
        var valueOfKey = messageSource.getMessage(key, null, LocaleContextHolder.getLocale());
        if (valueOfKey.isEmpty() || params.length == 0) {
            return valueOfKey;
        }

        for (int i = 0; i < params.length; i++) {
            valueOfKey=valueOfKey.replace("{" + i + "}", params[i]);
        }
        return valueOfKey;
    }
}
