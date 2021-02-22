package com.safetynet.alerts.model.DTO;

import java.util.List;
import lombok.Data;

@Data
public class InitDTO {

  List<PersonDTO> persons;
  List<FirestationDTO> firestations;
  List<MedicalRecordsDTO> medicalrecords;

}
