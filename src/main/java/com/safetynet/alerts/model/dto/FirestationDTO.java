package com.safetynet.alerts.model.dto;

import com.safetynet.alerts.model.Firestation;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represent a firestation
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FirestationDTO {

  @NotBlank(message = "Address is mandatory")
  private String address;
  @NotNull(message = "Station is mandatory")
  private Integer station;

  /**
   * Convert this DTO into a Firestation
   *
   * @return a Firestation
   */
  public Firestation convertToFirestation() {
    return Firestation.builder().address(getAddress()).stationNumber(getStation()).build();
  }
}
