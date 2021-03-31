package com.safetynet.alerts.service;

import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.model.dto.ChildrenDTO;
import com.safetynet.alerts.model.dto.PhoneNumberDTO;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlertService {

  @Autowired
  private PersonService personService;
  @Autowired
  private FirestationService firestationService;

  /**
   * This method will first check if they're children for this address. If at least one is found, it will look for the rest of the family
   *
   * @param address The address for which we are looking for a child
   * @return A ChildrenDTO who contain a list of children and a list of the family
   */
  public ChildrenDTO getAllChildrenAndTheirFamiliesForAnAddress(String address) {
    var children = personService.getChildrenForAnAddress(address);
    return !children.isEmpty() ? new ChildrenDTO(children, personService.getPersonsByAddress(address))
        : new ChildrenDTO();
  }

  /**
   * This method will first check if a firestation exist for this firestation number, and if it's the case, it will check if they're resident for the address served by this firestation. Once the residents found, it will extract their phone number and add it to a list
   *
   * @param firestationNumber The firestation number for which we are looking for
   * @return A list of phone number
   */
  public List<PhoneNumberDTO> getAllPhoneNumberForAFirestation(int firestationNumber) {
    var firestationList = firestationService.getFirestationsByStationNumber(firestationNumber);
    if (firestationList.isEmpty()) {
      return Collections.emptyList();
    }
    var personList = firestationList.stream()
        .map(firestation -> personService.getPersonsByAddress(firestation.getAddress()))
        .flatMap(List::stream).collect(Collectors.toList());
    if (personList.isEmpty()) {
      return Collections.emptyList();
    }
    return personList.stream().map(Person::convertPersonToPhoneNumber)
        .distinct().collect(Collectors.toList());
  }
}
