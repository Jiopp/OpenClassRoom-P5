package com.safetynet.alerts.exception;

public class PersonNotFoundException extends Exception {

  public PersonNotFoundException(Integer stationNumber) {
    super("Person not found for the station number " + stationNumber);
  }

  public PersonNotFoundException() {
    super("Person not found");
  }
}
