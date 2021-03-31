package com.safetynet.alerts.service;

import com.safetynet.alerts.exception.FirestationNotFoundException;
import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.model.dto.ResidentByFirestationDTO;
import com.safetynet.alerts.repository.FirestationRepository;
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
public class FirestationService {

  @Autowired
  private PersonService personService;
  @Autowired
  private FirestationRepository firestationRepository;

  /**
   * It will return the firestation that served the address in parameter
   *
   * @param address The address for which we are searching for a firestation
   * @return The firestation that serve the address
   */
  public Optional<Firestation> getFirestationByAddress(final String address) {
    return firestationRepository.findByAddress(address);
  }

  /**
   * It will return a list of firestation for every address served by this firestation
   *
   * @param station_number The firestation number for which we are looking for address
   * @return The address served by the firestation
   */
  public List<Firestation> getFirestationsByStationNumber(final Integer station_number) {
    return firestationRepository.findAllByStationNumber(station_number);
  }

  /**
   * It will save the firestation in database
   */
  public void saveFirestation(Firestation firestation) {
    if (firestation != null) {
      firestationRepository.save(firestation);
    }
  }

  /**
   * It will save the firestations in database
   */
  public void saveFirestations(List<Firestation> firestations) {
    firestationRepository.saveAll(firestations);
  }

  /**
   * This method will delete the link firestation -> address
   *
   * @param address the address for which we ant to delete the link firestation -> address
   * @throws FirestationNotFoundException is thrown if there's no firestation found
   */
  public void deleteFirestationByAddress(String address) throws FirestationNotFoundException {
    if (address != null) {
      firestationRepository.delete(getFirestationByAddress(address).orElseThrow(
          () -> new FirestationNotFoundException(address)));
    }
  }

  /**
   * This method will update the link between the firestation and the address
   *
   * @param firestation the firestation to update
   * @return The link between the firestation and the address updated
   * @throws FirestationNotFoundException is thrown if there's no firestation found
   */
  public Firestation updateFirestation(Firestation firestation) throws FirestationNotFoundException {
    if (firestation != null) {
      Firestation currentFirestation =
          firestationRepository.findByAddress(firestation.getAddress()).orElseThrow(
              () -> new FirestationNotFoundException(firestation.getAddress()));

      currentFirestation.setStationNumber(firestation.getStationNumber());
      saveFirestation(currentFirestation);
      return currentFirestation;
    }
    return null;
  }

  /**
   * It will search every address served by the firestation and then, for every address, it'll search for every residents a this address.
   * Then it'll look for every adult and every children in the response and set the number in the residentByFirestationDTO.
   *
   * @param stationNumber the firestation number for which we are looking for residents
   * @return ResidentByFirestationDTO who containt the number of adults, the number of children and all the residents served for a firestation number
   */
  public ResidentByFirestationDTO getPersonByFirestation(int stationNumber) {
    List<Person> flatPersons = firestationRepository.findAllByStationNumber(stationNumber).stream()
        .map(firestation -> personService.getPersonsByAddress(firestation.getAddress())).flatMap(List::stream)
        .collect(Collectors.toList());

    ResidentByFirestationDTO residentByFirestationDTO = ResidentByFirestationDTO.builder()
        .personsByFirestationDTO(flatPersons.stream()
            .map(Person::convertPersonToPersonsByFirestationDTO)
            .collect(Collectors.toList())).build();

    List<Person> adults = flatPersons.stream()
        .filter(person -> Person.calculateAge(person.getBirthDate()) >= 18)
        .collect(Collectors.toList());
    residentByFirestationDTO.setNumberOfAdults(adults.size());

    List<Person> children = flatPersons.stream()
        .filter(person -> Person.calculateAge(person.getBirthDate()) < 18)
        .collect(Collectors.toList());
    residentByFirestationDTO.setNumberOfChildren(children.size());

    return residentByFirestationDTO;
  }
}
