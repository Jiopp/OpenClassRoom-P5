package com.safetynet.alerts;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.model.dto.InitDTO;
import com.safetynet.alerts.service.InitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;

@SpringBootApplication
public class AlertsApplication {

  private static final Logger LOGGER = LoggerFactory.getLogger(AlertsApplication.class);

  @Value("classpath:data/data.json")
  private Resource dataResourceFile;

  @Autowired
  private InitService initService;

  public static void main(String[] args) {
    SpringApplication.run(AlertsApplication.class, args);
  }

  @Bean
  public CommandLineRunner demo() {
    return (args) -> {
      // Load data from data.json at startup of application
      LOGGER.info("Load data from data.json file");
      LOGGER.info("-------------------------------");
      ObjectMapper mapper = new ObjectMapper();
      initService.init(mapper.readValue(dataResourceFile.getFile(), InitDTO.class));
      LOGGER.info("Datas are loaded successfully.");
    };
  }

}
