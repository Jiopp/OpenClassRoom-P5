package com.safetynet.alerts.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table()
public class Firestation {

  @Id
  @Column
  private String address;

  @Column
  private Integer stationNumber;

}
