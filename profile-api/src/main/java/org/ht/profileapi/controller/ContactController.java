package org.ht.profileapi.controller;

import org.ht.profileapi.authority.Role;
import org.ht.profileapi.dto.request.ContactInfoCreateRequest;
import org.ht.profileapi.dto.response.ContactInfoResponse;
import org.ht.profileapi.facade.ContactInfoService;
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
public class ContactController {

    private final ContactInfoService contactInfoService;

    public ContactController(ContactInfoService contactInfoService) {
        this.contactInfoService = contactInfoService;
    }

    @GetMapping(value = "/{htId}/contact")
    @PreAuthorize(Role.ContactInfo.VIEW)
    public ResponseEntity<ContactInfoResponse> findOne(@PathVariable String htId) {
        ContactInfoResponse p = contactInfoService.findByHtId(htId);
        return ResponseEntity.ok(p);
    }

    @PostMapping(value = "/{htId}/contact")
    @PreAuthorize(Role.ContactInfo.MANAGE)
    public ResponseEntity<ContactInfoResponse> create(@PathVariable String htId, @Valid @RequestBody ContactInfoCreateRequest profile) {
        ContactInfoResponse createdProfile = contactInfoService.create(htId, profile);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProfile);
    }
}
