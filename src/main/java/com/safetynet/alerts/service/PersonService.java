package com.safetynet.alerts.service;

import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.PersonRepository;
import java.util.Optional;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Data
@Service
public class PersonService {

  @Autowired
  private PersonRepository personRepository;

  @Transactional
  public void deletePersonByName(final String firstName, final String lastName) {
    personRepository.deletePersonByFirstNameAndLastName(firstName, lastName);
  }

  public Optional<Person> getPersonByName(final String firstName, final String lastName) {
    return personRepository.getPersonByFirstNameAndLastName(firstName, lastName);
  }

  public Person savePerson(Person person) {
    return personRepository.save(person);
  }

}
