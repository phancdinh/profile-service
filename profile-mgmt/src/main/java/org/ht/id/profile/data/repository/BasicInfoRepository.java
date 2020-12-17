package org.ht.id.profile.data.repository;

import org.bson.types.ObjectId;
import org.ht.id.profile.data.model.BasicInfo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BasicInfoRepository extends MongoRepository<BasicInfo, String> {
    Optional<BasicInfo> findByHtCode(ObjectId htCode);

    boolean existsByHtCode(ObjectId htCode);
}
