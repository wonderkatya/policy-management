package com.example.policymanagement.service;

import com.example.policymanagement.domain.repository.PolicyRepository;
import com.example.policymanagement.exception.InvalidDateException;
import com.example.policymanagement.mapper.PolicyMapper;
import com.example.policymanagement.model.PolicyCreateRequest;
import com.example.policymanagement.model.PolicyCreateResponse;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class PolicyManagementService {

  private final PolicyRepository policyRepository;
  private final PolicyMapper mapper;

  public PolicyCreateResponse createPolicy(PolicyCreateRequest policyCreateRequest) {

    //todo some duplicates check before inserting
    if (!policyCreateRequest.getStartDate().isAfter(LocalDate.now())) {
      throw new InvalidDateException("startDate must not be before %s".formatted(LocalDate.now()));
    }
    var policy = policyRepository.save(mapper.toEntity(policyCreateRequest));
    log.info("Saved new policy with id [{}].", policy.getPolicyId());

    return mapper.toCreationResponse(policy);
  }
}
