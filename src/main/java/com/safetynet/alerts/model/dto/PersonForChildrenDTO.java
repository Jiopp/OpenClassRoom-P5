package com.safetynet.alerts.model.dto;

import lombok.Builder;
import lombok.Data;

/**
 * Represent a family person for the child alert
 */
@Builder
@Data
public class PersonForChildrenDTO {

  private String firstName;
  private String lastName;
}
