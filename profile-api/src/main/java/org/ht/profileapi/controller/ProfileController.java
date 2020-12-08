package org.ht.profileapi.controller;

import org.ht.profileapi.dto.request.ProfileCreateRequest;
import org.ht.profileapi.dto.response.ProfileResponse;
import org.ht.profileapi.facade.ProfileInfoFacade;
import org.ht.profileapi.dto.request.BasicInfoCreateRequest;
import org.ht.profileapi.dto.response.BasicInfoResponse;
import org.ht.profileapi.authority.Role;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "api/profiles")
public class ProfileController {

    private final ProfileInfoFacade profileInfoFacade;

    public ProfileController(ProfileInfoFacade profileInfoFacade) {
        this.profileInfoFacade = profileInfoFacade;
    }

    @GetMapping(value = "/{htId}/basic-info")
    @PreAuthorize(Role.BasicInfo.VIEW)
    public ResponseEntity<BasicInfoResponse> findOne(@PathVariable String htId) {
        BasicInfoResponse p = profileInfoFacade.find(htId);
        return ResponseEntity.ok(p);
    }

    @PostMapping(value = "/{htId}/basic-info")
    @PreAuthorize(Role.BasicInfo.MANAGE)
    public ResponseEntity<BasicInfoResponse> create(@PathVariable String htId, @Valid @RequestBody BasicInfoCreateRequest profile) {
        BasicInfoResponse createdProfile = profileInfoFacade.create(htId, profile);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProfile);
    }

    @PostMapping(value = "")
    @PreAuthorize(Role.BasicInfo.MANAGE)
    public ResponseEntity<ProfileResponse> createProfile(@Valid @RequestBody ProfileCreateRequest profile) {
        ProfileResponse response = profileInfoFacade.createProfileFromCertainInfo(profile);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping(value = "/{htId}")
    @PreAuthorize(Role.BasicInfo.MANAGE)
    public ResponseEntity<ProfileCreateRequest> deleteProfile(@PathVariable String htId) {
        profileInfoFacade.deleteProfile(htId);
        return ResponseEntity.noContent().build();
    }
}
