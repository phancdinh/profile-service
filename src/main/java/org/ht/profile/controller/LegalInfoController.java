package org.ht.profile.controller;

import org.ht.profile.authority.Role;
import org.ht.profile.bizservice.LegalInfoBizService;
import org.ht.profile.dto.request.LegalInfoCreateRequest;
import org.ht.profile.dto.response.LegalInfoResponse;
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
public class LegalInfoController {

    private final LegalInfoBizService legalInfoBizService;

    public LegalInfoController(LegalInfoBizService legalInfoBizService) {
        this.legalInfoBizService = legalInfoBizService;
    }

    @GetMapping(value = "/{htId}/legal-info")
    @PreAuthorize(Role.ContactInfo.VIEW)
    public ResponseEntity<LegalInfoResponse> findOne(@PathVariable String htId) {
        LegalInfoResponse result = legalInfoBizService.findByHtId(htId);
        return ResponseEntity.ok(result);
    }

    @PostMapping(value = {"/{htId}/legal-info"})
    @PreAuthorize(Role.LegalInfo.MANAGE)
    public ResponseEntity<LegalInfoResponse> createLegalInfo(@PathVariable String htId,
                                                             @Valid @RequestBody LegalInfoCreateRequest request) {
        LegalInfoResponse result = legalInfoBizService.create(htId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }
}
