package com.safetynet.alerts.model.dto;

import com.safetynet.alerts.model.Allergies;
import com.safetynet.alerts.model.Medication;
import java.util.List;
import lombok.Builder;
import lombok.Data;

/**
 * Represent a person for a flood alert
 */
@Data
@Builder
public class FloodPersonDTO {

  private String firstName;
  private String lastName;
  private String phoneNumber;
  private Integer age;
  private List<Medication> medicationList;
  private List<Allergies> allergiesList;
}
