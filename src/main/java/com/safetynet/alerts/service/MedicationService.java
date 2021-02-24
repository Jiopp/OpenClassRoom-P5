package com.safetynet.alerts.service;

import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.MedicationRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Data
@Service
public class MedicationService {

  @Autowired
  private MedicationRepository medicationRepository;

  /**
   * This method will delete every record of medications for a person
   *
   * @param person The person for which we want to delete the medications
   */
  public void deleteMedicalRecordByPerson(final Person person) {
    medicationRepository.deleteByPerson(person);
  }
}
