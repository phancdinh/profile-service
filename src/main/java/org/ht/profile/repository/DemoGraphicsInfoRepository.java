package org.ht.profile.repository;

import org.bson.types.ObjectId;
import org.ht.profile.model.DemoGraphicsInfo;
import org.ht.profile.constants.DemoGraphicsInfoAttribute;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DemoGraphicsInfoRepository extends MongoRepository<DemoGraphicsInfo, String> {
    DemoGraphicsInfo findByProfileIdAndAttribute(ObjectId profileId, DemoGraphicsInfoAttribute demoGraphicsInfoAttribute);
    void deleteByProfileIdAndAttribute(ObjectId profileId, DemoGraphicsInfoAttribute demoGraphicsInfoAttribute);
}
