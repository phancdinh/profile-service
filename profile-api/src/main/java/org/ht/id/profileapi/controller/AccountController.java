package org.ht.id.profileapi.controller;

import org.ht.id.profileapi.dto.request.AccountCreationRequest;
import org.ht.id.profileapi.dto.response.AccountResponse;
import org.ht.id.profileapi.facade.AccountFacade;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "api/accounts")
public class AccountController {

    private final AccountFacade accountFacade;

    public AccountController(AccountFacade accountFacade) {
        this.accountFacade = accountFacade;
    }

    @PostMapping
    public ResponseEntity<AccountResponse> create(@Valid @RequestBody AccountCreationRequest request) {
        AccountResponse result = accountFacade.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping
    @RequestMapping(value = "/email-validation")
    public boolean checkEmailHasRegistered(@Valid @RequestParam String email) {
        return accountFacade.checkEmailHasRegistered(email);
    }
}
