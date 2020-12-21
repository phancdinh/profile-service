package org.ht.id.profileapi.controller;

import lombok.AllArgsConstructor;
import org.ht.id.profileapi.dto.request.ActivationCreateRequest;
import org.ht.id.profileapi.dto.request.ActivationUpdateRequest;
import org.ht.id.profileapi.dto.request.InvitationCreateRequest;
import org.ht.id.profileapi.dto.request.InvitationUpdateRequest;
import org.ht.id.profileapi.dto.response.ActivationCreateResponse;
import org.ht.id.profileapi.dto.response.ActivationUpdateResponse;
import org.ht.id.profileapi.dto.response.InvitationCreateResponse;
import org.ht.id.profileapi.dto.response.InvitationUpdateResponse;
import org.ht.id.profileapi.facade.ActivationFacade;
import org.ht.id.profileapi.facade.InvitationFacade;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/accounts")
@AllArgsConstructor
public class AccountManageLinkController {

    private final ActivationFacade activationService;
    private final InvitationFacade invitationService;

    @PostMapping(value = "/activation")
    public ResponseEntity<ActivationCreateResponse> createActivation(@RequestBody ActivationCreateRequest activationCreateRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(activationService.createActivation(activationCreateRequest));
    }

    @PutMapping(value = "/activation")
    public ResponseEntity<ActivationUpdateResponse> updateActivation(@RequestBody ActivationUpdateRequest activationUpdateRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(activationService.updateActivation(activationUpdateRequest));
    }

    @PostMapping(value = "/invitation")
    public ResponseEntity<InvitationCreateResponse> createInvitation(@RequestBody InvitationCreateRequest invitationCreateRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(invitationService.createInvitation(invitationCreateRequest));
    }

    @PutMapping(value = "/invitation")
    public ResponseEntity<InvitationUpdateResponse> updateInvitation(@RequestBody InvitationUpdateRequest invitationUpdateRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(invitationService.updateInvitation(invitationUpdateRequest));
    }

}
