package com.safetynet.alerts.controller;

import static com.safetynet.alerts.utils.UtilsTest.asJsonString;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.safetynet.alerts.exception.FirestationNotFoundException;
import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.model.dto.FirestationDTO;
import com.safetynet.alerts.service.FirestationService;
import com.safetynet.alerts.service.FloodService;
import com.safetynet.alerts.service.InitService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = FirestationController.class)
class FirestationControllerTest {

  private static final String ADDRESS = "Home";
  private static final int THREE = 3;

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private FloodService floodService;

  @MockBean
  private InitService initService;

  @MockBean
  private FirestationService firestationService;

  @Test
  void createFirestation_should_return_200_and_firestation_object() throws Exception {
    // Arrange
    FirestationDTO firestationDTO = FirestationDTO.builder().address(ADDRESS).station(THREE).build();

    // Act
    mockMvc.perform(post("/firestation")
        .contentType(MediaType.APPLICATION_JSON)
        .content(asJsonString(firestationDTO)))
        // Assert
        .andExpect(status().isOk());
  }

  @Test
  void deletePerson_should_return_200_if_firestation_is_found() throws Exception {

    mockMvc.perform(delete("/firestation/Home"))
        .andExpect(status().isOk());
  }

  @Test
  void deletePerson_should_return_404_if_firestation_was_not_found() throws Exception {

    doThrow(new FirestationNotFoundException(ADDRESS)).when(firestationService).deleteFirestationByAddress(ADDRESS);

    mockMvc.perform(delete("/firestation/Home"))
        .andExpect(status().isBadRequest());
  }

  @Test
  void updateFirestation_should_return_200_if_firestation_is_found() throws Exception {
    // Arrange
    Firestation firestation = Firestation.builder().address(ADDRESS).stationNumber(THREE).build();
    FirestationDTO firestationDTO = FirestationDTO.builder().address(ADDRESS).station(THREE).build();
    when(firestationService.updateFirestation(firestation)).thenReturn(firestation);

    // Act
    mockMvc.perform(put("/firestation")
        .contentType(MediaType.APPLICATION_JSON)
        .content(asJsonString(firestationDTO)))
        // Assert
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.address", equalTo(ADDRESS)))
        .andExpect(jsonPath("$.stationNumber", equalTo(THREE)));
  }

  @Test
  void updateFirestation_should_return_404_if_firestation_was_not_found() throws Exception {
    // Arrange
    Firestation firestation = Firestation.builder().address(ADDRESS).stationNumber(THREE).build();
    FirestationDTO firestationDTO = FirestationDTO.builder().address(ADDRESS).station(THREE).build();
    doThrow(new FirestationNotFoundException(ADDRESS)).when(firestationService).updateFirestation(firestation);

    // Act
    mockMvc.perform(put("/firestation")
        .contentType(MediaType.APPLICATION_JSON)
        .content(asJsonString(firestationDTO)))
        // Assert
        .andExpect(status().isBadRequest());
  }
}

