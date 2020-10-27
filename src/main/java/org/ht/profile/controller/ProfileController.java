package org.ht.profile.controller;

import org.ht.profile.authority.Role;
import org.ht.profile.dto.ProfileResponse;
import org.ht.profile.dto.request.ProfileCreateRequest;
import org.ht.profile.dto.request.ProfileUpdateRequest;
import org.ht.profile.serviceFacade.ProfileServiceFacade;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping(value = "api/profiles")
public class ProfileController {

    private final ProfileServiceFacade profileServiceFacade;

    public ProfileController(ProfileServiceFacade profileServiceFacade) {
        this.profileServiceFacade = profileServiceFacade;
    }

    @GetMapping(value = {""})
    @PreAuthorize(Role.Profile.VIEW)
    public ResponseEntity<List<ProfileResponse>> list() {
        List<ProfileResponse> profiles = profileServiceFacade.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(profiles);
    }

    @GetMapping(value = "/{htId}")
    @PreAuthorize(Role.Profile.VIEW)
    public ResponseEntity<ProfileResponse> findOne(@PathVariable String htId) {
        ProfileResponse p = profileServiceFacade.find(htId);
        return ResponseEntity.ok(p);
    }

    @PostMapping(value = {""})
    @PreAuthorize(Role.Profile.MANAGE)
    public ResponseEntity<ProfileResponse> create(ProfileCreateRequest p) {
        ProfileResponse createdProfile = profileServiceFacade.create(p);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProfile);
    }

    @PutMapping(value = "/{htId}")
    @PreAuthorize(Role.Profile.MANAGE)
    public ResponseEntity<ProfileResponse> update(@PathVariable String htId, ProfileUpdateRequest profile) {
        ProfileResponse updatedProfile = profileServiceFacade.update(htId, profile);
        return ResponseEntity.ok(updatedProfile);
    }

    @DeleteMapping(value = "/{htId}")
    @PreAuthorize(Role.Profile.MANAGE)
    public ResponseEntity delete(@PathVariable String htId) throws Exception {
        profileServiceFacade.delete(htId);
        return ResponseEntity.noContent().build();
    }

}
