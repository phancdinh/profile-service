package org.ht.profile.data.repository;

import org.ht.profile.data.model.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileRepository extends MongoRepository<Profile, String> {
    Optional<Profile> findByHtId(String htId);

    boolean existsByHtId(String htId);

    void deleteByHtId(String htId);

}
