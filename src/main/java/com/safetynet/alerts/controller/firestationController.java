package com.safetynet.alerts.controller;

import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.service.FirestationService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class firestationController {

  @Autowired
  private FirestationService firestationService;

  /**
   * Create - Add a new firestation/address maping
   *
   * @param firestation An object firestation
   * @return The firestation object saved
   */
  @PostMapping("/firestation")
  public Firestation createFirestation(@RequestBody Firestation firestation) {
    return firestationService.saveFirestation(firestation);
  }

  /**
   * Update - Update an existing firestation/address mapping
   *
   * @param firestation - The firestation object updated
   * @return The firestation object saved
   */
  @PutMapping("/firestation")
  public Optional<Firestation> updateFirestation(@RequestBody Firestation firestation) {
    Optional<Firestation> f = firestationService.getFirestationByAddress(firestation.getAddress());
    if (f.isPresent()) {
      Firestation currentFirestation = f.get();

      Integer station_number = firestation.getStationNumber();
      if (station_number != null) {
        currentFirestation.setStationNumber(station_number);
      }
      firestationService.saveFirestation(currentFirestation);
      return Optional.of(currentFirestation);
    } else {
      return Optional.empty();
    }
  }

  /**
   * Delete - Delete a firestation/address mapping
   *
   * @param id - The id of the firestation/address mapping to delete
   */
  @DeleteMapping("/firestation/{id}")
  public void deleteFirestation(@PathVariable("id") final Long id) {
    firestationService.deleteFirestation(id);
  }
}
