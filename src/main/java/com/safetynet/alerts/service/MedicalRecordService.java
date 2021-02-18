package com.safetynet.alerts.service;

import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.repository.MedicalRecordRepository;
import java.util.List;
import java.util.Optional;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Data
@Service
public class MedicalRecordService {

  @Autowired
  private MedicalRecordRepository medicalRecordRepository;

  public Optional<MedicalRecord> getMedicalRecord(final Long id) {
    return medicalRecordRepository.findById(id);
  }

  public List<MedicalRecord> getMedicalRecordByPerson(final Long person_id) {
    return medicalRecordRepository.findByPersonId(person_id);
  }

  public Iterable<MedicalRecord> getMedicalRecords() {
    return medicalRecordRepository.findAll();
  }

  public void deleteMedicalRecord(final Long id) {
    medicalRecordRepository.deleteById(id);
  }

  public MedicalRecord saveMedicalRecord(MedicalRecord medicalrecord) {
    return medicalRecordRepository.save(medicalrecord);
  }

}
