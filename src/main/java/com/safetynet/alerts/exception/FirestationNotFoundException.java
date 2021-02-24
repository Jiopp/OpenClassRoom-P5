package com.safetynet.alerts.exception;

public class FirestationNotFoundException extends Exception {

  public FirestationNotFoundException(String address) {
    super("Firestation not found for the address " + address);
  }

  public FirestationNotFoundException(Integer stationNumber) {
    super("Firestation not found for the station number " + stationNumber);
  }
}
