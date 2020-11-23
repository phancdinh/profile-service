package org.ht.profile.data.repository;

import org.bson.types.ObjectId;
import org.ht.profile.data.model.BasicInfo;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface BasicInfoRepository extends MongoRepository<BasicInfo, String> {
    Optional<BasicInfo> findByProfileId(ObjectId profileId);

    boolean existsByProfileId(ObjectId profileId);
}
