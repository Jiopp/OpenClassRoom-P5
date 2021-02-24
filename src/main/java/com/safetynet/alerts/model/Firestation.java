package com.safetynet.alerts.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represent the link between a firestation and an address
 */
@Data
@Entity
@Table()
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Firestation {

  @Id
  @Column
  private String address;

  @Column
  private Integer stationNumber;
}
