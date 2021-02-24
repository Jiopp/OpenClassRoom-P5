package com.safetynet.alerts;

import static org.assertj.core.api.Assertions.assertThat;

import com.safetynet.alerts.service.PersonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AlertsApplicationTests {

  @Autowired
  PersonService personService;

  @Test
  void contextLoads() {
    assertThat(personService).isNotNull();
  }
}
