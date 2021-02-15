package com.openclassrooms.safetyNetAlerts.service;

import org.springframework.boot.CommandLineRunner;

public class PrintInConsole implements CommandLineRunner {

  @Override
  public void run(String... args) throws Exception {
    System.out.println("Hello World");
  }
}
