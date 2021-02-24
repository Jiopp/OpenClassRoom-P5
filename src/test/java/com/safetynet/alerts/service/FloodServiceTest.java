package com.safetynet.alerts.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.model.dto.FloodDTO;
import com.safetynet.alerts.model.primarykey.MyCompositePK;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class FloodServiceTest {

  private final static String ADDRESS = "947 E. Rose Dr";

  @InjectMocks
  private FloodService floodService;
  @Mock
  private FirestationService firestationService;
  @Mock
  private PersonService personService;

  @BeforeEach
  void init_mocks() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  @DisplayName("When getAddressAndPersonsForAListOfStationNumbers for an unknown firestation, then return empty list ")
  void getAddressAndPersonsForAListOfStationNumbers_with_an_unknown_station() {
    // ARRANGE
    when(firestationService.getFirestationsByStationNumber(1))
        .thenReturn(new ArrayList<>());
    //ACT
    List<FloodDTO> floodDTOListResult = floodService.getAddressAndPersonsForAListOfStationNumbers(List.of(1));
    assertThat(floodDTOListResult.get(0).getPersonsByAddressList().size()).isZero();
  }

  @Test
  @DisplayName(
      "When getAddressAndPersonsForAListOfStationNumbers for an exist firestation, but without persons should return empty list ")
  void getAddressAndPersonsForAListOfStationNumbers_with_an_exist_station_but_no_persons_found() {
    // ARRANGE
    when(firestationService.getFirestationsByStationNumber(1))
        .thenReturn(List.of(Firestation.builder().address(ADDRESS).stationNumber(1).build()));
    when(personService.getPersonsByAddress(ADDRESS)).thenReturn(new ArrayList<>());
    //ACT
    List<FloodDTO> floodDTOListResult = floodService.getAddressAndPersonsForAListOfStationNumbers(List.of(1));
    assertThat(floodDTOListResult.get(0).getPersonsByAddressList().get(0).getFloodPersonDTOList().size()).isZero();
  }

  @Test
  @DisplayName(
      "When getAddressAndPersonsForAListOfStationNumbers for an exist firestation and persons should return list of FloodDTO")
  void getAddressAndPersonsForAListOfStationNumbers_with_an_exist_station_and_persons() {
    // ARRANGE
    when(firestationService.getFirestationsByStationNumber(1))
        .thenReturn(List.of(Firestation.builder().address(ADDRESS).stationNumber(1).build()));
    when(personService.getPersonsByAddress(ADDRESS)).thenReturn(
        List.of(
            Person.builder().id(MyCompositePK.builder().firstName("John").lastName("Boyd").build())
                .birthDate(LocalDate.of(1988, 3, 6)).build(),
            Person.builder().id(MyCompositePK.builder().firstName("Suzy").lastName("Boyd").build())
                .birthDate(LocalDate.of(2018, 3, 6)).build()));
    //ACT
    List<FloodDTO> floodDTOListResult = floodService.getAddressAndPersonsForAListOfStationNumbers(List.of(1));
    assertThat(floodDTOListResult.get(0).getPersonsByAddressList().get(0).getFloodPersonDTOList().get(1).getFirstName())
        .isEqualTo("Suzy");
  }
}
