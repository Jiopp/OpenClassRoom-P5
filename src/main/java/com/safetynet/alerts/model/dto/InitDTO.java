package com.safetynet.alerts.model.dto;

import java.util.List;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represent the format for the init
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InitDTO {

  @Valid
  private List<PersonDTO> persons;
  @Valid
  private List<FirestationDTO> firestations;
  @Valid
  private List<MedicalRecordDTO> medicalrecords;
}
