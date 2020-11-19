package org.ht.profile.controller;

import org.ht.profile.authority.Role;
import org.ht.profile.dto.response.BasicInfoResponse;
import org.ht.profile.dto.request.BasicInfoCreateRequest;
import org.ht.profile.serviceFacade.ProfileServiceFacade;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    private final ProfileServiceFacade profileServiceFacade;

    public ProfileController(ProfileServiceFacade profileServiceFacade) {
        this.profileServiceFacade = profileServiceFacade;
    }

    @GetMapping(value = "/{htId}")
    @PreAuthorize(Role.BasicInfo.VIEW)
    public ResponseEntity<BasicInfoResponse> findOne(@PathVariable String htId) {
        BasicInfoResponse p = profileServiceFacade.find(htId);
        return ResponseEntity.ok(p);
    }

    @PostMapping(value = {""})
    @PreAuthorize(Role.BasicInfo.MANAGE)
    public ResponseEntity<BasicInfoResponse> create(@Valid @RequestBody BasicInfoCreateRequest profile) {
        BasicInfoResponse createdProfile = profileServiceFacade.create(profile);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProfile);
    }


}
