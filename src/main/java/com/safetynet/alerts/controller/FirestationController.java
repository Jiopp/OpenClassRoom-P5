package com.safetynet.alerts.controller;

import com.safetynet.alerts.exception.FirestationNotFoundException;
import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.model.dto.FirestationDTO;
import com.safetynet.alerts.model.dto.FloodDTO;
import com.safetynet.alerts.model.dto.ResidentByFirestationDTO;
import com.safetynet.alerts.service.FirestationService;
import com.safetynet.alerts.service.FloodService;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Endpoint for everything linked to the firestation
 */
@RestController
@Validated
public class FirestationController {

  @Autowired
  private FirestationService firestationService;
  @Autowired
  private FloodService floodService;

  /**
   * This method allow you to create a link between a firestation number and an address
   *
   * @param firestationDTO example : { "address" : "home", "station" : 1 }
   */
  @PostMapping("/firestation")
  public void createFirestation(@Valid @RequestBody FirestationDTO firestationDTO) {
    firestationService.saveFirestation(firestationDTO.convertToFirestation());
  }

  /**
   * This method allow you to update a link between a firestation number and an address
   *
   * @param firestationDTO example : { "address" : "home", "station" : 1 }
   * @return The link between a firestation number and an address
   * @throws FirestationNotFoundException A FirestationNotFoundException is thrown if the address don't exist
   */
  @PutMapping("/firestation")
  public Firestation updateFirestation(@Valid @RequestBody FirestationDTO firestationDTO)
      throws FirestationNotFoundException {
    return firestationService.updateFirestation(firestationDTO.convertToFirestation());
  }

  /**
   * This method allow you to delete a link between a firestation number and an address
   *
   * @param address The address you wish to delete
   * @throws FirestationNotFoundException A FirestationNotFoundException is thrown if the address don't exist
   */
  @DeleteMapping("/firestation/{address}")
  public void deleteFirestationByAddress(@PathVariable String address)
      throws FirestationNotFoundException {
    firestationService.deleteFirestationByAddress(address);
  }

  /**
   * This method return the list of persons linked to the firestation as well as the number of adults and children
   *
   * @param stationNumber The number of the fire station for which you are looking for residents.
   * @return The number of adults and children linked to the firestation as well as the list of resident with their address and phone number
   */
  @GetMapping("/firestation")
  public ResidentByFirestationDTO getPersonByFirestation(@RequestParam @Positive int stationNumber) {
    return firestationService.getPersonByFirestation(stationNumber);
  }

  /**
   * This method return a list of all homes linked to the fire station. It group peoples by address.
   * You'll also find the name, phone number, age and medical record for the residents
   *
   * @param stations A list of station for which you are looking for homes and residents
   * @return People with their name, phone number, age and medical record for the residents grouped by address
   */
  @GetMapping("/flood/stations")
  public List<FloodDTO> getAddressAndPersonsForAListOfStationNumbers(@RequestParam List<Integer> stations) {
    return floodService.getAddressAndPersonsForAListOfStationNumbers(stations);
  }
}
