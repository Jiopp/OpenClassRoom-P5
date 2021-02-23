package com.safetynet.alerts.service;

import com.safetynet.alerts.model.DTO.FirestationDTO;
import com.safetynet.alerts.model.DTO.InitDTO;
import com.safetynet.alerts.model.DTO.MedicalRecordDTO;
import com.safetynet.alerts.model.DTO.PersonDTO;
import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Data
@Service
public class InitService {

  private final PersonService personService;
  private final FirestationService firestationService;
  private final MedicalRecordService medicalRecordService;

  @Transactional
  public void init(InitDTO initDTO) {

    for (PersonDTO personDTO : initDTO.getPersons()) {
      Person person = new Person();

      if (personDTO.getFirstName() != null) {
        person.setFirstName(personDTO.getFirstName());
      }
      if (personDTO.getLastName() != null) {
        person.setLastName(personDTO.getLastName());
      }
      if (personDTO.getAddress() != null) {
        person.setAddress(personDTO.getAddress());
      }

      if (personDTO.getCity() != null) {
        person.setCity(personDTO.getCity());
      }

      if (personDTO.getZip() != null) {
        person.setZip(personDTO.getZip());
      }

      if (personDTO.getPhone() != null) {
        person.setPhone(personDTO.getPhone());
      }

      if (personDTO.getEmail() != null) {
        person.setEmail(personDTO.getEmail());
      }

      personService.savePerson(person);
    }

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
