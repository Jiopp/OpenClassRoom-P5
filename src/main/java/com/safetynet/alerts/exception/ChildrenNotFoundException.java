package com.safetynet.alerts.exception;

public class ChildrenNotFoundException extends Exception {

  public ChildrenNotFoundException(String address) {
    super("Children not found for the address " + address);
  }
}
