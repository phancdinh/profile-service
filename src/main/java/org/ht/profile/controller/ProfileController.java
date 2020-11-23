package org.ht.profile.controller;

import org.ht.profile.bizservice.ProfileBizService;
import org.ht.profile.dto.request.BasicInfoCreateRequest;
import org.ht.profile.dto.response.BasicInfoResponse;
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

import static org.ht.profile.authority.Role.BasicInfo.MANAGE;
import static org.ht.profile.authority.Role.BasicInfo.VIEW;

@RestController
@RequestMapping(value = "api/profiles")
public class ProfileController {

    private final ProfileBizService profileBizService;

    public ProfileController(ProfileBizService profileBizService) {
        this.profileBizService = profileBizService;
    }

    @GetMapping(value = "/{htId}")
    @PreAuthorize(VIEW)
    public ResponseEntity<BasicInfoResponse> findOne(@PathVariable String htId) {
        BasicInfoResponse p = profileBizService.find(htId);
        return ResponseEntity.ok(p);
    }

    @PostMapping(value = {""})
    @PreAuthorize(MANAGE)
    public ResponseEntity<BasicInfoResponse> create(@Valid @RequestBody BasicInfoCreateRequest profile) {
        BasicInfoResponse createdProfile = profileBizService.create(profile);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProfile);
    }


}
