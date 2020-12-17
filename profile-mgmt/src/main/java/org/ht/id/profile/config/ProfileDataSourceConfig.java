package org.ht.id.profile.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@RequiredArgsConstructor
@EnableMongoRepositories(basePackages = {"org.ht.profile.data.repository"})
@EnableMongoAuditing
@EntityScan("org.ht.profile.data.model")
public class ProfileDataSourceConfig {
}
