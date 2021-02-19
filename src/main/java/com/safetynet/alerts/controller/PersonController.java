package com.safetynet.alerts.controller;

import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

  @DeleteMapping("/person/{firstName}/{lastName}")
  public void deletePerson(@PathVariable("firstName") final String firstName,
      @PathVariable("lastName") final String lastName) {
    personService.deletePersonByName(firstName, lastName);
  }
}
