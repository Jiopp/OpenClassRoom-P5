package com.safetynet.alerts.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.model.dto.ChildrenDTO;
import com.safetynet.alerts.model.dto.PhoneNumberDTO;
import com.safetynet.alerts.model.primarykey.MyCompositePK;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class AlertServiceTest {

  private final static String ADDRESS = "947 E. Rose Dr";
  private final static String WORKADDRESS = "Work";
  private final static String PHONENUMBER = "01234";
  private final static String PHONENUMBER2 = "56789";
  private final static String FIRSTNAME = "John";
  private final static String FIRSTNAME2 = "Kendrick";
  private final static String LASTNAME = "Boyd";
  @InjectMocks
  private AlertService alertService;
  @Mock
  private PersonService personService;
  @Mock
  private FirestationService firestationService;

  @BeforeEach
  void init_mocks() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void getAllChildrenAndTheirFamiliesForAnAddress_should_return_A_ChildrenDTO_object_when_both_children_and_family_exist() {

    // Arrange
    List<Person> family = new ArrayList<>();
    family.add(Person.builder().id(MyCompositePK.builder().firstName(FIRSTNAME).lastName(LASTNAME).build())
        .address(ADDRESS).build());

    List<Person> childrenResultList = new ArrayList<>();
    childrenResultList.add(Person.builder().id(MyCompositePK.builder().firstName(FIRSTNAME2).lastName(LASTNAME).build())
        .birthDate(LocalDate.of(2014, 6, 3)).build());

    List<Person> personByAddressList = Stream.concat(family.stream(), childrenResultList.stream())
        .collect(Collectors.toList());

    when(personService.getChildrenForAnAddress(ADDRESS))
        .thenReturn(childrenResultList);
    when(personService.getPersonsByAddress(ADDRESS)).thenReturn(personByAddressList);

    // Act
    ChildrenDTO childrenDTOResult = alertService.getAllChildrenAndTheirFamiliesForAnAddress(ADDRESS);

    // Assert
    List<Person> children = List
        .of(Person.builder().id(MyCompositePK.builder().firstName(FIRSTNAME2).lastName(LASTNAME).build())
            .birthDate(LocalDate.of(2014, 6, 3)).build());

    ChildrenDTO expectedChildrenDTO = new ChildrenDTO(children, family);

    assertThat(childrenDTOResult).isEqualTo(expectedChildrenDTO);
  }

  @Test
  void getAllChildrenAndTheirFamiliesForAnAddress_should_return_A_ChildrenDTO_object_when_children_exist_and_family_doesnt() {

    // Arrange
    List<Person> family = new ArrayList<>();

    List<Person> childrenResultList = new ArrayList<>();
    childrenResultList.add(Person.builder().id(MyCompositePK.builder().firstName(FIRSTNAME2).lastName(LASTNAME).build())
        .birthDate(LocalDate.of(2014, 6, 3)).build());

    when(personService.getChildrenForAnAddress(ADDRESS))
        .thenReturn(childrenResultList);
    when(personService.getPersonsByAddress(ADDRESS)).thenReturn(childrenResultList);

    // Act
    ChildrenDTO childrenDTOResult = alertService.getAllChildrenAndTheirFamiliesForAnAddress(ADDRESS);

    // Assert
    List<Person> children = List
        .of(Person.builder().id(MyCompositePK.builder().firstName(FIRSTNAME2).lastName(LASTNAME).build())
            .birthDate(LocalDate.of(2014, 6, 3)).build());

    ChildrenDTO expectedChildrenDTO = new ChildrenDTO(children, family);

    assertThat(childrenDTOResult).isEqualTo(expectedChildrenDTO);
  }

  @Test
  void getAllChildrenAndTheirFamiliesForAnAddress_should_return_empty_object_when_both_family_exist_and_children_doesnt() {

    // Arrange
    List<Person> family = new ArrayList<>();
    family.add(Person.builder().id(MyCompositePK.builder().firstName(FIRSTNAME).lastName(LASTNAME).build())
        .address(ADDRESS).build());
    when(personService.getChildrenForAnAddress(ADDRESS)).thenReturn(Collections.emptyList());
    when(personService.getPersonsByAddress(ADDRESS)).thenReturn(family);

    // Act
    ChildrenDTO result = alertService.getAllChildrenAndTheirFamiliesForAnAddress(ADDRESS);
    assertThat(result.getChildren()).isNull();
    assertThat(result.getFamily()).isNull();

  }

  @Test
  void getAllPhoneNumberForAFirestation_should_return_A_PhoneNumberDTO_object_when_both_firestation_and_person_exist() {
    // Arrange
    when(firestationService.getFirestationsByStationNumber(1)).thenReturn(
        List.of(Firestation.builder().address(ADDRESS).build(), Firestation.builder().address(WORKADDRESS).build()));
    when(personService.getPersonsByAddress(ADDRESS)).thenReturn(List.of(Person.builder().phone(PHONENUMBER).build()));
    when(personService.getPersonsByAddress(WORKADDRESS))
        .thenReturn(List.of(Person.builder().phone(PHONENUMBER2).build()));

    // Act
    List<PhoneNumberDTO> resultPhoneNumber = alertService.getAllPhoneNumberForAFirestation(1);
    // Assert
    List<PhoneNumberDTO> expectedPhoneNumberDTO = List.of(PhoneNumberDTO.builder().phoneNumber(PHONENUMBER).build(),
        PhoneNumberDTO.builder().phoneNumber(PHONENUMBER2).build());
    assertThat(resultPhoneNumber).isEqualTo(expectedPhoneNumberDTO);
  }

  @Test
  void getAllPhoneNumberForAFirestation_should_return_empty_when_firestation_exist_and_person_doesnt() {
    // Arrange
    when(firestationService.getFirestationsByStationNumber(1)).thenReturn(
        List.of(Firestation.builder().address(ADDRESS).build(), Firestation.builder().address(WORKADDRESS).build()));

    // Act
    assertThat(alertService.getAllPhoneNumberForAFirestation(1).size()).isZero();
  }

  @Test
  void getAllPhoneNumberForAFirestation_should_return_empty_when_person_exist_and_firestation_doesnt() {
    // Act
    assertThat(alertService.getAllPhoneNumberForAFirestation(1).size()).isZero();
  }
}
