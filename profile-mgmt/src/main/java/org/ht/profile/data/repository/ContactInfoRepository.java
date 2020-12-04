package org.ht.profile.data.repository;

import org.bson.types.ObjectId;
import org.ht.profile.data.model.ContactInfo;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ContactInfoRepository extends MongoRepository<ContactInfo, String> {
    Optional<ContactInfo> findByHtCode(ObjectId htCode);
    boolean existsByHtCode(ObjectId htCode);
}
