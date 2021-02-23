package com.safetynet.alerts.controller;

import com.safetynet.alerts.model.DTO.InitDTO;
import com.safetynet.alerts.service.InitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InitController {

  final InitService initService;

  @Autowired
  public InitController(InitService initService) {
    this.initService = initService;
  }


  @PostMapping("/init")
  public InitDTO Init(@RequestBody InitDTO initDTO){
    initService.init(initDTO);
    return initDTO;
  }
}
