package org.ht.account.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(AccountMongoDbProperties.class)
@EnableMongoRepositories(basePackages = {"org.ht.account.data.repository"}, mongoTemplateRef = "profileMongoTemplate")
@EnableMongoAuditing
@ComponentScan("org.ht.account.*")
@EntityScan("org.ht.account.data.model")
public class AccountDataSourceConfig {
}
