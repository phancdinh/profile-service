package org.ht.id.account.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@RequiredArgsConstructor
@EnableMongoRepositories(basePackages = {"org.ht.account.data.repository"})
@EnableMongoAuditing
@EntityScan("org.ht.account.data.model")
public class AccountDataSourceConfig {
}
