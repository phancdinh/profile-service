package org.ht.profile.controller;

import org.ht.profile.authority.Role;
import org.ht.profile.dto.response.DemoGraphicsInfoResponse;
import org.ht.profile.dto.request.DemoGraphicsInfoCreateRequest;
import org.ht.profile.dto.request.DemoGraphicsInfoUpdateRequest;
import org.ht.profile.constants.DemoGraphicsInfoAttribute;
import org.ht.profile.serviceFacade.DemoGraphicsInfoServiceFacade;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "api/profiles")
public class DemoGraphicsInfoController {
    private final DemoGraphicsInfoServiceFacade demoGraphicsInfoServiceFacade;

    public DemoGraphicsInfoController(DemoGraphicsInfoServiceFacade demoGraphicsInfoServiceFacade) {
        this.demoGraphicsInfoServiceFacade = demoGraphicsInfoServiceFacade;
    }

    // API For Marital
    @GetMapping(value = "/{htId}/marital-status")
    @PreAuthorize(Role.DemoGraphics.VIEW)
    public ResponseEntity<DemoGraphicsInfoResponse> findMarital(@PathVariable String htId) {
        DemoGraphicsInfoResponse p = demoGraphicsInfoServiceFacade.findByHtIdAndAttribute(htId, DemoGraphicsInfoAttribute.MARITAL_STATUS);
        return ResponseEntity.ok(p);
    }

    @PostMapping(value = {"/{htId}/marital-status"})
    @PreAuthorize(Role.DemoGraphics.MANAGE)
    public ResponseEntity<DemoGraphicsInfoResponse> createMarital(@PathVariable String htId, DemoGraphicsInfoCreateRequest request) {
        DemoGraphicsInfoResponse createdProfile = demoGraphicsInfoServiceFacade.create(htId, DemoGraphicsInfoAttribute.MARITAL_STATUS, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProfile);
    }

    @PutMapping(value = "/{htId}/marital-status")
    @PreAuthorize(Role.DemoGraphics.MANAGE)
    public ResponseEntity<DemoGraphicsInfoResponse> updateMarital(@PathVariable String htId, DemoGraphicsInfoUpdateRequest request) {
        DemoGraphicsInfoResponse updatedProfile = demoGraphicsInfoServiceFacade.update(htId, DemoGraphicsInfoAttribute.MARITAL_STATUS, request);
        return ResponseEntity.ok(updatedProfile);
    }

    @DeleteMapping(value = "/{htId}/marital-status")
    @PreAuthorize(Role.DemoGraphics.MANAGE)
    public ResponseEntity deleteMarital(@PathVariable String htId) {
        demoGraphicsInfoServiceFacade.delete(htId, DemoGraphicsInfoAttribute.MARITAL_STATUS);
        return ResponseEntity.noContent().build();
    }

    // API For Customer Type
    @GetMapping(value = "/{htId}/customer-type-status")
    @PreAuthorize(Role.DemoGraphics.VIEW)
    public ResponseEntity<DemoGraphicsInfoResponse> findCustomerType(@PathVariable String htId) {
        DemoGraphicsInfoResponse p = demoGraphicsInfoServiceFacade.findByHtIdAndAttribute(htId, DemoGraphicsInfoAttribute.CUSTOMER_TYPE_STATUS);
        return ResponseEntity.ok(p);
    }

    @PostMapping(value = {"/{htId}/customer-type-status"})
    @PreAuthorize(Role.DemoGraphics.MANAGE)
    public ResponseEntity<DemoGraphicsInfoResponse> createCustomerType(@PathVariable String htId, DemoGraphicsInfoCreateRequest request) {
        DemoGraphicsInfoResponse createdProfile = demoGraphicsInfoServiceFacade.create(htId, DemoGraphicsInfoAttribute.CUSTOMER_TYPE_STATUS, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProfile);
    }

    @PutMapping(value = "/{htId}/customer-type-status")
    @PreAuthorize(Role.DemoGraphics.MANAGE)
    public ResponseEntity<DemoGraphicsInfoResponse> updateCustomerType(@PathVariable String htId, DemoGraphicsInfoUpdateRequest request) {
        DemoGraphicsInfoResponse updatedProfile = demoGraphicsInfoServiceFacade.update(htId, DemoGraphicsInfoAttribute.CUSTOMER_TYPE_STATUS, request);
        return ResponseEntity.ok(updatedProfile);
    }

    @DeleteMapping(value = "/{htId}/customer-type-status")
    @PreAuthorize(Role.DemoGraphics.MANAGE)
    public ResponseEntity deleteCustomerType(@PathVariable String htId) {
        demoGraphicsInfoServiceFacade.delete(htId, DemoGraphicsInfoAttribute.CUSTOMER_TYPE_STATUS);
        return ResponseEntity.noContent().build();
    }

}
