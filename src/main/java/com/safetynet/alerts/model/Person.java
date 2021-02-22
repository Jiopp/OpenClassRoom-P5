package com.safetynet.alerts.model;

import static javax.persistence.CascadeType.ALL;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table()
public class Person {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  private String firstName;

  @Column
  private String lastName;

  @Column
  private String email;

  @Column
  private String address;

  @Column
  private String city;

  @Column
  private Integer zip;

  @Column
  private String phone;

  @Column
  private String birthDate;

  @OneToMany(cascade = ALL, mappedBy = "person")
  private List<Medication> medications;

  @OneToMany(cascade = ALL, mappedBy = "person")
  private List<Allergies> allergies;

}
