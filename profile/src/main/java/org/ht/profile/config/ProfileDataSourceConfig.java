package org.ht.profile.config;

import com.mongodb.client.MongoClients;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.domain.EntityScanner;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.annotation.Persistent;
import org.springframework.data.mapping.model.FieldNamingStrategy;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.core.convert.DbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.Collections;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(MongoDbProperties.class)
@EnableMongoRepositories(basePackages = {"org.ht.profile.data.repository"}, mongoTemplateRef = "profileMongoTemplate")
@EnableMongoAuditing
@ComponentScan("org.ht.profile.*")
@EntityScan("org.ht.profile.data.model")
public class ProfileDataSourceConfig {

    private final MongoDbProperties mongoDbProperties;
    private final ApplicationContext applicationContext;

    @Bean(name = "profileMongoTemplate")
    public MongoTemplate profileMongoTemplate() throws Exception {
        MongoDatabaseFactory factory = mongoDbFactory(this.mongoDbProperties.getMongoProperties());
        MongoCustomConversions conversions = new MongoCustomConversions(Collections.emptyList());
        MongoConverter converter = mappingMongoConverter(factory, conversions, mongoMappingContext(conversions));
        return new MongoTemplate(factory, converter);
    }

    public MongoDatabaseFactory mongoDbFactory(final MongoProperties mongoProperties) {
        return new SimpleMongoClientDatabaseFactory(MongoClients.create(mongoProperties.getUri()), mongoProperties.getDatabase());
    }

    public MongoMappingContext mongoMappingContext(MongoCustomConversions conversions)
            throws ClassNotFoundException {
        MongoMappingContext context = new MongoMappingContext();
        context.setInitialEntitySet(new EntityScanner(this.applicationContext)
                .scan(Document.class, Persistent.class));
        Class<?> strategyClass = this.mongoDbProperties.getMongoProperties().getFieldNamingStrategy();
        if (strategyClass != null) {
            context.setFieldNamingStrategy(
                    (FieldNamingStrategy) BeanUtils.instantiateClass(strategyClass));
        }
        context.setSimpleTypeHolder(conversions.getSimpleTypeHolder());
        return context;
    }

    public MappingMongoConverter mappingMongoConverter(MongoDatabaseFactory databaseFactory, MongoCustomConversions customConversions, MongoMappingContext mappingContext) {
        DbRefResolver dbRefResolver = new DefaultDbRefResolver(databaseFactory);
        MappingMongoConverter converter = new MappingMongoConverter(dbRefResolver, mappingContext);
        converter.setCustomConversions(customConversions);
        converter.setCodecRegistryProvider(databaseFactory);
        return converter;
    }

}
