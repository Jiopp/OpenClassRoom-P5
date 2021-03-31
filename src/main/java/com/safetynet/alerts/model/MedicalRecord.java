package com.safetynet.alerts.model;

import java.time.LocalDate;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import lombok.Builder;
import lombok.Data;

/**
 * Represent a medical record for a Person
 */
@Data
@Builder
public class MedicalRecord {

  @NotNull
  @NotBlank(message = "First name cannot be empty")
  private String firstName;
  @NotNull
  @NotBlank(message = "Last name cannot be empty")
  private String lastName;
  @PastOrPresent
  private LocalDate birthdate;
  private List<String> medications;
  private List<String> allergies;
}
