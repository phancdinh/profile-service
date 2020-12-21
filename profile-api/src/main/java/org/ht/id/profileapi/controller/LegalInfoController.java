package org.ht.id.profileapi.controller;

import javax.validation.Valid;

import org.ht.id.profileapi.authority.Role;
import org.ht.id.profileapi.dto.request.LegalInfoCreateRequest;
import org.ht.id.profileapi.dto.response.LegalInfoResponse;
import org.ht.id.profileapi.facade.LegalInfoFacade;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/profiles")
public class LegalInfoController {

    private final LegalInfoFacade legalInfoFacade;

    public LegalInfoController(LegalInfoFacade legalInfoFacade) {
        this.legalInfoFacade = legalInfoFacade;
    }


    @GetMapping(value = "/{htId}/legal-info")
    @PreAuthorize(Role.LegalInfo.VIEW)
    public ResponseEntity<LegalInfoResponse> findOne(@PathVariable String htId) {
        LegalInfoResponse result = legalInfoFacade.findByHtId(htId);
        return ResponseEntity.ok(result);
    }

    @PostMapping(value = {"/{htId}/legal-info"})
    public ResponseEntity<LegalInfoResponse> createLegalInfo(@PathVariable String htId,
                                                             @Valid @RequestBody LegalInfoCreateRequest request) {
        LegalInfoResponse result = legalInfoFacade.create(htId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }
}
