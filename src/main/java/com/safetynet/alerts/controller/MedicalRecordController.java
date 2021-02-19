package com.safetynet.alerts.controller;

import com.safetynet.alerts.service.MedicalRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MedicalRecordController {

  final
  MedicalRecordService medicalRecordService;

  @Autowired
  public MedicalRecordController(MedicalRecordService medicalRecordService) {
    this.medicalRecordService = medicalRecordService;
  }
}
