package com.safetynet.alerts.repository;

import com.safetynet.alerts.model.Firestation;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FirestationRepository extends CrudRepository<Firestation, Long> {
  Optional<Firestation> findByAddress(String address);
  Optional<Firestation> findByStationNumber(Integer station_number);
  void deleteFirestationByAddress(String address);


}
