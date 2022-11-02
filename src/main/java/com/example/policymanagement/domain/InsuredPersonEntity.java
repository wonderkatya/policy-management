package com.example.policymanagement.domain;

import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "insured_person")
@Getter
@Setter
public class InsuredPersonEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;
  private String firstName;
  private String secondName;
  private BigDecimal premium;
  @ManyToOne(fetch = FetchType.LAZY)
  private PolicyEntity policy;
}
