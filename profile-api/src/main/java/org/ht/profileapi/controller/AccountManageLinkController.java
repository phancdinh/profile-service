package org.ht.profileapi.controller;

import org.ht.account.dto.response.ResponseData;
import org.ht.account.dto.response.ResponseStatus;
import org.ht.profileapi.authority.Role;
import org.ht.profileapi.facade.AccountFacade;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "api/accounts")
public class AccountManageLinkController {

    private final AccountFacade accountFacade;

    public AccountManageLinkController(AccountFacade accountFacade) {
        this.accountFacade = accountFacade;
    }

    @GetMapping(value = "/{htId}/activation")
    @PreAuthorize(Role.BasicInfo.VIEW)
    public ResponseEntity<ResponseData> getActLink(@RequestParam(name = "htId", required = true) String htId,
                                                   @RequestParam(name = "prefixUrl", required = true) String prefixUrl) {

        ResponseData response = accountFacade.getActLink(htId, prefixUrl);
        log.info(response.getMessage());

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }

    @PatchMapping(value = "/{htId}/activation/verification")
    @PreAuthorize(Role.ContactInfo.VIEW)
    public ResponseEntity<ResponseData> verifyActLink(@RequestParam(name = "htId", required = true) String htId,
                                                      @RequestParam(name = "url", required = true) String url) {

        ResponseData response = new ResponseData();
        response = accountFacade.verifyActLink(htId, url);
        log.info("Validate user: " + htId + ", result: " + response);

        if (response.getStatus() == ResponseStatus.SUCCESS) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }
    }


    @GetMapping(value = "/{htId}/invitation")
    @PreAuthorize(Role.ContactInfo.VIEW)
    public ResponseEntity<ResponseData> getInvtLink(@RequestParam(name = "htId", required = true) String htId
            , @RequestParam(name = "prefixUrl", required = true) String prefixUrl
            , @RequestParam(name = "contact", required = true) String contact) {


        ResponseData response = accountFacade.getInvtLink(htId, prefixUrl, contact);
        log.info(response.getMessage());

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }

    @PatchMapping(value = "/{htId}/invitation/verification")
    @PreAuthorize(Role.ContactInfo.VIEW)
    public ResponseEntity<ResponseData> verifyInvtLink(@RequestParam(name = "htId", required = true) String htId
            , @RequestParam(name = "url", required = true) String url
            , @RequestParam(name = "contact", required = true) String contact) {

        ResponseData p = accountFacade.verifyInvtLink(htId, url, contact);
        log.info("Validate user: " + htId + ", result: " + p);

        if (p.getStatus() == ResponseStatus.SUCCESS) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(p);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(p);
        }
    }
}
