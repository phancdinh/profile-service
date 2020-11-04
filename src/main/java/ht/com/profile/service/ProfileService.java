package ht.com.profile.service;

import ht.com.profile.model.Profile;
import ht.com.profile.repository.ProfileRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfileService {

    private final ProfileRepository profileRepository;

    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public List<Profile> findAll() {
        return profileRepository.findAll();
    }

    public Profile findById(String id) {
        return profileRepository.findById(id).orElse(null);
    }

    public Profile findByUserName(String userName) {
        return profileRepository.findByUserName(userName);
    }

    public Profile create(Profile p) {
        return profileRepository.insert(p);
    }

    public Profile update(String id, Profile updatedProfile) {
        Profile p = findById(id);
        return profileRepository.save(p);
    }

    public void delete(String id) {
        profileRepository.deleteById(id);
    }

}
