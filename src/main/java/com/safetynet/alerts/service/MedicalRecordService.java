package com.safetynet.alerts.service;

import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.repository.AllergiesRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Data
@Service
public class MedicalRecordService {

  private final AllergiesService allergiesService;
  private final MedicationService medicationService;
  private final PersonService personService;

  @Autowired
  public MedicalRecordService(
      AllergiesService allergiesService, MedicationService medicationService, PersonService personService) {
    this.allergiesService = allergiesService;
    this.medicationService = medicationService;
    this.personService = personService;
  }

  public MedicalRecord saveMedicalRecord(MedicalRecord medicalRecord) {
    return medicalRecord;
  }

  public void deleteMedicalRecord(String firstName, String lastName) {
    Long personID = personService.getPersonByName(firstName, lastName).orElseThrow().getIdPerson();

    allergiesService.deleteAllergiesByPersonId(personID);
    medicationService.deleteMedicalRecordByPersonId(personID);
  }
}
