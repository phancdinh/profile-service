package org.ht.id.profileapi.controller;

import org.ht.id.common.constant.DemoGraphicsAttribute;
import org.ht.id.profileapi.dto.request.DemoGraphicsInfoCreateRequest;
import org.ht.id.profileapi.dto.response.DemoGraphicsInfoResponse;
import org.ht.id.profileapi.facade.DemoGraphicsInfoFacade;
import org.ht.id.profileapi.dto.request.DemoGraphicsInfoUpdateRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private final DemoGraphicsInfoFacade demoGraphicsInfoBizService;

    public DemoGraphicsInfoController(DemoGraphicsInfoFacade demoGraphicsInfoBizService) {
        this.demoGraphicsInfoBizService = demoGraphicsInfoBizService;
    }


    // API For Marital
    @GetMapping(value = "/{htId}/marital-status")
    public ResponseEntity<DemoGraphicsInfoResponse> findMarital(@PathVariable String htId) {
        DemoGraphicsInfoResponse p = demoGraphicsInfoBizService.findByHtIdAndAttribute(htId, DemoGraphicsAttribute.MARITAL_STATUS);
        return ResponseEntity.ok(p);
    }

    @PostMapping(value = {"/{htId}/marital-status"})
    public ResponseEntity<DemoGraphicsInfoResponse> createMarital(@PathVariable String htId,
                                                                  @Valid @RequestBody DemoGraphicsInfoCreateRequest request) {
        DemoGraphicsInfoResponse createdProfile = demoGraphicsInfoBizService.create(htId, DemoGraphicsAttribute.MARITAL_STATUS, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProfile);
    }

    @PutMapping(value = "/{htId}/marital-status")
    public ResponseEntity<DemoGraphicsInfoResponse> updateMarital(@PathVariable String htId,
                                                                  @Valid @RequestBody DemoGraphicsInfoUpdateRequest request) {
        DemoGraphicsInfoResponse updatedProfile = demoGraphicsInfoBizService.update(htId, DemoGraphicsAttribute.MARITAL_STATUS, request);
        return ResponseEntity.ok(updatedProfile);
    }

    @DeleteMapping(value = "/{htId}/marital-status")
    public ResponseEntity<Void> deleteMarital(@PathVariable String htId) {
        demoGraphicsInfoBizService.delete(htId, DemoGraphicsAttribute.MARITAL_STATUS);
        return ResponseEntity.noContent().build();
    }

    // API For Customer Type
    @GetMapping(value = "/{htId}/customer-type-status")
    public ResponseEntity<DemoGraphicsInfoResponse> findCustomerType(@PathVariable String htId) {
        DemoGraphicsInfoResponse p = demoGraphicsInfoBizService.findByHtIdAndAttribute(htId, DemoGraphicsAttribute.CUSTOMER_TYPE_STATUS);
        return ResponseEntity.ok(p);
    }

    @PostMapping(value = {"/{htId}/customer-type-status"})
    public ResponseEntity<DemoGraphicsInfoResponse> createCustomerType(@PathVariable String htId,
                                                                       @Valid @RequestBody DemoGraphicsInfoCreateRequest request) {
        DemoGraphicsInfoResponse createdProfile = demoGraphicsInfoBizService.create(htId, DemoGraphicsAttribute.CUSTOMER_TYPE_STATUS, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProfile);
    }

    @PutMapping(value = "/{htId}/customer-type-status")
    public ResponseEntity<DemoGraphicsInfoResponse> updateCustomerType(@PathVariable String htId,
                                                                       @Valid @RequestBody DemoGraphicsInfoUpdateRequest request) {
        DemoGraphicsInfoResponse updatedProfile = demoGraphicsInfoBizService.update(htId, DemoGraphicsAttribute.CUSTOMER_TYPE_STATUS, request);
        return ResponseEntity.ok(updatedProfile);
    }

    @DeleteMapping(value = "/{htId}/customer-type-status")
    public ResponseEntity<Void> deleteCustomerType(@PathVariable String htId) {
        demoGraphicsInfoBizService.delete(htId, DemoGraphicsAttribute.CUSTOMER_TYPE_STATUS);
        return ResponseEntity.noContent().build();
    }
}
