package com.safetynet.alerts.model.DTO;

import java.util.List;
import lombok.Data;

@Data
public class MedicalRecordsDTO {

  String firstName;
  String lastName;
  String birthdate;
  List<String> medications;
  List<String> allergies;
}
