package com.safetynet.alerts.controller;

import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.MedicalRecordService;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Endpoint for everything related to medical record
 */
@RestController
@Validated
public class MedicalRecordController {

  @Autowired
  private MedicalRecordService medicalRecordService;

  /**
   * This method allow you to create a medical record for a person
   *
   * @param medicalRecord the medical record must contain a valid firstname and lastname. Example :
   * {
   * "firstName":"John",
   * "lastName":"Boyd",
   * "birthdate":"1988-06-03",
   * "medications":[
   * "aznol:60mg",
   * "hydrapermazol:900mg",
   * "pharmacol:5000mg"
   * ],
   * "allergies":[
   * "peanut",
   * "shellfish",
   * "aznol"
   * ]
   * }
   * @return The person with the medical record created
   */
  @PostMapping("/medicalRecord")
  public Person createMedicalRecord(@Valid @RequestBody MedicalRecord medicalRecord) {
    return medicalRecordService.saveMedicalRecord(medicalRecord);
  }

  /**
   * This method allow you to update a medical record for a person
   *
   * @param medicalRecord the medical record must contain a valid firstname and lastname. Example :
   * {
   * "firstName":"John",
   * "lastName":"Boyd",
   * "birthdate":"1988-06-03",
   * "medications":[
   * "aznol:60mg",
   * "hydrapermazol:900mg",
   * "pharmacol:5000mg"
   * ],
   * "allergies":[
   * "peanut",
   * "shellfish",
   * "aznol"
   * ]
   * }
   * @return The person with the medical record updated
   */
  @PutMapping("/medicalRecord")
  public Person updateMedicalRecord(@Valid @RequestBody MedicalRecord medicalRecord) {
    return medicalRecordService.updateMedicalRecord(medicalRecord);
  }

  /**
   * This method allow you to delete a medical record for a person
   *
   * @param firstName The first name of the person. Cannot be null
   * @param lastName The last name of the person. Cannot be null
   */
  @DeleteMapping("/medicalRecord/{firstName}/{lastName}")
  public void deleteMedicalRecord(@NotBlank(message = "First name cannot be empty") @PathVariable String firstName,
      @NotBlank(message = "Last name cannot be empty") @PathVariable String lastName) {
    medicalRecordService.deleteMedicalRecord(firstName, lastName);
  }
}
