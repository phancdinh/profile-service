package org.ht.profileapi.controller;

import lombok.RequiredArgsConstructor;
import org.ht.profileapi.authority.Role;
import org.ht.profileapi.constants.AccountActionKey;
import org.ht.profileapi.dto.request.BasicInfoCreateRequest;
import org.ht.profileapi.dto.request.ProfileCreateRequest;
import org.ht.profileapi.dto.response.BasicInfoResponse;
import org.ht.profileapi.dto.response.ProfileResponse;
import org.ht.profileapi.dto.response.ResetPasswordResponse;
import org.ht.profileapi.facade.AccountFacade;
import org.ht.profileapi.facade.ProfileInfoFacade;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "api/profiles")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileInfoFacade profileInfoFacade;
    private final AccountFacade accountFacade;

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

    @GetMapping
    @RequestMapping(value = "/accounts")
    public ResponseEntity<ResetPasswordResponse> validateResetPassword(@Valid @RequestParam(name = "ak", required = true) String action, @RequestParam(name = "e", required = true) String email) {
        if (!AccountActionKey.RESET_PASSWORD_AK.equalsIgnoreCase(action)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResetPasswordResponse(false));
        }

        ResetPasswordResponse result = accountFacade.resetPassword(email);
        return ResponseEntity.status(result.isResult() ? HttpStatus.OK : HttpStatus.NOT_FOUND).body(result);
    }
}
