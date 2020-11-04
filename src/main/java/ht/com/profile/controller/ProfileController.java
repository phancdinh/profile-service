package ht.com.profile.controller;

import ht.com.profile.authority.Role;
import ht.com.profile.model.Profile;
import ht.com.profile.service.ProfileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping(value = "api")
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping(value = "/profiles/")
    @PreAuthorize(Role.Profile.VIEW)
    public ResponseEntity<List<Profile>> list() {
        List<Profile> profiles = profileService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(profiles);
    }

    @GetMapping(value = "/profiles/{id}")
    @PreAuthorize(Role.Profile.VIEW)
    public ResponseEntity<Profile> findOne(@PathVariable String id) {
        Profile p = profileService.findById(id);
        return ResponseEntity.ok(p);
    }

    @PostMapping(value = "/profiles")
    @PreAuthorize(Role.Profile.MANAGE)
    public ResponseEntity<Profile> save(Profile p) {
        Profile createdProfile = profileService.create(p);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProfile);
    }

    @PutMapping(value = "/profiles/{id}")
    @PreAuthorize(Role.Profile.MANAGE)
    public ResponseEntity<Profile> update(@PathVariable String id, Profile p) {
        Profile updatedProfile = profileService.update(id, p);
        return ResponseEntity.ok(updatedProfile);
    }

    @DeleteMapping(value = "/profiles/{id}")
    @PreAuthorize(Role.Profile.MANAGE)
    public ResponseEntity delete(@PathVariable String id) {
        profileService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
