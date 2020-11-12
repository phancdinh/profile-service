package org.ht.profile.serviceFacade.impl;

import org.ht.profile.converter.ProfileConverter;
import org.ht.profile.dto.ProfileResponse;
import org.ht.profile.dto.request.ProfileCreateRequest;
import org.ht.profile.dto.request.ProfileUpdateRequest;
import org.ht.profile.model.Profile;
import org.ht.profile.service.ProfileService;
import org.ht.profile.serviceFacade.ProfileServiceFacade;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProfileServiceFacadeImpl implements ProfileServiceFacade {

    private final ProfileService profileService;
    private final ProfileConverter profileConverter;

    public ProfileServiceFacadeImpl(ProfileService profileService, ProfileConverter profileConverter) {
        this.profileService = profileService;
        this.profileConverter = profileConverter;
    }

    @Override
    public ProfileResponse create(ProfileCreateRequest profileRequest) {
        return profileConverter.convert(profileService.create(profileRequest));
    }

    @Override
    public ProfileResponse findById(String id) {
        return profileConverter.convert(profileService.findById(id));
    }

    @Override
    public ProfileResponse find(String htId) {
        return profileConverter.convert(profileService.findByHtId(htId));
    }

    @Override
    public List<ProfileResponse> findAll() {
        List<Profile> list = profileService.findAll();
        return list.stream().map(profileConverter::convert).collect(Collectors.toList());
    }

    @Override
    public ProfileResponse update(String id, ProfileUpdateRequest profileRequest) {
        return profileConverter.convert(profileService.update(id, profileRequest));
    }

    @Override
    public void delete(String id) {
        profileService.delete(id);
    }

}
