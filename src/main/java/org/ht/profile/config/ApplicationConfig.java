package org.ht.profile.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.data.domain.AuditorAware;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
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
    public LocalValidatorFactoryBean getValidator() {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource());
        return bean;
    }
}
