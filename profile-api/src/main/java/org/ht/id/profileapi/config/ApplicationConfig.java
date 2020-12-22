package org.ht.id.profileapi.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.data.domain.AuditorAware;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;
import java.util.Optional;

@Configuration
public class ApplicationConfig {

    @Bean
    public AuditorAware<String> auditorAware() {
        /*
          if you are using spring security, you can get the currently logged username with following code segment.
          SecurityContextHolder.getContext().getAuthentication().getName()
         */
        return () -> Optional.ofNullable("temporary_user");
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource
                = new ReloadableResourceBundleMessageSource();

        messageSource.setBasename("classpath:messages\\messages");
        messageSource.setDefaultEncoding("UTF-8");
        Locale locale = new Locale("vn", "VN");
        messageSource.setDefaultLocale(locale);
        return messageSource;
    }

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver localeResolver = new SessionLocaleResolver();
        Locale locale = new Locale("vn", "VN");
        localeResolver.setDefaultLocale(locale);
        return localeResolver;
    }

    @Bean
    public LocalValidatorFactoryBean getValidator() {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource());
        return bean;
    }
}
