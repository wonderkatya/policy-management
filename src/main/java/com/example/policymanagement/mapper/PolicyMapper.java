package com.example.policymanagement.mapper;

import com.example.policymanagement.domain.InsuredPersonEntity;
import com.example.policymanagement.domain.PolicyEntity;
import com.example.policymanagement.model.PolicyCreateRequest;
import com.example.policymanagement.model.PolicyCreateResponse;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Objects;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PolicyMapper {

  @Mapping(target = "policyId", expression = "java(generatePolicyId())")
  PolicyEntity toEntity(PolicyCreateRequest createRequest);

  @Mapping(target = "totalPremium", expression = "java(calculateTotalPremium(source.getInsuredPersons()))")
  PolicyCreateResponse toCreationResponse(PolicyEntity source);

  default String generatePolicyId() {
    return "CU423DF89"; //todo more reasonable logic
  }

  default BigDecimal calculateTotalPremium(List<InsuredPersonEntity> insuredPersons) {

    return insuredPersons.stream()
        .map(InsuredPersonEntity::getPremium)
        .filter(Objects::nonNull)
        .reduce(BigDecimal.ZERO, BigDecimal::add)
        .setScale(2, RoundingMode.HALF_UP);
  }
}
