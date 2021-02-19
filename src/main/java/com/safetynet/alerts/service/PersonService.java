package com.safetynet.alerts.service;

import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.PersonRepository;
import java.util.Optional;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Data
@Service
public class PersonService {

  @Autowired
  private PersonRepository personRepository;

  public Optional<Person> getPerson(final Long id) {
    return personRepository.findById(id);
  }

  public Iterable<Person> getPersons() {
    return personRepository.findAll();
  }

  public void deletePersonByID(final Long id) {
    personRepository.deleteById(id);
  }

  public void deletePersonByName(final String firstName, final String lastName) {
    personRepository.deletePersonByFirstNameAndLastName(firstName, lastName);
  }

  public Person savePerson(Person person) {
    return personRepository.save(person);
  }

}
