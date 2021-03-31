package com.safetynet.alerts.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.safetynet.alerts.exception.PersonNotFoundException;
import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.model.dto.EmailDTO;
import com.safetynet.alerts.model.dto.FireDTO;
import com.safetynet.alerts.model.primarykey.MyCompositePK;
import com.safetynet.alerts.repository.FirestationRepository;
import com.safetynet.alerts.repository.PersonRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class PersonServiceTest {

  private final static String ADDRESS = "Home";
  private final static String CITY = "Culver";
  private final static String FIRSTNAME = "John";
  private final static String FIRSTNAME2 = "Shawna";
  private final static String LASTNAME = "BOYD";
  private final static String LASTNAME2 = "Stelzer";
  private final static String EMAIL = "jaboyd@email.com";
  private final static String EMAIL2 = "ssanw@email.com";
  private final static LocalDate BIRTHDATE = LocalDate.of(1988, 3, 6);

  @Mock
  private PersonRepository personRepository;
  @Mock
  private FirestationRepository firestationRepository;
  @InjectMocks
  private PersonService personService;

  @BeforeEach
  void init_mocks() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  @DisplayName("When update an unknown person, then throw a PersonNotFoundException")
  void updatePerson_for_an_unknown_person() {
    // ARRANGE
    Person person = Person.builder().id(MyCompositePK.builder().firstName(FIRSTNAME).lastName(LASTNAME).build())
        .build();
    when(personRepository.findPersonById(MyCompositePK.builder().firstName(FIRSTNAME).lastName(LASTNAME).build()))
        .thenReturn(Optional.empty());
    // ASSERT
    Assertions.assertThatExceptionOfType(PersonNotFoundException.class)
        .isThrownBy(() -> personService.updatePerson(person));
  }

  @Test
  @DisplayName("When update an exist person, then respond with thr person updated")
  void updatePerson_for_an_existing_person() throws PersonNotFoundException {
    // ARRANGE
    Person personToUpdate = Person.builder().id(MyCompositePK.builder().firstName(FIRSTNAME).lastName(LASTNAME).build())
        .birthDate(BIRTHDATE).build();
    when(personRepository.findPersonById(personToUpdate.getId())).thenReturn(Optional.ofNullable(
        Person.builder().id(MyCompositePK.builder().firstName(FIRSTNAME).lastName(LASTNAME).build()).build()));
    when(personRepository.save(personToUpdate)).thenReturn(personToUpdate);
    Person personResult = personService.updatePerson(personToUpdate);
    // ASSERT
    assertThat(personResult).isEqualTo(personToUpdate);
  }

  @Test
  @DisplayName("When getPersonAndFirestationNumberForAnAddress for a valid address, then return a FireDTO")
  void getPersonAndFirestationNumberForAnAddress_Test() {
    // ARRANGE
    FireDTO fireDTOExpected = FireDTO.builder().firestationNumber(1).persons(
        List.of(Person.builder().id(MyCompositePK.builder().firstName(FIRSTNAME).lastName(LASTNAME).build()).build(),
            Person.builder().id(MyCompositePK.builder().firstName(FIRSTNAME2).lastName(LASTNAME2).build()).build()))
        .build();

    when(firestationRepository.findByAddress(ADDRESS))
        .thenReturn(Optional.ofNullable(Firestation.builder().stationNumber(1).address(ADDRESS).build()));
    when(personRepository.findAllByAddress(ADDRESS)).thenReturn(
        List.of(Person.builder().id(MyCompositePK.builder().firstName(FIRSTNAME).lastName(LASTNAME).build()).build(),
            Person.builder().id(MyCompositePK.builder().firstName(FIRSTNAME2).lastName(LASTNAME2).build()).build()));
    // ACT
    FireDTO fireDTOResult = personService.getPersonAndFirestationNumberForAnAddress(ADDRESS);
    // ASSERT
    assertThat(fireDTOResult).isEqualTo(fireDTOExpected);
  }

  @Test
  @DisplayName("When getEmailByCity for a valid city, then return a List of EmailDTO ")
  void getEmailByCity() {
    // ARRANGE
    List<EmailDTO> emailDTOListExpected = List
        .of(EmailDTO.builder().email(EMAIL).build(), EmailDTO.builder().email(EMAIL2).build());
    when(personRepository.findAllByCity(CITY)).thenReturn(List.of(
        Person.builder().id(MyCompositePK.builder().firstName(FIRSTNAME).lastName(LASTNAME).build()).email(EMAIL)
            .build(),
        Person.builder().id(MyCompositePK.builder().firstName(FIRSTNAME2).lastName(LASTNAME2).build()).email(EMAIL2)
            .build()));
    // ACT
    List<EmailDTO> emailDTOListResult = personService.getEmailByCity(CITY);
    // ASSERT
    assertThat(emailDTOListResult).isEqualTo(emailDTOListExpected);
  }

  @Test
  @DisplayName("When deletePersonByName for an unknown person, then throw a PersonNotFoundException ")
  void deletePersonByNameThrowException() {
    Assertions.assertThatExceptionOfType(PersonNotFoundException.class)
        .isThrownBy(() -> personService.deletePersonByName(FIRSTNAME, LASTNAME));
  }

  @Test
  @DisplayName("When deletePersonByName for an exist person, then check that the delete is called ")
  void deletePersonByName() throws PersonNotFoundException {
    // ARRANGE
    when(personRepository.findPersonById(MyCompositePK.builder().firstName(FIRSTNAME).lastName(LASTNAME).build()))
        .thenReturn(
            Optional.ofNullable(
                Person.builder().id(MyCompositePK.builder().firstName(FIRSTNAME).lastName(LASTNAME).build()).build()));
    personService.deletePersonByName(FIRSTNAME, LASTNAME);
    // ASSERT
    verify(personRepository, times(1)).delete(any());
  }

  @Test
  @DisplayName("When savePersons for a list of Person, then check that the save method is called ")
  void savePersons() {
    // ARRANGE
    personService.savePersons(
        List.of(Person.builder().id(MyCompositePK.builder().firstName(FIRSTNAME).lastName(LASTNAME).build()).build()));
    // ASSERT
    verify(personRepository, times(1)).saveAll(any());
  }
}
