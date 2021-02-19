package com.safetynet.alerts.model;

import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table()
public class Person {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long idPerson;

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
  private Date birthDate;

}
