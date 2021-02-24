package com.safetynet.alerts.service;

import com.safetynet.alerts.model.DTO.FirestationDTO;
import com.safetynet.alerts.model.DTO.InitDTO;
import com.safetynet.alerts.model.DTO.MedicalRecordDTO;
import com.safetynet.alerts.model.DTO.PersonDTO;
import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class InitService {

  @Autowired
  private PersonService personService;
  @Autowired
  private FirestationService firestationService;
  @Autowired
  private MedicalRecordService medicalRecordService;

  public void init(InitDTO initDTO) {

    for (PersonDTO personDTO : initDTO.getPersons()) {
      // TODO créer une liste en utilisant les Lambda + utiliser savePersons
      Person person = new Person();
      person.setFirstName(personDTO.getFirstName());
      person.setLastName(personDTO.getLastName());
      person.setAddress(personDTO.getAddress());
      person.setCity(personDTO.getCity());
      person.setZip(personDTO.getZip());
      person.setPhone(personDTO.getPhone());
      person.setEmail(personDTO.getEmail());
      personService.savePerson(person);
    }
    // TODO retirer les if + utiliser saveFirestations
    for (FirestationDTO firestationDTO : initDTO.getFirestations()) {
      Firestation firestation = new Firestation();

      if (firestationDTO.getAddress() != null) {
        firestation.setAddress(firestationDTO.getAddress());
      }
      if (firestationDTO.getStation() != null) {
        firestation.setStationNumber(firestationDTO.getStation());
      }
      firestationService.saveFirestation(firestation);
    }

    // TODO retirer les if + utiliser saveMedicalRecords
    for (MedicalRecordDTO medicalRecordDTO : initDTO.getMedicalrecords()) {
      MedicalRecord medicalRecord = new MedicalRecord();

      if (medicalRecordDTO.getFirstName() != null) {
        medicalRecord.setFirstName(medicalRecordDTO.getFirstName());
      }
      if (medicalRecordDTO.getLastName() != null) {
        medicalRecord.setLastName(medicalRecordDTO.getLastName());
      }
      if (medicalRecordDTO.getBirthdate() != null) {
        medicalRecord.setBirthdate(medicalRecordDTO.getBirthdate());
      }
      if (medicalRecordDTO.getMedications() != null) {
        medicalRecord.setMedications(medicalRecordDTO.getMedications());
      }
      if (medicalRecordDTO.getAllergies() != null) {
        medicalRecord.setAllergies(medicalRecordDTO.getAllergies());
      }
      medicalRecordService.saveMedicalRecord(medicalRecord);
    }
  }
}
