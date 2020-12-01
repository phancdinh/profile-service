package org.ht.profile.config;

import lombok.Data;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "profile.datasource")
public class MongoDbProperties {
    private MongoProperties mongoProperties = new MongoProperties();
}