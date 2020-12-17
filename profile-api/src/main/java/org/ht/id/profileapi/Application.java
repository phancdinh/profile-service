package org.ht.id.profileapi;

import org.ht.id.account.config.EnableAccountMgmtModule;
import org.ht.id.profile.config.EnableProfileMgmtModule;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@SpringBootApplication(scanBasePackages = {"org.ht.profileapi", "org.ht.email"})
@EnableProfileMgmtModule
@EnableAccountMgmtModule
@EnableFeignClients(basePackages = {"org.ht.external.user", "org.ht.account.external.service.bitly"})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
            String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
        };
    }
}
