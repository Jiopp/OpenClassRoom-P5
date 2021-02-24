package com.safetynet.alerts.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represent a medication for a Person
 */
@Entity
@Table()
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Medication {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @JsonBackReference
  @ManyToOne()
  @JoinColumn(name = "firstName", nullable = false)
  @JoinColumn(name = "lastName", nullable = false)
  private Person person;

  @Column
  private String medicationAndDosage;
}
