package com.safetynet.alerts.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.model.primarykey.MyCompositePK;
import com.safetynet.alerts.service.InitService;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;

@DataJpaTest
class PersonRepositoryTest {

  private static final String FIRSTNAME = "John";
  private static final String LASTNAME = "Boyd";
  private static final String FIRSTNAME2 = "Zoe";
  private static final String LASTNAME2 = "Boyd";
  private static final String ADDRESS = "Home";
  private static final String WRONG_ADDRESS = "Work";
  private static final LocalDate BIRTHDATE_OVER_18 = LocalDate.of(1988, 3, 6);
  private static final LocalDate BIRTHDATE_UNDER_18 = LocalDate.of(2005, 3, 6);

  @Autowired
  private TestEntityManager entityManager;
  @Autowired
  private PersonRepository personRepository;

  @MockBean
  private InitService initService;

  @Test
  @DisplayName("When find by person ID, then return person")
  void findPersonById() {
    // ARRANGE
    Person person = Person.builder().id(MyCompositePK.builder().firstName(FIRSTNAME).lastName(LASTNAME).build())
        .build();
    entityManager.persist(person);
    entityManager.flush();
    // ACT
    Optional<Person> personFound = personRepository
        .findPersonById(MyCompositePK.builder().firstName(FIRSTNAME).lastName(LASTNAME).build());
    // ASSERT
    assertThat(personFound).contains(person);
  }

  @Test
  @DisplayName("When find persons by address and birthdate over 18, then return persons ")
  void findAllByAddressAndBirthDateAfter_For_person_over_18_year_old() {
    // ARRANGE
    Person person = Person.builder().id(MyCompositePK.builder().firstName(FIRSTNAME).lastName(LASTNAME).build())
        .address(ADDRESS).birthDate(BIRTHDATE_OVER_18).build();
    entityManager.persist(person);
    entityManager.flush();
    // ACT
    List<Person> personFound = personRepository.findAllByAddressAndBirthDateAfter(ADDRESS, LocalDate.of(2003, 1, 1));
    // ASSERT
    assertThat(personFound).isEmpty();
  }

  @Test
  @DisplayName("When find persons by address and birthdate under 18, then return persons ")
  void findAllByAddressAndBirthDateAfter_For_person_under_18_year_old() {
    // ARRANGE
    Person person = Person.builder().id(MyCompositePK.builder().firstName(FIRSTNAME).lastName(LASTNAME).build())
        .address(ADDRESS).birthDate(BIRTHDATE_UNDER_18).build();
    entityManager.persist(person);
    entityManager.flush();
    // ACT
    List<Person> personFound = personRepository.findAllByAddressAndBirthDateAfter(ADDRESS, LocalDate.of(2003, 1, 1));
    // ASSERT
    assertThat(personFound.get(0)).isEqualTo(person);
  }

  @Test
  @DisplayName("When find person by address, then return person ")
  void findAllByAddress() {
    // ARRANGE
    Person personWithRightAddress = Person.builder()
        .id(MyCompositePK.builder().firstName(FIRSTNAME).lastName(LASTNAME).build())
        .address(ADDRESS).build();
    Person personWithWrongAddress = Person.builder()
        .id(MyCompositePK.builder().firstName(FIRSTNAME2).lastName(LASTNAME2).build())
        .address(WRONG_ADDRESS).build();
    entityManager.persist(personWithRightAddress);
    entityManager.persist(personWithWrongAddress);
    entityManager.flush();
    // ACT
    List<Person> personFound = personRepository.findAllByAddress(ADDRESS);
    // ASSERT
    assertThat(personFound.get(0)).isEqualTo(personWithRightAddress);
    assertThat(personFound.size()).isEqualTo(1);
  }

  @Test
  @DisplayName("When find by city , then return persons")
  void findAllByCity() {
    // ARRANGE
    Person personWithRightAddress = Person.builder()
        .id(MyCompositePK.builder().firstName(FIRSTNAME).lastName(LASTNAME).build())
        .city(ADDRESS).build();
    Person personWithWrongAddress = Person.builder()
        .id(MyCompositePK.builder().firstName(FIRSTNAME2).lastName(LASTNAME2).build())
        .city(WRONG_ADDRESS).build();
    entityManager.persist(personWithRightAddress);
    entityManager.persist(personWithWrongAddress);
    entityManager.flush();
    // ACT
    List<Person> personFound = personRepository.findAllByCity(ADDRESS);
    // ASSERT
    assertThat(personFound.get(0)).isEqualTo(personWithRightAddress);
    assertThat(personFound.size()).isEqualTo(1);
  }
}
