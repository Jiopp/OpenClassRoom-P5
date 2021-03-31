package com.safetynet.alerts.model.dto;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.model.primarykey.MyCompositePK;
import com.safetynet.alerts.service.InitService;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ChildrenDTO.class)
class ChildrenDTOTest {

  private final static String LASTNAME = "Boyd";
  private final static String ADDRESS = "947 E. Rose Dr";
  @Autowired
  private MockMvc mockMvc;
  @MockBean
  private InitService initService;

  @Test
  void convertListPersonToChildrenDTO_should_return_a_childrenDTO() {
    // ASSERT
    List<Person> childrenToConvert;
    childrenToConvert = initChildrenToConvert();
    List<Person> familyToConvert;
    familyToConvert = initFamilyToConvert();

    ChildrenDTO validChildrenDTO;
    validChildrenDTO = initValidChildrenDTO();
    // ACT
    ChildrenDTO childrenDTO = new ChildrenDTO(childrenToConvert, familyToConvert);

    // ASSERT
    assertThat(childrenDTO).isEqualTo(validChildrenDTO);
  }

  private ChildrenDTO initValidChildrenDTO() {
    List<Person> children = List
        .of(Person.builder().id(MyCompositePK.builder().firstName("Kendrik").lastName(LASTNAME).build())
            .birthDate(LocalDate.of(2014, 6, 3)).build());
    List<Person> family = new ArrayList<>();
    addFamily(family);
    return new ChildrenDTO(children, family);
  }

  private List<Person> initFamilyToConvert() {
    List<Person> familyToConvert = initChildrenToConvert();
    addFamily(familyToConvert);
    return familyToConvert;
  }

  private void addFamily(List<Person> family) {
    family.add(Person.builder().id(MyCompositePK.builder().firstName("Brian").lastName(LASTNAME).build())
        .address(ADDRESS).build());
    family.add(Person.builder().id(MyCompositePK.builder().firstName("Shawna").lastName(LASTNAME).build())
        .address(ADDRESS).build());
  }

  private List<Person> initChildrenToConvert() {
    List<Person> childrenToConvert = new ArrayList<>();
    childrenToConvert.add(Person.builder().id(MyCompositePK.builder().firstName("Kendrik").lastName(LASTNAME).build())
        .birthDate(LocalDate.of(2014, 6, 3)).build());
    return childrenToConvert;
  }
}

