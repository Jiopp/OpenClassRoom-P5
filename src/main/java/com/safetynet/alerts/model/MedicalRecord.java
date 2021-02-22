package com.safetynet.alerts.model;

import java.sql.Date;
import java.util.List;
import lombok.Data;


@Data
public class MedicalRecord {

  String firstName;
  String lastName;
  String birthdate;
  List<String> medications;
  List<String> allergies;
}
