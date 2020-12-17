package org.ht.id.profile.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan({"org.ht.id.profile", "org.ht.id.external.user"})
@Import(ProfileDataSourceConfig.class)
public class ProfileModuleConfiguration {

    @Bean(name = "profileMapper")
    protected ModelMapper profileMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        return modelMapper;
    }
}
