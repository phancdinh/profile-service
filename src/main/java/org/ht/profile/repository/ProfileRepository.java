package org.ht.profile.repository;

import org.ht.profile.model.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProfileRepository extends MongoRepository<Profile, String> {
    Profile findByHtId(String htId);

    boolean existsByHtId(String htId);
}
