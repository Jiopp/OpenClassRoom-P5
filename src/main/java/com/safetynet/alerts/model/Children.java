package com.safetynet.alerts.model;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Data;

/**
 * Represent a children
 */
@Data
@Builder
public class Children {

  private String firstName;
  private String lastName;
  private LocalDate birthDate;
}

