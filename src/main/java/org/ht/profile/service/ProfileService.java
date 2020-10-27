package org.ht.profile.service;

import org.ht.profile.converter.ProfileConverter;
import org.ht.profile.dto.request.ProfileCreateRequest;
import org.ht.profile.dto.request.ProfileUpdateRequest;
import org.ht.profile.model.Profile;
import org.ht.profile.repository.ProfileRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.webjars.NotFoundException;

import java.util.List;

@Service
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final ProfileConverter profileConverter;

    public ProfileService(ProfileRepository profileRepository, ProfileConverter profileConverter) {
        this.profileRepository = profileRepository;
        this.profileConverter = profileConverter;
    }

    public List<Profile> findAll() {
        return profileRepository.findAll();
    }

    public Profile findById(String id) throws NotFoundException{
        Profile profile = profileRepository.findById(id).orElse(null);
        if (profile == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Profile does not exists with id: %s", id));
        }
        return profile;
    }

    public Profile findByHtId(String htId) {
        Profile profile = profileRepository.findByHtId(htId);
        if (profile == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Profile does not exists with htId: %s", htId));
        }
        return profile;
    }

    public Profile findByUserName(String userName) {
        Profile profile = profileRepository.findByUserName(userName);
        if (profile == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Profile does not exists with user name: %s", userName));
        }
        return profile;
    }

    public Profile create(ProfileCreateRequest profileRequest) {
        Profile profile = profileConverter.convert(profileRequest);
        profile.setHtId(profile.getUserName());
        return profileRepository.insert(profile);
    }

    public Profile update(String htId, ProfileUpdateRequest updatedProfile) {
        Profile profile = findByHtId(htId);
        BeanUtils.copyProperties(updatedProfile, profile);
        return profileRepository.save(profile);
    }

    public void delete(String htId) {
        Profile profile = findByHtId(htId);
        profileRepository.delete(profile);
    }

}
