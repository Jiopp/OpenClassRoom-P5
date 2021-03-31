package com.safetynet.alerts.model.dto;

import java.util.List;
import lombok.Builder;
import lombok.Data;

/**
 * Represent the number of children and adults for an address and the list of this people
 */
@Data
@Builder
public class ResidentByFirestationDTO {

  @Builder.Default
  private Integer numberOfAdults = 0;
  @Builder.Default
  private Integer numberOfChildren = 0;
  private List<PersonsByFirestationDTO> personsByFirestationDTO;
}
