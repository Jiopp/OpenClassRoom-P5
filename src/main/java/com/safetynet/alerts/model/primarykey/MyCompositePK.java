package com.safetynet.alerts.model.primarykey;

import java.io.Serializable;
import javax.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represent the primary key for the Person
 */
@Embeddable
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MyCompositePK implements Serializable {

  private String firstName;
  private String lastName;
}
