package com.safetynet.alerts.controller;

import static com.safetynet.alerts.utils.UtilsTest.asJsonString;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Medication;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.model.dto.MedicalRecordDTO;
import com.safetynet.alerts.model.primarykey.MyCompositePK;
import com.safetynet.alerts.service.InitService;
import com.safetynet.alerts.service.MedicalRecordService;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = MedicalRecordController.class)
class MedicalRecordControllerTest {

  private final static String FIRSTNAME = "John";
  private final static String LASTNAME = "Boyd";
  private final static LocalDate BIRTHDATE = LocalDate.of(1988, 6, 3);
  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private MedicalRecordService medicalRecordService;
  @MockBean
  private InitService initService;

  private Person initPersonTest() {
    Person person = Person.builder().id(MyCompositePK.builder().firstName(FIRSTNAME).lastName(LASTNAME).build())
        .birthDate(BIRTHDATE).build();
    Medication med1 = Medication.builder().person(person).medicationAndDosage("aznol:60mg").build();
    Medication med2 = Medication.builder().person(person).medicationAndDosage("hydrapermazol:900mg").build();
    Medication med3 = Medication.builder().person(person).medicationAndDosage("pharmacol:5000mg").build();
    List<Medication> medications = new ArrayList<>();
    medications.add(med1);
    medications.add(med2);
    medications.add(med3);
    person.setMedications(medications);
    return person;
  }

  @Test
  void createMedicalRecord_should_return_200_and_person_object() throws Exception {

    // Arrange
    MedicalRecordDTO medicalRecordDTO = MedicalRecordDTO.builder().firstName(FIRSTNAME).lastName(LASTNAME)
        .birthdate("1988-06-03")
        .medications(List.of("aznol:60mg", "hydrapermazol:900mg", "pharmacol:5000mg"))
        .allergies(List.of("peanut", "shellfish", "aznol")).build();
    MedicalRecord medicalRecord = MedicalRecord.builder().firstName(FIRSTNAME).lastName(LASTNAME)
        .birthdate(BIRTHDATE)
        .medications(List.of("aznol:60mg", "hydrapermazol:900mg", "pharmacol:5000mg"))
        .allergies(List.of("peanut", "shellfish", "aznol")).build();
    Person person = initPersonTest();
    when(medicalRecordService.saveMedicalRecord(medicalRecord)).thenReturn(person);

    // Act
    mockMvc.perform(post("/medicalRecord")
        .contentType(MediaType.APPLICATION_JSON)
        .content(asJsonString(medicalRecordDTO)))
        // Assert
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id.firstName", equalTo(FIRSTNAME)))
        .andExpect(jsonPath("$.id.lastName", equalTo(LASTNAME)))
        .andExpect(jsonPath("$.medications[0].medicationAndDosage", equalTo("aznol:60mg")));
  }

  @Test
  void updateMedicalRecord_should_return_200_and_person_object() throws Exception {

    // Arrange
    MedicalRecordDTO medicalRecordDTO = MedicalRecordDTO.builder().firstName(FIRSTNAME).lastName(LASTNAME)
        .birthdate("1988-06-03")
        .medications(List.of("aznol:60mg", "hydrapermazol:900mg", "pharmacol:5000mg"))
        .allergies(List.of("peanut", "shellfish", "aznol")).build();
    MedicalRecord medicalRecord = MedicalRecord.builder().firstName(FIRSTNAME).lastName(LASTNAME)
        .birthdate(BIRTHDATE)
        .medications(List.of("aznol:60mg", "hydrapermazol:900mg", "pharmacol:5000mg"))
        .allergies(List.of("peanut", "shellfish", "aznol")).build();
    Person person = initPersonTest();

    when(medicalRecordService.updateMedicalRecord(medicalRecord)).thenReturn(person);

    // Act
    mockMvc.perform(put("/medicalRecord")
        .contentType(MediaType.APPLICATION_JSON)
        .content(asJsonString(medicalRecordDTO)))
        // Assert
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id.firstName", equalTo(FIRSTNAME)))
        .andExpect(jsonPath("$.id.lastName", equalTo(LASTNAME)))
        .andExpect(jsonPath("$.medications[0].medicationAndDosage", equalTo("aznol:60mg")));
  }

  @Test
  void deleteMedicalRecord_should_return_200_if_MedicalRecord_is_found() throws Exception {
    mockMvc.perform(delete("/medicalRecord/John/Boyd"))
        .andExpect(status().isOk());
  }
}
