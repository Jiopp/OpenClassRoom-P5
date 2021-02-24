package com.safetynet.alerts.model.dto;

import lombok.Builder;
import lombok.Data;

/**
 * Represent a phone number
 */
@Data
@Builder
public class PhoneNumberDTO {

  private String phoneNumber;
}
