package com.safetynet.alerts.exception;

import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@EqualsAndHashCode
public class ErrorDetails {

  private LocalDateTime timestamp;
  private String message;
  private String details;

  public ErrorDetails(LocalDateTime now, String message, String details) {
    this.timestamp = now;
    this.message = message;
    this.details = details;
  }
}
