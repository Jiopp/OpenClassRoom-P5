package com.safetynet.alerts.service;

import com.safetynet.alerts.exception.PersonNotFoundException;
import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.model.dto.EmailDTO;
import com.safetynet.alerts.model.dto.FireDTO;
import com.safetynet.alerts.model.primarykey.MyCompositePK;
import com.safetynet.alerts.repository.FirestationRepository;
import com.safetynet.alerts.repository.PersonRepository;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Data
@Service
@Transactional
public class PersonService {

  @Autowired
  private PersonRepository personRepository;
  @Autowired
  private FirestationRepository firestationRepository;

  /**
   * Delete a person in database. The person should exist or an exception will be thrown
   *
   * @param firstName The firstname of the person
   * @param lastName The lastname of the person
   * @throws PersonNotFoundException If the person doesn't exist
   */
  public void deletePersonByName(final String firstName, final String lastName) throws PersonNotFoundException {
    personRepository.delete(
        personRepository.findPersonById(MyCompositePK.builder().firstName(firstName).lastName(lastName).build())
            .orElseThrow(PersonNotFoundException::new));
  }

  /**
   * Save a person in database
   *
   * @param person The person to save
   * @return The person saved
   */
  public Person savePerson(Person person) {
    return personRepository.save(person);
  }

  /**
   * Save a list of persons in database
   *
   * @param persons The list of persons to save
   */
  public void savePersons(List<Person> persons) {
    personRepository.saveAll(persons);
  }

  /**
   * Return a person updated
   *
   * @param person The person to update
   * @return The person updated
   * @throws PersonNotFoundException If the person is not found
   */
  public Person updatePerson(Person person) throws PersonNotFoundException {
    Optional<Person> optionalPerson = personRepository.findPersonById(person.getId());
    if (optionalPerson.isEmpty()) {
      throw new PersonNotFoundException();
    } else {
      Person currentPerson = optionalPerson.get();
      currentPerson.setEmail(person.getEmail());
      currentPerson.setAddress(person.getAddress());
      currentPerson.setCity(person.getCity());
      currentPerson.setZip(person.getZip());
      currentPerson.setPhone(person.getPhone());
      currentPerson.setBirthDate(person.getBirthDate());
      return savePerson(currentPerson);
    }
  }

  /**
   * Return a person or an optional empty for an id
   *
   * @param firstName The firstname of the person
   * @param lastName The lastname of the person
   * @return The person found in database
   */
  public Person getPersonByName(final String firstName, final String lastName) {
    Optional<Person> optionalPerson = personRepository
        .findPersonById(MyCompositePK.builder().firstName(firstName).lastName(lastName).build());
    return optionalPerson.orElse(null);
  }

  /**
   * Return a list of persons for an address
   *
   * @param address The address for which we are looking for persons
   * @return The persons found in database
   */
  public List<Person> getPersonsByAddress(final String address) {
    return personRepository.findAllByAddress(address);
  }

  /**
   * Return a list of persons for a city
   *
   * @param city The city for which we are looking for persons
   * @return The persons found in database
   */
  public List<Person> getPersonsByCity(String city) {
    return personRepository.findAllByCity(city);
  }

  /**
   * Return a list of children aged under 18 for an address
   *
   * @param address The address for which we are looking for children
   * @return The persons (children) found in database
   */
  public List<Person> getChildrenForAnAddress(final String address) {
    return personRepository.findAllByAddressAndBirthDateAfter(address, LocalDate.now().minus(Period.ofYears(18)));
  }

  /**
   * Return the station number that served this address and the list of residents for this address
   *
   * @param address the address for which we are looking for residents and firestation
   * @return the station number that served this address and the list of residents for this address
   */
  public FireDTO getPersonAndFirestationNumberForAnAddress(String address) {
    FireDTO fireDTO = FireDTO.builder().build();
    Optional<Firestation> optionalFirestation = firestationRepository.findByAddress(address);
    optionalFirestation.ifPresent(value -> fireDTO.setFirestationNumber(value.getStationNumber()));
    fireDTO.setPersons(getPersonsByAddress(address));
    return fireDTO;
  }

  /**
   * Return all residents' email for a city
   *
   * @param city for which we ant emails
   * @return List of emails
   */
  public List<EmailDTO> getEmailByCity(String city) {
    return getPersonsByCity(city).stream().map(Person::convertPersonToEmail).distinct().collect(Collectors.toList());
  }
}
