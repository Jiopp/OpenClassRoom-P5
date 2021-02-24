package com.safetynet.alerts.controller;

import static com.safetynet.alerts.utils.UtilsTest.asJsonString;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.safetynet.alerts.exception.PersonNotFoundException;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.model.dto.PersonDTO;
import com.safetynet.alerts.model.primarykey.MyCompositePK;
import com.safetynet.alerts.service.InitService;
import com.safetynet.alerts.service.PersonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = PersonController.class)
class PersonControllerTest {

  private final static String FIRSTNAME = "John";
  private final static String LASTNAME = "Boyd";
  @Autowired
  private MockMvc mockMvc;
  @MockBean
  private PersonService personService;
  @MockBean
  private InitService initService;

  @Test
  void createPerson_should_return_200_and_person_object() throws Exception {
    // Arrange
    PersonDTO personDTO = PersonDTO.builder().firstName(FIRSTNAME).lastName(LASTNAME).build();
    Person person = Person.builder().id(MyCompositePK.builder().firstName(FIRSTNAME).lastName(LASTNAME).build())
        .build();
    when(personService.savePerson(person)).thenReturn(person);

    // Act
    mockMvc.perform(post("/person")
        .contentType(MediaType.APPLICATION_JSON)
        .content(asJsonString(personDTO)))
        // Assert
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id.lastName", equalTo(LASTNAME)))
        .andExpect(jsonPath("$.id.firstName", equalTo(FIRSTNAME)))
        .andExpect(jsonPath("$.city").doesNotExist());
  }

  @Test
  void deletePerson_should_return_400_if_person_was_not_found() throws Exception {
    doThrow(new PersonNotFoundException()).when(personService).deletePersonByName(FIRSTNAME, LASTNAME);

    mockMvc.perform(delete("/person/John/Boyd"))
        .andExpect(status().isBadRequest());
  }

  @Test
  void deletePerson_should_return_200_if_person_was_found() throws Exception {

    mockMvc.perform(delete("/person/John/Boyd"))
        .andExpect(status().isOk());
    verify(personService).deletePersonByName(FIRSTNAME, LASTNAME);
  }


  @Test
  void updatePerson_should_return_200_if_person_is_found() throws Exception {
    // Arrange
    PersonDTO personDTO = PersonDTO.builder().firstName(FIRSTNAME).lastName(LASTNAME).build();
    Person person = Person.builder().id(MyCompositePK.builder().firstName(FIRSTNAME).lastName(LASTNAME).build())
        .build();
    when(personService.updatePerson(person)).thenReturn(person);

    // Act
    mockMvc.perform(put("/person")
        .contentType(MediaType.APPLICATION_JSON)
        .content(asJsonString(personDTO)))
        // Assert
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id.lastName", equalTo(LASTNAME)))
        .andExpect(jsonPath("$.id.firstName", equalTo(FIRSTNAME)))
        .andExpect(jsonPath("$.city").doesNotExist());
  }

  @Test
  void updatePerson_should_return_400_if_person_was_not_found() throws Exception {
    // Arrange
    PersonDTO personDTO = PersonDTO.builder().firstName(FIRSTNAME).lastName(LASTNAME).build();
    Person person = Person.builder().id(MyCompositePK.builder().firstName(FIRSTNAME).lastName(LASTNAME).build())
        .build();
    doThrow(new PersonNotFoundException()).when(personService).updatePerson(person);

    // Act
    mockMvc.perform(put("/person")
        .contentType(MediaType.APPLICATION_JSON)
        .content(asJsonString(personDTO)))
        // Assert
        .andExpect(status().isBadRequest());
  }
}
