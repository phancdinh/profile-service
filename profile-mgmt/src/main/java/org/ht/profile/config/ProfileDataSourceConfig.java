package org.ht.profile.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(MongoDbProperties.class)
@EnableMongoRepositories(basePackages = {"org.ht.profile.data.repository"}, mongoTemplateRef = "profileMongoTemplate")
@EnableMongoAuditing
@EntityScan("org.ht.profile.data.model")
public class ProfileDataSourceConfig {

    private final MongoDbProperties mongoDbProperties;

    @Primary
    @Bean(name = "profileMongoTemplate")
    public MongoTemplate profileMongoTemplate() throws Exception {
        return new MongoTemplate(profileMongoFactory(this.mongoDbProperties.getMongoProperties()));

    }

    @Primary
    @Bean
    public MongoDatabaseFactory profileMongoFactory(final MongoProperties mongo) {
        return new SimpleMongoClientDatabaseFactory(mongo.getUri());
    }
}
