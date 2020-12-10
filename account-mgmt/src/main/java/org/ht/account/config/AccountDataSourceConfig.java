package org.ht.account.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(AccountMongoDbProperties.class)
@EnableMongoRepositories(basePackages = {"org.ht.account.data.repository"}, mongoTemplateRef = "accountMongoTemplate")
@EnableMongoAuditing
@EntityScan("org.ht.account.data.model")
public class AccountDataSourceConfig {

    private final AccountMongoDbProperties mongoDbProperties;

    @Bean(name = "accountMongoTemplate")
    public MongoTemplate accountMongoTemplate() throws Exception {
        return new MongoTemplate(accountMongoFactory(this.mongoDbProperties.getMongoProperties()));
    }

    @Bean
    public MongoDatabaseFactory accountMongoFactory(final MongoProperties mongo) throws Exception {
        return new SimpleMongoClientDatabaseFactory(mongo.getUri());
    }
}
