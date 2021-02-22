package com.safetynet.alerts.controller;

import com.safetynet.alerts.model.DTO.InitDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InitController {

  @PostMapping("/init")
  public InitDTO Init(@RequestBody InitDTO initDTO){
    return initDTO;
  }
}
