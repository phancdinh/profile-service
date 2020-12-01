package org.ht.profile.data.repository;

import org.bson.types.ObjectId;
import org.ht.profile.constants.DemoGraphicsInfoAttribute;
import org.ht.profile.data.model.DemoGraphicsInfo;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface DemoGraphicsInfoRepository extends MongoRepository<DemoGraphicsInfo, String> {
    Optional<DemoGraphicsInfo> findByProfileIdAndAttribute(ObjectId profileId, DemoGraphicsInfoAttribute demoGraphicsInfoAttribute);

    boolean existsByProfileIdAndAttribute(ObjectId profileId, DemoGraphicsInfoAttribute demoGraphicsInfoAttribute);

    void deleteByProfileIdAndAttribute(ObjectId profileId, DemoGraphicsInfoAttribute demoGraphicsInfoAttribute);
}
