package org.ht.id.profile.data.repository;

import org.bson.types.ObjectId;
import org.ht.id.common.constant.UserStatus;
import org.ht.id.profile.data.model.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfileRepository extends MongoRepository<Profile, String> {
    Optional<Profile> findByHtId(String htId);

    boolean existsByHtId(String htId);

    void deleteByHtId(String htId);

    boolean existsByHtCodeInAndStatus(List<ObjectId> htCodes, UserStatus status);
}