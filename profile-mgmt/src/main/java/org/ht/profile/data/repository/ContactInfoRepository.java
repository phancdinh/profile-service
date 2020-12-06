package org.ht.profile.data.repository;

import org.bson.types.ObjectId;
import org.ht.profile.data.model.ContactInfo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ContactInfoRepository extends MongoRepository<ContactInfo, String> {
    Optional<ContactInfo> findByHtCode(ObjectId htCode);

    @Query("{$and: [{\"emails.value\": ?0}, {\"emails.primary\": true} ] }")
    List<ContactInfo> findByEmailAndPrimary(String email);

    boolean existsByHtCode(ObjectId htCode);
}
