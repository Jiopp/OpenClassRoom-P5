package com.openclassrooms.safetyNetAlerts.service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class PrintInConsole implements CommandLineRunner {

  @Override
  public void run(String... args) throws Exception {
    System.out.println("Hello World");
  }
}
