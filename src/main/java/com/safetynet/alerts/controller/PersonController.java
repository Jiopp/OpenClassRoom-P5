package com.safetynet.alerts.controller;

import com.safetynet.alerts.exception.PersonNotFoundException;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.model.dto.EmailDTO;
import com.safetynet.alerts.model.dto.FireDTO;
import com.safetynet.alerts.model.dto.PersonDTO;
import com.safetynet.alerts.service.PersonService;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Endpoint for everything related to a person
 */
@RestController
@Validated
public class PersonController {

  @Autowired
  private PersonService personService;

  /**
   * This method allow you to create a new person
   *
   * @param personDTO example :
   * {
   * "firstName": "John",
   * "lastName": "Boyd",
   * "address": "1509 Culver St",
   * "city": "Culver",
   * "zip": "97451",
   * "phone": "841-874-6512",
   * "email": "jaboyd@email.com"
   * }
   * @return The new person created
   */
  @PostMapping("/person")
  public Person createPerson(@Valid @RequestBody PersonDTO personDTO) {
    return personService.savePerson(personDTO.convertToPerson());
  }

  /**
   * This method allow you to update a person
   *
   * @param personDTO example :
   * {
   * "firstName": "John",
   * "lastName": "Boyd",
   * "address": "1509 Culver St",
   * "city": "Culver",
   * "zip": "97451",
   * "phone": "841-874-6512",
   * "email": "jaboyd@email.com"
   * }
   * @return The person updated
   * @throws PersonNotFoundException can be throw if the person doesn't exist
   */
  @PutMapping("/person")
  public Person updatePerson(@Valid @RequestBody PersonDTO personDTO) throws PersonNotFoundException {
    return personService.updatePerson(personDTO.convertToPerson());
  }

  /**
   * This method allow you to delete a person
   *
   * @param firstName The first name of the person. Cannot be null
   * @param lastName The last name of the person. Cannot be null
   * @throws PersonNotFoundException can be throw if the person doesn't exist
   */
  @DeleteMapping("/person/{firstName}/{lastName}")
  public void deletePerson(@NotBlank(message = "First name cannot be empty") @PathVariable String firstName,
      @NotBlank(message = "Last name cannot be empty") @PathVariable String lastName) throws PersonNotFoundException {
    personService.deletePersonByName(firstName, lastName);
  }

  /**
   * This method will return the firestation number that serves this address as well as the list of person living at this address
   *
   * @param address The address for which you are looking for residents.
   * @return The number of the firestation that serves this address as well as the list of person living at this address
   */
  @GetMapping("/fire")
  public FireDTO getPersonAndFirestationNumber(@RequestParam String address) {
    return personService.getPersonAndFirestationNumberForAnAddress(address);
  }

  /**
   * This method will return the information about a person
   *
   * @param firstName The first name of the person. Cannot be null
   * @param lastName The last name of the person. Cannot be null
   * @return All information about the person including his medical record
   */
  @GetMapping("/personInfo")
  public Person getPersonAndFirestationNumber(
      @NotBlank(message = "First name cannot be empty") @RequestParam String firstName,
      @NotBlank(message = "Last name cannot be empty") @RequestParam String lastName) {
    return personService.getPersonByName(firstName, lastName);
  }

  /**
   * This method will return all the residents' email for a city
   *
   * @param city The name of the city
   * @return residents' email
   */
  @GetMapping("/communityEmail")
  public List<EmailDTO> getEmailByCity(@RequestParam String city) {
    return personService.getEmailByCity(city);
  }
}
