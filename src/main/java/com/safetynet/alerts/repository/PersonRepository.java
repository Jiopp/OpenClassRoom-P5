package com.safetynet.alerts.repository;

import com.safetynet.alerts.model.Person;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends CrudRepository<Person, Long> {

  Optional<Person> getPersonByFirstNameAndLastName(String firstName, String lastName);
}
