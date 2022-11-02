package com.example.policymanagement.domain;

import java.time.LocalDate;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "policy")
@Getter
@Setter
public class PolicyEntity {

  @Id
  private String policyId;
  private LocalDate startDate;
  private LocalDate endDate;
  @OneToMany(mappedBy = "policy", cascade = CascadeType.ALL)
  private List<InsuredPersonEntity> insuredPersons;
}
