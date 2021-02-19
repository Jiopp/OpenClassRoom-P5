package com.safetynet.alerts.model;

import java.sql.Date;
import lombok.Data;


@Data
public class MedicalRecord {

  String firstName;
  String lastName;
  Date birthDate;
  String medications;
  String allergies;
}
