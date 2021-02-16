package com.openclassrooms.safetyNetAlerts.model;

import org.springframework.stereotype.Component;

@Component
public class BusinessService {

  public HelloWorld getHelloWorld() {
    return new HelloWorld();
  }
}
