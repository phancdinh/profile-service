package org.ht.profile.data.repository;

import org.bson.types.ObjectId;
import org.ht.common.constant.DemoGraphicsAttribute;
import org.ht.profile.data.model.DemoGraphicsInfo;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface DemoGraphicsInfoRepository extends MongoRepository<DemoGraphicsInfo, String> {
    Optional<DemoGraphicsInfo> findByProfileIdAndAttribute(ObjectId profileId, DemoGraphicsAttribute demoGraphicsInfoAttribute);

    boolean existsByProfileIdAndAttribute(ObjectId profileId, DemoGraphicsAttribute demoGraphicsInfoAttribute);

    void deleteByProfileIdAndAttribute(ObjectId profileId, DemoGraphicsAttribute demoGraphicsInfoAttribute);
}
