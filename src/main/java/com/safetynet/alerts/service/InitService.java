package com.safetynet.alerts.service;

import static java.util.stream.Collectors.toList;

import com.safetynet.alerts.model.dto.FirestationDTO;
import com.safetynet.alerts.model.dto.InitDTO;
import com.safetynet.alerts.model.dto.MedicalRecordDTO;
import com.safetynet.alerts.model.dto.PersonDTO;
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

  /**
   * This method is used only to initialize some data in the application. It will parse the initDTO and save every person, every firestation and every medical record
   *
   * @param initDTO the object init
   */
  public void init(InitDTO initDTO) {

    personService.savePersons(initDTO.getPersons().stream().map(PersonDTO::convertToPerson).collect(toList()));

    firestationService.saveFirestations(
        initDTO.getFirestations().stream().map(FirestationDTO::convertToFirestation).collect(toList()));

    medicalRecordService
        .saveMedicalRecords(initDTO.getMedicalrecords().stream().map(MedicalRecordDTO::convertToMedicalRecord)
            .collect(toList()));
  }
}
