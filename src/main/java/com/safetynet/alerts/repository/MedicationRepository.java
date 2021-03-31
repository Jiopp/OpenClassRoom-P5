package com.safetynet.alerts.repository;

import com.safetynet.alerts.model.Medication;
import com.safetynet.alerts.model.Person;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicationRepository extends CrudRepository<Medication, Long> {

  void deleteByPerson(Person person);
}
