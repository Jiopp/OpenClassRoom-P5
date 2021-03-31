package com.safetynet.alerts.model.dto;

import com.safetynet.alerts.model.MedicalRecord;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represent a medical record
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MedicalRecordDTO {

  @NotBlank
  @NotNull(message = "First Name for a medical record cannot be null")
  private String firstName;
  @NotBlank
  @NotNull(message = "Last Name for a medical record cannot be null")
  private String lastName;
  private String birthdate;
  private List<String> medications;
  private List<String> allergies;

  /**
   * Convert this DTO into a MedicalRecord
   *
   * @return The MedicalRecord
   */
  public MedicalRecord convertToMedicalRecord() {
    return MedicalRecord.builder().firstName(getFirstName()).lastName(getLastName())
        .birthdate(LocalDate.parse(getBirthdate(), DateTimeFormatter.ofPattern("MM/dd/yyyy")))
        .medications(getMedications()).allergies(getAllergies()).build();
  }
}
