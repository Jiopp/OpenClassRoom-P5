package com.safetynet.alerts.controller;

import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.service.MedicalRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MedicalRecordController {

  @Autowired
  MedicalRecordService medicalRecordService;

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
}
