package org.ht.profile.data.repository;

import org.bson.types.ObjectId;
import org.ht.common.constant.DemoGraphicsAttribute;
import org.ht.profile.data.model.DemoGraphicsInfo;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface DemoGraphicsInfoRepository extends MongoRepository<DemoGraphicsInfo, String> {
    Optional<DemoGraphicsInfo> findByHtCodeAndAttribute(ObjectId htCode, DemoGraphicsAttribute demoGraphicsInfoAttribute);

    boolean existsByHtCodeAndAttribute(ObjectId htCode, DemoGraphicsAttribute demoGraphicsInfoAttribute);

    void deleteByHtCodeAndAttribute(ObjectId htCode, DemoGraphicsAttribute demoGraphicsInfoAttribute);
}
