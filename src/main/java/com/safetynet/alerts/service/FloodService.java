package com.safetynet.alerts.service;

import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.model.dto.FloodDTO;
import com.safetynet.alerts.model.dto.PersonsByAddress;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Data
@Service
public class FloodService {

  @Autowired
  private FirestationService firestationService;
  @Autowired
  private PersonService personService;

  /**
   * This method will look for every person for each station number
   *
   * @param stationNumbers A list of station number
   * @return A list of every person grouped by firestation
   */
  public List<FloodDTO> getAddressAndPersonsForAListOfStationNumbers(List<Integer> stationNumbers) {
    return stationNumbers.stream().map(this::initFloodDTO).collect(Collectors.toList());
  }

  /**
   * This method will create a FloodDTO which contain the list of person for a station number
   *
   * @param stationNumber The firestation number
   * @return A list of person for a firestation
   */
  public FloodDTO initFloodDTO(Integer stationNumber) {
    return FloodDTO.builder().stationNumber(stationNumber).personsByAddressList(
        firestationService.getFirestationsByStationNumber(stationNumber).stream().map(this::initPersonsByAddress)
            .collect(Collectors.toList())).build();
  }

  /**
   * This method will research every person for an address in the object Firestation
   *
   * @param firestation the firestation for which we are looking for persons
   * @return A list of persons grouped by address
   */
  public PersonsByAddress initPersonsByAddress(Firestation firestation) {
    return PersonsByAddress.builder().address(firestation.getAddress()).floodPersonDTOList(
        personService.getPersonsByAddress(firestation.getAddress()).stream().map(Person::convertPersonToFloodPersonDTO)
            .collect(Collectors.toList())).build();
  }
}
