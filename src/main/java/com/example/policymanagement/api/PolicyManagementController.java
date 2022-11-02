package com.example.policymanagement.api;

import com.example.policymanagement.model.PolicyCreateRequest;
import com.example.policymanagement.model.PolicyCreateResponse;
import com.example.policymanagement.service.PolicyManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PolicyManagementController implements PolicyApi {

  private final PolicyManagementService service;

  @Override
  public ResponseEntity<PolicyCreateResponse> createPolicy(PolicyCreateRequest policyCreateRequest) {
    return ResponseEntity.ok(service.createPolicy(policyCreateRequest));
  }
}
