package com.safetynet.alerts.model.dto;

import lombok.Builder;
import lombok.Data;

/**
 * Represent an email
 */
@Data
@Builder
public class EmailDTO {

  private String email;
}
