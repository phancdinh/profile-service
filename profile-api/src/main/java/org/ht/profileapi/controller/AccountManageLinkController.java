package org.ht.profileapi.controller;

import org.ht.profileapi.dto.response.ActivationResponse;
import org.ht.profileapi.dto.response.InvitationResponse;
import org.ht.profileapi.facade.AccountManageLinkFacade;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/accounts")
public class AccountManageLinkController {

    private final AccountManageLinkFacade accMgmLinkService;

    public AccountManageLinkController(AccountManageLinkFacade accMgmLinkService) {
        this.accMgmLinkService = accMgmLinkService;
    }

    @PostMapping(value = "/activation")
    public ResponseEntity<ActivationResponse> generateActivationLink(
            @RequestParam(name = "htId", required = true) String htId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(accMgmLinkService.generateActivationLink(htId));
    }

    @GetMapping(value = "/activation")
    public ResponseEntity<ActivationResponse> getActivationLink(
            @RequestParam(name = "htId", required = true) String htId,
            @RequestParam(name = "valid", required = true) String valid) {
        return ResponseEntity.status(HttpStatus.OK).body(accMgmLinkService.getActivationLink(htId, valid));
    }

    @PostMapping(value = "/invitation")
    public ResponseEntity<InvitationResponse> generateInvitationLink(
            @RequestParam(name = "htId", required = true) String htId,
            @RequestParam(name = "contact", required = true) String contact) {
        return ResponseEntity.status(HttpStatus.CREATED).body(accMgmLinkService.generateInvitationLink(htId, contact));
    }

    @GetMapping(value = "/invitation")
    public ResponseEntity<InvitationResponse> getInvitationLink(
            @RequestParam(name = "htId", required = true) String htId,
            @RequestParam(name = "valid", required = true) String valid) {
        return ResponseEntity.status(HttpStatus.OK).body(accMgmLinkService.getInvitationLink(htId, valid));
    }

}
