package org.ht.profile.converter;

import org.ht.profile.dto.ProfileResponse;
import org.ht.profile.dto.request.ProfileCreateRequest;
import org.ht.profile.model.Profile;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class ProfileConverter {
    public Profile convert(ProfileCreateRequest profileRequest) {
        Profile profile = new Profile();
        BeanUtils.copyProperties(profileRequest, profile);
        return profile;
    }

    public ProfileResponse convert(Profile profile) {
        ProfileResponse profileResponse = new ProfileResponse();
        BeanUtils.copyProperties(profile, profileResponse);
        return profileResponse;
    }
}
