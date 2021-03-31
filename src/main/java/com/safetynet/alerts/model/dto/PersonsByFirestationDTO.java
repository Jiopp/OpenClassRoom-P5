package com.safetynet.alerts.model.dto;

import lombok.Builder;
import lombok.Data;

/**
 * Represent a person for a firestation
 */
@Data
@Builder
public class PersonsByFirestationDTO {

  private String firstName;
  private String lastName;
  private String address;
  private String phoneNumber;
}
