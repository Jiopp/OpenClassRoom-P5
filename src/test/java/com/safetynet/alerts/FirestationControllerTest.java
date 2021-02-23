package com.safetynet.alerts;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.controller.FirestationController;
import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.service.FirestationService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = FirestationController.class)
class FirestationControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private FirestationService firestationService;

  public static String asJsonString(final Object obj) {
    try {
      return new ObjectMapper().writeValueAsString(obj);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  public void testPostNewFirestation() throws Exception {
    Firestation firestation = new Firestation();
    firestation.setStationNumber(3);
    firestation.setAddress("Home");

    mockMvc.perform(post("/firestation")
        .contentType(MediaType.APPLICATION_JSON)
        .content(asJsonString(firestation)))
        .andExpect(status().isOk());
  }

  @Test
  public void deleteFirestation() throws Exception {
    Firestation firestation = new Firestation();
    firestation.setStationNumber(3);
    firestation.setAddress("Home");

    mockMvc.perform(post("/firestation")
        .contentType(MediaType.APPLICATION_JSON)
        .content(asJsonString(firestation)));

    mockMvc.perform(delete("/firestation/Home")
        .contentType(MediaType.APPLICATION_JSON)
        .content(asJsonString(firestation)))
        .andExpect(status().isOk());
  }

  @Test
  public void updateFirestation() throws Exception {
    Firestation firestation = new Firestation();
    firestation.setStationNumber(3);
    firestation.setAddress("Home");

    Firestation firestationUpdated = new Firestation();
    firestationUpdated.setAddress("Home");
    firestationUpdated.setStationNumber(1);

    mockMvc.perform(post("/firestation")
        .contentType(MediaType.APPLICATION_JSON)
        .content(asJsonString(firestation)));

    mockMvc.perform(put("/firestation")
        .contentType(MediaType.APPLICATION_JSON)
        .content(asJsonString(firestationUpdated)))
        .andExpect(status().isOk());
  }
}
