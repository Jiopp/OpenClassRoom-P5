package com.safetynet.alerts.model;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.safetynet.alerts.model.dto.EmailDTO;
import com.safetynet.alerts.model.dto.FloodPersonDTO;
import com.safetynet.alerts.model.dto.PersonsByFirestationDTO;
import com.safetynet.alerts.model.dto.PhoneNumberDTO;
import com.safetynet.alerts.model.primarykey.MyCompositePK;
import com.safetynet.alerts.service.InitService;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(Person.class)
class PersonTest {

  private final static String FIRSTNAME = "John";
  private final static String LASTNAME = "Boyd";
  private final static String ADDRESS = "947 E. Rose Dr";
  @Autowired
  private MockMvc mockMvc;
  @MockBean
  private InitService initService;

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

  @Test
  void convertPersonToPhoneNumber_should_return_a_PhoneNumber() {
    // ACT
    PhoneNumberDTO phoneNumberDTO = Person.builder().phone("0645454545").build().convertPersonToPhoneNumber();
    // ASSERT
    assertThat(phoneNumberDTO.getPhoneNumber()).isEqualTo("0645454545");
  }

  @Test
  void convertPersonToEmail_should_return_a_PhoneNumber() {
    // ACT
    EmailDTO emailDTO = Person.builder().email("toto@email.com").build().convertPersonToEmail();
    // ASSERT
    assertThat(emailDTO.getEmail()).isEqualTo("toto@email.com");
  }

  @Test
  void convertPersonToFloodPersonDTO_should_return_a_PhoneNumber() {
    // ACT
    Person person = initPersonTest();

    FloodPersonDTO floodPersonDTO = person.convertPersonToFloodPersonDTO();
    // ASSERT
    assertThat(floodPersonDTO.getFirstName()).isEqualTo(FIRSTNAME);
    assertThat(floodPersonDTO.getLastName()).isEqualTo(LASTNAME);
    assertThat(floodPersonDTO.getAge()).isNotNull();
  }

  @Test
  void convertPersonToPersonsByFirestationDTO_should_return_a_PhoneNumber() {
    // ACT
    Person person = initPersonTest();

    PersonsByFirestationDTO personsByFirestationDTO = person.convertPersonToPersonsByFirestationDTO();
    // ASSERT
    assertThat(personsByFirestationDTO.getFirstName()).isEqualTo(FIRSTNAME);
    assertThat(personsByFirestationDTO.getLastName()).isEqualTo(LASTNAME);
    assertThat(personsByFirestationDTO.getAddress()).isNotNull();
    assertThat(personsByFirestationDTO.getPhoneNumber()).isNull();
  }

  @Test
  void calculateAge_with_birthDate_null() {
    assertThat(Person.calculateAge(null)).isZero();
  }
}
