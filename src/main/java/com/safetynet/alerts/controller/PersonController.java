package com.safetynet.alerts.controller;

import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.PersonService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonController {

  @Autowired
  PersonService personService;

  /**
   * Create - Add a new person
   *
   * @param person An object person
   * @return The person object saved
   */
  @PostMapping("/person")
  public Person createPerson(@RequestBody Person person) {
    return personService.savePerson(person);
  }

  /**
   * Delete - Delete a person
   *
   * @param firstName the first name of the person
   * @param lastName the last name of the person
   */
  @DeleteMapping("/person/{firstName}/{lastName}")
  public void deletePerson(@PathVariable("firstName") final String firstName,
      @PathVariable("lastName") final String lastName) {
    personService.deletePersonByName(firstName, lastName);
  }

  /**
   * Update - Update an existing person
   *
   * @param firstName the first name of the person
   * @param lastName the last name of the person
   * * @return The person object saved
   */
  @PutMapping("/person/{firstName}/{lastName}")
  public Optional<Person> updatePerson(@PathVariable("firstName") final String firstName,
      @PathVariable("lastName") final String lastName, @RequestBody Person person) {

    Optional<Person> p = personService.getPersonByName(firstName, lastName);
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
      personService.savePerson(currentPerson);
      return Optional.of(currentPerson);
    } else {
      return Optional.empty();
    }
  }
}
