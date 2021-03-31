package com.safetynet.alerts.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.safetynet.alerts.model.Medication;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.model.dto.ChildrenDTO;
import com.safetynet.alerts.model.dto.PhoneNumberDTO;
import com.safetynet.alerts.model.primarykey.MyCompositePK;
import com.safetynet.alerts.service.AlertService;
import com.safetynet.alerts.service.InitService;
import com.safetynet.alerts.service.PersonService;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = AlertController.class)
class AlertControllerTest {

  private final static String FIRSTNAME = "John";
  private final static String LASTNAME = "Boyd";
  private final static String ADDRESS = "947 E. Rose Dr";
  private final static String PHONENUMBER = "841-874-6512";
  private final static String PHONENUMBER2 = "841-874-6544";

  @Autowired
  private MockMvc mockMvc;
  @MockBean
  private AlertService alertService;
  @MockBean
  private PersonService personService;
  @MockBean
  private InitService initService;

  @Test
  void get_childAlert_should_return_200_and_ChildrenDTO_object() throws Exception {
    // Arrange
    List<Person> family = new ArrayList<>();
    family.add(initPersonTest());
    List<Person> children = new ArrayList<>();
    children.add(Person.builder().id(MyCompositePK.builder().firstName(FIRSTNAME).lastName(LASTNAME).build())
        .birthDate(LocalDate.of(2020, 4, 27)).build());

    when(alertService.getAllChildrenAndTheirFamiliesForAnAddress(ADDRESS))
        .thenReturn(new ChildrenDTO(children, family));

    // Act
    mockMvc.perform(get("/childAlert?address=947 E. Rose Dr"))
        // Assert
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.children[0].firstName", equalTo(FIRSTNAME)))
        .andExpect(jsonPath("$.children[0].lastName", equalTo(LASTNAME)));
  }

  @Test
  void get_phoneAlert_should_return_200_and_list_of_PhoneNumberDTO() throws Exception {
    // Arrange
    List<PhoneNumberDTO> phoneNumberDTO = new ArrayList<>();
    phoneNumberDTO.add(PhoneNumberDTO.builder().phoneNumber(PHONENUMBER).build());
    phoneNumberDTO.add(PhoneNumberDTO.builder().phoneNumber(PHONENUMBER2).build());

    when(alertService.getAllPhoneNumberForAFirestation(3)).thenReturn(phoneNumberDTO);

    // Act
    mockMvc.perform(get("/phoneAlert?firestation=3"))
        // Assert
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.[0].phoneNumber", equalTo(PHONENUMBER)))
        .andExpect(jsonPath("$.[1].phoneNumber", equalTo(PHONENUMBER2)));
  }

  private Person initPersonTest() {
    Person person = Person.builder().id(MyCompositePK.builder().firstName(FIRSTNAME).lastName(LASTNAME).build())
        .birthDate(LocalDate.of(1988, 6, 3)).address(ADDRESS).build();
    Medication med1 = Medication.builder().medicationAndDosage("aznol:60mg").person(person).build();
    Medication med2 = Medication.builder().medicationAndDosage("hydrapermazol:900mg").person(person).build();
    Medication med3 = Medication.builder().medicationAndDosage("pharmacol:5000mg").person(person).build();
    List<Medication> medications = new ArrayList<>();
    medications.add(med1);
    medications.add(med2);
    medications.add(med3);
    person.setMedications(medications);
    return person;
  }
}
