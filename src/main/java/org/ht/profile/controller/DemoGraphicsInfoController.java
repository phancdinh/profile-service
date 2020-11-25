package org.ht.profile.controller;

import org.ht.profile.authority.Role;
import org.ht.profile.bizservice.DemoGraphicsInfoBizService;
import org.ht.profile.constants.DemoGraphicsInfoAttribute;
import org.ht.profile.dto.request.DemoGraphicsInfoCreateRequest;
import org.ht.profile.dto.request.DemoGraphicsInfoUpdateRequest;
import org.ht.profile.dto.response.DemoGraphicsInfoResponse;
import org.ht.profile.dto.response.LegalInfoResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@RequestMapping(value = "api/profiles")
public class DemoGraphicsInfoController {
    private final DemoGraphicsInfoBizService demoGraphicsInfoBizService;

    public DemoGraphicsInfoController(DemoGraphicsInfoBizService demoGraphicsInfoBizService) {
        this.demoGraphicsInfoBizService = demoGraphicsInfoBizService;
    }

    // API For Marital
    @GetMapping(value = "/{htId}/marital-status")
    @PreAuthorize(Role.DemoGraphics.VIEW)
    public ResponseEntity<DemoGraphicsInfoResponse> findMarital(@PathVariable String htId) {
        DemoGraphicsInfoResponse p = demoGraphicsInfoBizService.findByHtIdAndAttribute(htId, DemoGraphicsInfoAttribute.MARITAL_STATUS);
        return ResponseEntity.ok(p);
    }

    @PostMapping(value = {"/{htId}/marital-status"})
    @PreAuthorize(Role.DemoGraphics.MANAGE)
    public ResponseEntity<DemoGraphicsInfoResponse> createMarital(@PathVariable String htId,
                                                                  @Valid @RequestBody DemoGraphicsInfoCreateRequest request) {
        DemoGraphicsInfoResponse createdProfile = demoGraphicsInfoBizService.create(htId, DemoGraphicsInfoAttribute.MARITAL_STATUS, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProfile);
    }

    @PutMapping(value = "/{htId}/marital-status")
    @PreAuthorize(Role.DemoGraphics.MANAGE)
    public ResponseEntity<DemoGraphicsInfoResponse> updateMarital(@PathVariable String htId,
                                                                  @Valid @RequestBody DemoGraphicsInfoUpdateRequest request) {
        DemoGraphicsInfoResponse updatedProfile = demoGraphicsInfoBizService.update(htId, DemoGraphicsInfoAttribute.MARITAL_STATUS, request);
        return ResponseEntity.ok(updatedProfile);
    }

    @DeleteMapping(value = "/{htId}/marital-status")
    @PreAuthorize(Role.DemoGraphics.MANAGE)
    public ResponseEntity<Void> deleteMarital(@PathVariable String htId) {
        demoGraphicsInfoBizService.delete(htId, DemoGraphicsInfoAttribute.MARITAL_STATUS);
        return ResponseEntity.noContent().build();
    }

    // API For Customer Type
    @GetMapping(value = "/{htId}/customer-type-status")
    @PreAuthorize(Role.DemoGraphics.VIEW)
    public ResponseEntity<DemoGraphicsInfoResponse> findCustomerType(@PathVariable String htId) {
        DemoGraphicsInfoResponse p = demoGraphicsInfoBizService.findByHtIdAndAttribute(htId, DemoGraphicsInfoAttribute.CUSTOMER_TYPE_STATUS);
        return ResponseEntity.ok(p);
    }

    @PostMapping(value = {"/{htId}/customer-type-status"})
    @PreAuthorize(Role.DemoGraphics.MANAGE)
    public ResponseEntity<DemoGraphicsInfoResponse> createCustomerType(@PathVariable String htId,
                                                                       @Valid @RequestBody DemoGraphicsInfoCreateRequest request) {
        DemoGraphicsInfoResponse createdProfile = demoGraphicsInfoBizService.create(htId, DemoGraphicsInfoAttribute.CUSTOMER_TYPE_STATUS, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProfile);
    }

    @PutMapping(value = "/{htId}/customer-type-status")
    @PreAuthorize(Role.DemoGraphics.MANAGE)
    public ResponseEntity<DemoGraphicsInfoResponse> updateCustomerType(@PathVariable String htId,
                                                                       @Valid @RequestBody DemoGraphicsInfoUpdateRequest request) {
        DemoGraphicsInfoResponse updatedProfile = demoGraphicsInfoBizService.update(htId, DemoGraphicsInfoAttribute.CUSTOMER_TYPE_STATUS, request);
        return ResponseEntity.ok(updatedProfile);
    }

    @DeleteMapping(value = "/{htId}/customer-type-status")
    @PreAuthorize(Role.DemoGraphics.MANAGE)
    public ResponseEntity<Void> deleteCustomerType(@PathVariable String htId) {
        demoGraphicsInfoBizService.delete(htId, DemoGraphicsInfoAttribute.CUSTOMER_TYPE_STATUS);
        return ResponseEntity.noContent().build();
    }
}
