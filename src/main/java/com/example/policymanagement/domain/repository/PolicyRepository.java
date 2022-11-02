package com.example.policymanagement.domain.repository;

import com.example.policymanagement.domain.PolicyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PolicyRepository extends JpaRepository<PolicyEntity, String> {

}
