package com.safetynet.alerts.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

import com.safetynet.alerts.exception.FirestationNotFoundException;
import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.model.dto.ResidentByFirestationDTO;
import com.safetynet.alerts.model.primarykey.MyCompositePK;
import com.safetynet.alerts.repository.FirestationRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class FirestationServiceTest {

  private final static String ADDRESS = "Home";

  @InjectMocks
  FirestationService firestationService;
  @Mock
  PersonService personService;
  @Mock
  private FirestationRepository firestationRepository;

  @BeforeEach
  void init_mocks() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  @Tag("Integration test")
  @DisplayName(
      "When deleteFirestationByAddress for an address who doesn't exist, then throw FirestationNotFoundException")
  void deleteFirestationByAddress_for_an_address_who_doesnt_exist() {
    Assertions.assertThatExceptionOfType(FirestationNotFoundException.class)
        .isThrownBy(() -> firestationService.deleteFirestationByAddress(ADDRESS));
  }

  @Test
  @DisplayName("When updateFirestation for an existing firestation, then return firestation ")
  void updateFirestation_for_an_existing_firestation() throws FirestationNotFoundException {

    // ARRANGE
    Firestation firestation = Firestation.builder().address(ADDRESS).stationNumber(2).build();

    when(firestationRepository.findByAddress(ADDRESS)).thenReturn(Optional
        .ofNullable(Firestation.builder().address(ADDRESS).stationNumber(1).build()));
    // ACT
    Firestation firestationResult = firestationService.updateFirestation(firestation);
    // ASSERT
    assertThat(firestationResult).isEqualTo(firestation);
  }

  @Test
  @DisplayName("When updateFirestation for an unknown firestation, then return FirestationNotFoundException ")
  void updateFirestation_for_an_unknown_firestation() {
    // ARRANGE
    Firestation firestation = Firestation.builder().address(ADDRESS).stationNumber(1).build();
    when(firestationRepository.findByAddress(ADDRESS)).thenReturn(Optional.empty());
    //ACT
    Assertions.assertThatExceptionOfType(FirestationNotFoundException.class)
        .isThrownBy(() -> firestationService.updateFirestation(firestation));
  }

  @Test
  @DisplayName("When getPersonByFirestation for an unknown firestation, then return empty list ")
  void getPersonByFirestation_for_an_unknown_firestation() {
    // ARRANGE
    when(firestationRepository.findAllByStationNumber(1)).thenReturn(new ArrayList<>());
    //ACT
    ResidentByFirestationDTO firestationResult = firestationService.getPersonByFirestation(1);
    assertThat(firestationResult.getPersonsByFirestationDTO().size()).isZero();
  }

  @Test
  @DisplayName("When getPersonByFirestation for an exist firestation, but without persons should return empty list ")
  void getPersonByFirestation_for_an_exist_firestation_but_no_persons_found() {
    // ARRANGE
    when(firestationRepository.findAllByStationNumber(1))
        .thenReturn(List.of(Firestation.builder().address(ADDRESS).stationNumber(1).build()));
    when(personService.getPersonsByAddress(ADDRESS)).thenReturn(new ArrayList<>());
    //ACT
    ResidentByFirestationDTO firestationResult = firestationService.getPersonByFirestation(1);
    assertThat(firestationResult.getPersonsByFirestationDTO().size()).isZero();
  }

  @Test
  @DisplayName(
      "When getPersonByFirestation for an exist firestation and persons should return list of ResidentByFirestationDTO")
  void getPersonByFirestation_for_an_exist_firestation_and_persons_found() {
    // ARRANGE
    when(firestationRepository.findAllByStationNumber(1))
        .thenReturn(List.of(Firestation.builder().address(ADDRESS).stationNumber(1).build()));
    when(personService.getPersonsByAddress(ADDRESS)).thenReturn(
        List.of(
            Person.builder().id(MyCompositePK.builder().firstName("John").lastName("Boyd").build())
                .birthDate(LocalDate.of(1988, 3, 6)).build(),
            Person.builder().id(MyCompositePK.builder().firstName("Suzy").lastName("Boyd").build())
                .birthDate(LocalDate.of(2018, 3, 6)).build()));
    //ACT
    ResidentByFirestationDTO firestationResult = firestationService.getPersonByFirestation(1);
    assertThat(firestationResult.getNumberOfChildren()).isEqualTo(1);
    assertThat(firestationResult.getNumberOfAdults()).isEqualTo(1);
    assertThat(firestationResult.getPersonsByFirestationDTO().get(1).getFirstName()).isEqualTo("Suzy");
  }
}
