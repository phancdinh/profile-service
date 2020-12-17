package org.ht.id.profile.data.repository;

import org.bson.types.ObjectId;
import org.ht.id.profile.data.model.LegalInfo;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface LegalInfoRepository extends MongoRepository<LegalInfo, String> {
    Optional<LegalInfo> findByHtCode(ObjectId htCode);
    boolean existsByHtCode(ObjectId htCode);
}
