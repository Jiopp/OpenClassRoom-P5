package com.safetynet.alerts.model.dto;

import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.model.primarykey.MyCompositePK;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represent a person
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonDTO {

  @NotBlank
  @NotNull(message = "First Name for a person cannot be null")
  private String firstName;

  @NotBlank
  @NotNull(message = "Last Name for a person cannot be null")
  private String lastName;

  @Email(message = "email should be in a correct format")
  private String email;

  private String address;

  private String city;

  private Integer zip;

  private String phone;

  /**
   * Convert this PersonDTO into a Person
   *
   * @return The Person
   */
  public Person convertToPerson() {
    return Person.builder()
        .id(MyCompositePK.builder().firstName(getFirstName()).lastName(getLastName()).build())
        .address(getAddress()).city(getCity()).zip(getZip()).phone(getPhone()).email(getEmail()).build();
  }
}
