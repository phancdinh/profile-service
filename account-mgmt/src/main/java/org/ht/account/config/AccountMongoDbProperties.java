package org.ht.account.config;

import lombok.Data;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "account-mgmt.datasource")
public class AccountMongoDbProperties {
    private MongoProperties mongoProperties = new MongoProperties();
}