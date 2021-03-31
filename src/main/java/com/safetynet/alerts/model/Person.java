package com.safetynet.alerts.model;

import static javax.persistence.CascadeType.ALL;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.safetynet.alerts.model.dto.EmailDTO;
import com.safetynet.alerts.model.dto.FloodPersonDTO;
import com.safetynet.alerts.model.dto.PersonForChildrenDTO;
import com.safetynet.alerts.model.dto.PersonsByFirestationDTO;
import com.safetynet.alerts.model.dto.PhoneNumberDTO;
import com.safetynet.alerts.model.primarykey.MyCompositePK;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represent a Peron
 */
@Data
@Entity
@Table()
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Person {

  @EmbeddedId
  private MyCompositePK id;

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
  private LocalDate birthDate;

  @JsonManagedReference
  @OneToMany(cascade = ALL, mappedBy = "person")
  private List<Medication> medications;

  @JsonManagedReference
  @OneToMany(cascade = ALL, mappedBy = "person")
  private List<Allergies> allergies;

  /**
   * This method calculate the age for a Person
   *
   * @param birthDate the birthdate for the person
   * @return the age of the person
   */
  public static int calculateAge(LocalDate birthDate) {
    if (birthDate != null) {
      return Period.between(birthDate, LocalDate.now()).getYears();
    }
    return 0;
  }

  /**
   * This method convert a Person into a Children
   *
   * @return The Children
   */
  public Children convertPersonToChildren() {
    return Children.builder().firstName(getId().getFirstName()).lastName(getId().getLastName())
        .birthDate(getBirthDate())
        .build();
  }

  /**
   * This method convert a Person into a PhoneNumber
   *
   * @return The PhoneNumber
   */
  public PhoneNumberDTO convertPersonToPhoneNumber() {
    return PhoneNumberDTO.builder().phoneNumber(getPhone()).build();
  }

  /**
   * This method convert a Person into a Email
   *
   * @return The Email
   */
  public EmailDTO convertPersonToEmail() {
    return EmailDTO.builder().email(getEmail()).build();
  }

  /**
   * This method convert a Person into a FloodPersonDTO
   *
   * @return The FloodPersonDTO
   */
  public FloodPersonDTO convertPersonToFloodPersonDTO() {
    return FloodPersonDTO.builder()
        .firstName(getId().getFirstName()).lastName(getId().getLastName()).phoneNumber(getPhone())
        .medicationList(getMedications()).allergiesList(getAllergies())
        .age(calculateAge(getBirthDate())).build();
  }

  /**
   * This method convert a Person into a PersonsByFirestationDTO
   *
   * @return The PersonsByFirestationDTO
   */
  public PersonsByFirestationDTO convertPersonToPersonsByFirestationDTO() {
    return PersonsByFirestationDTO.builder().firstName(getId().getFirstName()).lastName(getId().getLastName())
        .address(getAddress()).phoneNumber(getPhone()).build();
  }

  /**
   * This method convert a Person into a PersonForChildrenDTO
   *
   * @return The PersonForChildrenDTO
   */
  public PersonForChildrenDTO convertPersonToPersonForChildrenDTO() {
    return PersonForChildrenDTO.builder().firstName(getId().getFirstName())
        .lastName(getId().getLastName()).build();
  }
}
