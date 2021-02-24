package com.safetynet.alerts.model.dto;

import com.safetynet.alerts.model.Person;
import java.util.List;
import lombok.Builder;
import lombok.Data;

/**
 * Represent a list of person for a firestation
 */
@Data
@Builder
public class FireDTO {

  private int firestationNumber;
  private List<Person> persons;
}
