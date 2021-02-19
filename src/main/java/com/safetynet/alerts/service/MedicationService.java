package com.safetynet.alerts.service;

import com.safetynet.alerts.model.Medication;
import com.safetynet.alerts.repository.MedicationRepository;
import java.util.List;
import java.util.Optional;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Data
@Service
public class MedicationService {

  private final MedicationRepository medicationRepository;

  @Autowired
  public MedicationService(MedicationRepository medicationRepository) {
    this.medicationRepository = medicationRepository;
  }

  public Optional<Medication> getMedicalRecord(final Long id) {
    return medicationRepository.findById(id);
  }

  public List<Medication> getMedicalRecordByPerson(final Long person_id) {
    return medicationRepository.findByPersonId(person_id);
  }

  public Iterable<Medication> getMedicalRecords() {
    return medicationRepository.findAll();
  }

  public void deleteMedicalRecord(final Long id) {
    medicationRepository.deleteById(id);
  }

  public Medication saveMedicalRecord(Medication medication) {
    return medicationRepository.save(medication);
  }

}
