package com.safetynet.alerts.controller;

import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Medication;
import com.safetynet.alerts.service.MedicalRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MedicalRecordController {

  final
  MedicalRecordService medicalRecordService;

  @Autowired
  public MedicalRecordController(MedicalRecordService medicalRecordService) {
    this.medicalRecordService = medicalRecordService;
  }

  /**
   * Create - Add a new medical record
   *
   * @param medicalRecord An object medicalRecord
   * @return The medicalRecord object saved
   */
  @PostMapping("/medicalRecord")
  public MedicalRecord createMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
    return medicalRecordService.saveMedicalRecord(medicalRecord);
  }

  /**
   * Delete - Delete all medical records and allergies for one person
   *
   * @param firstName of the person
   * @param lastName of the person
   */
  @DeleteMapping("/medicalRecord/{firstName}/{lastName}")
  public void deleteMedicalRecord(@PathVariable String firstName, @PathVariable String lastName) {
    medicalRecordService.deleteMedicalRecord(firstName, lastName);
  }
}
