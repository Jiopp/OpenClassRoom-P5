package com.safetynet.alerts.model.dto;

import java.util.List;
import lombok.Builder;
import lombok.Data;

/**
 * Represent a list of persons for a firestation number
 */
@Data
@Builder
public class FloodDTO {

  private int stationNumber;
  private List<PersonsByAddress> personsByAddressList;

}
