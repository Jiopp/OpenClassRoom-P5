package com.safetynet.alerts.repository;

import com.safetynet.alerts.model.Allergies;
import com.safetynet.alerts.model.Person;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AllergiesRepository extends CrudRepository<Allergies, Long> {

  void deleteByPerson(Person person);
}
