package com.safetynet.alerts.model.dto;

import java.util.List;
import lombok.Builder;
import lombok.Data;

/**
 * Represent a list of FloodPersonDTO for an address
 */
@Data
@Builder
public class PersonsByAddress {

  private String address;
  private List<FloodPersonDTO> floodPersonDTOList;
}
