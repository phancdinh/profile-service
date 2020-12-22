package org.ht.id.profileapi.controller;

import org.ht.id.profileapi.dto.request.ContactEmailCreateRequest;
import org.ht.id.profileapi.dto.request.ContactInfoCreateRequest;
import org.ht.id.profileapi.dto.response.ContactEmailsResponse;
import org.ht.id.profileapi.dto.response.ContactInfoResponse;
import org.ht.id.profileapi.dto.response.internal.HierarchyContactResponse;
import org.ht.id.profileapi.facade.ContactInfoFacade;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    private final ContactInfoFacade contactInfoFacade;

    public ContactController(ContactInfoFacade contactInfoFacade) {
        this.contactInfoFacade = contactInfoFacade;
    }

    @GetMapping(value = "/{htId}/contacts")
    public ResponseEntity<ContactInfoResponse> findOne(@PathVariable String htId) {
        ContactInfoResponse p = contactInfoFacade.findByHtId(htId);
        return ResponseEntity.ok(p);
    }

    @PostMapping(value = "/{htId}/contacts")
    public ResponseEntity<ContactInfoResponse> create(@PathVariable String htId, @Valid @RequestBody ContactInfoCreateRequest profile) {
        ContactInfoResponse createdProfile = contactInfoFacade.create(htId, profile);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProfile);
    }

    @GetMapping(value = "/info/{htId}/contacts/emails")
    public ResponseEntity<ContactEmailsResponse> findContactEmails(@PathVariable String htId) {
        ContactInfoResponse contactInfo = contactInfoFacade.findByHtId(htId);
        ContactEmailsResponse emailsResponse = new ContactEmailsResponse();
        emailsResponse.setEmails(contactInfo.getEmails());
        return ResponseEntity.ok(emailsResponse);
    }

    @PostMapping(value = "/info/{htId}/contacts/emails")
    public ResponseEntity<HierarchyContactResponse> createContactEmail(@PathVariable String htId, @Valid @RequestBody ContactEmailCreateRequest request) {
        HierarchyContactResponse createdEmail = contactInfoFacade.createContactEmail(htId, request.getValue());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEmail);
    }
}