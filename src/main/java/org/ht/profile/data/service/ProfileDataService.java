package org.ht.profile.data.service;

import lombok.extern.slf4j.Slf4j;
import org.ht.profile.data.model.Profile;
import org.ht.profile.data.repository.ProfileRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;


@Slf4j
@Service
public class ProfileDataService {

    private final ProfileRepository profileRepository;

    public ProfileDataService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public List<Profile> findAll() {
        return profileRepository.findAll();
    }

    public Optional<Profile> findByHtId(String htId) {
        return profileRepository.findByHtId(htId);
    }

    public boolean existsByHtId(String htId) {
        return profileRepository.existsByHtId(htId);
    }

    public Profile create(String htId) {
        Profile addedProfile = new Profile();
        addedProfile.setHtId(htId);
        return profileRepository.insert(addedProfile);
    }

}
