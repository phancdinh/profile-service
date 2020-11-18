package org.ht.profile.repository;

import org.bson.types.ObjectId;
import org.ht.profile.model.BasicInfo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BasicInfoRepository extends MongoRepository<BasicInfo, String> {
    BasicInfo findByProfileId(ObjectId profileId);

    boolean existsByProfileId(ObjectId profileId);
}
