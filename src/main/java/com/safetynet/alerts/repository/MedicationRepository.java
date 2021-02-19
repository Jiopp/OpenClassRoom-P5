package com.safetynet.alerts.repository;

import com.safetynet.alerts.model.Medication;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicationRepository extends CrudRepository<Medication, Long> {
  List<Medication> findByPersonId(Long person_id);
}
