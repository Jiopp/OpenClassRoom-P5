package com.safetynet.alerts.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.model.dto.FirestationDTO;
import com.safetynet.alerts.model.dto.InitDTO;
import com.safetynet.alerts.model.dto.MedicalRecordDTO;
import com.safetynet.alerts.model.dto.PersonDTO;
import com.safetynet.alerts.model.primarykey.MyCompositePK;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class InitServiceTest {

  private final static String FIRSTNAME = "John";
  private final static String LASTNAME = "Boyd";
  private final static String ADDRESS = "Home";
  private final static String BIRTHDATE = "01/08/1980";
  private final static LocalDate LOCALBIRTHDATE = LocalDate.of(1980, 1, 8);
  private final static int STATIONNUMBER = 1;
  @Captor
  ArgumentCaptor<List<Person>> personListArgumentCaptor;
  @Captor
  ArgumentCaptor<List<Firestation>> firestationListArgumentCaptor;
  @Captor
  ArgumentCaptor<List<MedicalRecord>> medicalRecordListArgumentCaptor;
  @InjectMocks
  private InitService initService;
  @Mock
  private PersonService personService;
  @Mock
  private FirestationService firestationService;
  @Mock
  private MedicalRecordService medicalRecordService;

  @BeforeEach
  void init_mocks() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  @DisplayName("When initDTO is use, then verify that save person,firestation and medical record is called ")
  void init() {
    // ARRANGE
    List<PersonDTO> personDTOList = new ArrayList<>();
    List<FirestationDTO> firestationDTOList = new ArrayList<>();
    List<MedicalRecordDTO> medicalRecordDTOList = new ArrayList<>();
    personDTOList.add(PersonDTO.builder().firstName(FIRSTNAME).lastName(LASTNAME).address(ADDRESS).build());
    firestationDTOList.add(FirestationDTO.builder().address(ADDRESS).station(STATIONNUMBER).build());
    medicalRecordDTOList.add(
        MedicalRecordDTO.builder().firstName(FIRSTNAME).lastName(LASTNAME).birthdate(BIRTHDATE)
            .build());

    InitDTO initDTO = InitDTO.builder().persons(personDTOList).firestations(firestationDTOList)
        .medicalrecords(medicalRecordDTOList).build();

    // ACT
    initService.init(initDTO);
    // ASSERT
    List<Person> personList = List.of(Person.builder()
        .id(MyCompositePK.builder().firstName(FIRSTNAME).lastName(LASTNAME).build()).address(ADDRESS).build());
    verify(personService).savePersons(personListArgumentCaptor.capture());
    List<Person> personListArgumentCaptorValue = personListArgumentCaptor.getValue();
    assertThat(personListArgumentCaptorValue).isEqualTo(personList);

    List<Firestation> firestationList = List
        .of(Firestation.builder().address(ADDRESS).stationNumber(STATIONNUMBER).build());
    verify(firestationService).saveFirestations(firestationListArgumentCaptor.capture());
    List<Firestation> firestationListArgumentCaptorValue = firestationListArgumentCaptor.getValue();
    assertThat(firestationListArgumentCaptorValue).isEqualTo(firestationList);

    List<MedicalRecord> medicalRecords = List
        .of(MedicalRecord.builder().firstName(FIRSTNAME).lastName(LASTNAME).birthdate(LOCALBIRTHDATE).build());
    verify(medicalRecordService).saveMedicalRecords(medicalRecordListArgumentCaptor.capture());
    List<MedicalRecord> medicalRecordListArgumentCaptorValue = medicalRecordListArgumentCaptor.getValue();
    assertThat(medicalRecordListArgumentCaptorValue).isEqualTo(medicalRecords);
  }
}
