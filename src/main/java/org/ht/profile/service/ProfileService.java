package org.ht.profile.service;

import lombok.extern.slf4j.Slf4j;
import org.ht.profile.model.Profile;
import org.ht.profile.repository.ProfileRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@Slf4j
@Service
public class ProfileService {

    private final ProfileRepository profileRepository;

    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public List<Profile> findAll() {
        return profileRepository.findAll();
    }

    public Profile findByHtId(String htId) {
        Profile profile = profileRepository.findByHtId(htId);
        if (profile == null) {
            String error = String.format("Profile is  not existed with htId: %s", htId);
            log.error(error);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, error);
        }
        return profile;
    }

    public Profile create(String htId) {
        if (profileRepository.existsByHtId(htId)) {
            String error = String.format("Profile is already existed with %s", htId);
            log.error(error);
            throw new ResponseStatusException(HttpStatus.CONFLICT, error);
        }
        Profile addedProfile = new Profile();
        addedProfile.setHtId(htId);
        return profileRepository.insert(addedProfile);
    }

    public void delete(String htId) {
        Profile profile = findByHtId(htId);
        profile.setHtId("");
        profileRepository.save(profile);
    }

}
