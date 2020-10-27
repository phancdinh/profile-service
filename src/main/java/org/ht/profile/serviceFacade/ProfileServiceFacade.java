package org.ht.profile.serviceFacade;

import org.ht.profile.dto.ProfileResponse;
import org.ht.profile.dto.request.ProfileCreateRequest;
import org.ht.profile.dto.request.ProfileUpdateRequest;

import java.util.List;


public interface ProfileServiceFacade {
    ProfileResponse create(ProfileCreateRequest p);

    ProfileResponse findById(String id);

    List<ProfileResponse> findAll();

    ProfileResponse update(String hiId, ProfileUpdateRequest p);

    void delete(String htId);

    ProfileResponse find(String htId);
}
