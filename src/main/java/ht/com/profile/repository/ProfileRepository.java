package ht.com.profile.repository;

import ht.com.profile.model.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProfileRepository extends MongoRepository<Profile, String> {
    Profile findByUserName(String userName);
}
