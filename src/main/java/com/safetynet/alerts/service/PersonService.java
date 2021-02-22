package com.safetynet.alerts.service;

import com.safetynet.alerts.exception.PersonNotFoundException;
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

  private final PersonRepository personRepository;

  @Autowired
  public PersonService(PersonRepository personRepository) {
    this.personRepository = personRepository;
  }

  @Transactional
  public void deletePersonByName(final String firstName, final String lastName) throws PersonNotFoundException {
    personRepository.delete(personRepository.getPersonByFirstNameAndLastName(firstName, lastName).orElseThrow(
        PersonNotFoundException::new));
    //personRepository.deletePersonByFirstNameAndLastName(firstName, lastName);
  }

  public Optional<Person> getPersonByName(final String firstName, final String lastName) {
    return personRepository.getPersonByFirstNameAndLastName(firstName, lastName);
  }

  public Person savePerson(Person person) {
    return personRepository.save(person);
  }

  @Transactional
  public Optional<Person> updatePerson(Person person, String firstName, String lastName) {
    Optional<Person> p = getPersonByName(firstName, lastName);
    if (p.isPresent()) {
      Person currentPerson = p.get();

      if (person.getEmail() != null) {
        currentPerson.setEmail(person.getEmail());
      }
      if (person.getAddress() != null) {
        currentPerson.setAddress(person.getAddress());
      }
      if (person.getCity() != null) {
        currentPerson.setCity(person.getCity());
      }
      if (person.getZip() != null) {
        currentPerson.setZip(person.getZip());
      }
      if (person.getPhone() != null) {
        currentPerson.setPhone(person.getPhone());
      }
      if (person.getBirthDate() != null) {
        currentPerson.setBirthDate(person.getBirthDate());
      }
      savePerson(currentPerson);
      return Optional.of(currentPerson);
    } else {
      return Optional.empty();
    }
  }
}
