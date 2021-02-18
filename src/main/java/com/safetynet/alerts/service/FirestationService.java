package com.safetynet.alerts.service;

import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.repository.FirestationRepository;
import java.util.Optional;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Data
@Service
public class FirestationService {

  @Autowired
  private FirestationRepository firestationRepository;

  public Optional<Firestation> getFirestation(final Long id) {
    return firestationRepository.findById(id);
  }

  public Optional<Firestation> getFirestationByAddress(final String address) {
    return firestationRepository.findByAddress(address);
  }

  public Optional<Firestation> getFirestationByStationNumber(final Integer station_number) {
    return firestationRepository.findByStationNumber(station_number);
  }

  public Iterable<Firestation> getFireStations() {
    return firestationRepository.findAll();
  }

  public void deleteFirestation(final Long id) {
    firestationRepository.deleteById(id);
  }

  public Firestation saveFirestation(Firestation firestation) {
    return firestationRepository.save(firestation);
  }

}
