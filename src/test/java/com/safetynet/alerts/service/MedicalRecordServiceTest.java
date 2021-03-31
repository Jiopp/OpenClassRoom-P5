package com.safetynet.alerts.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.safetynet.alerts.model.Allergies;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Medication;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.model.primarykey.MyCompositePK;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class MedicalRecordServiceTest {

  private final static String FIRSTNAME = "John";
  private final static String LASTNAME = "BOYD";
  private final static LocalDate BIRTHDATE = LocalDate.of(1988, 3, 6);

  @Mock
  private AllergiesService allergiesService;
  @Mock
  private MedicationService medicationService;
  @Mock
  private PersonService personService;
  @InjectMocks
  private MedicalRecordService medicalRecordService;

  @BeforeEach
  void init_mocks() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  @DisplayName("When addMedicalRecordToAPerson for an existing person then return the person updated")
  void addMedicalRecordToAPerson_for_an_existing_person() {
    // ARRANGE
    MedicalRecord medicalRecord = MedicalRecord.builder().firstName(FIRSTNAME).lastName(LASTNAME)
        .birthdate(BIRTHDATE)
        .medications(List.of("aznol:60mg", "hydrapermazol:900mg", "pharmacol:5000mg"))
        .allergies(List.of("peanut", "shellfish", "aznol")).build();
    when(personService.getPersonByName(FIRSTNAME, LASTNAME))
        .thenReturn(
            Person.builder().id(MyCompositePK.builder().firstName(FIRSTNAME).lastName(LASTNAME).build()).build());
    // ACT
    Person person = medicalRecordService.addMedicalRecordToAPerson(medicalRecord);
    // ASSERT
    assertThat(person.getBirthDate()).isEqualTo(BIRTHDATE);
    assertThat(person.getAllergies().size()).isNotZero();
    assertThat(person.getMedications().size()).isNotZero();
  }

  @Test
  @DisplayName("When addMedicalRecordToAPerson for an unknown person then return the person created")
  void addMedicalRecordToAPerson_for_an_unknown_person() {
    // ARRANGE
    MedicalRecord medicalRecord = MedicalRecord.builder().firstName(FIRSTNAME).lastName(LASTNAME)
        .birthdate(BIRTHDATE)
        .medications(List.of("aznol:60mg", "hydrapermazol:900mg", "pharmacol:5000mg"))
        .allergies(List.of("peanut", "shellfish", "aznol")).build();
    when(personService.getPersonByName(FIRSTNAME, LASTNAME))
        .thenReturn(null);
    // ACT
    Person person = medicalRecordService.addMedicalRecordToAPerson(medicalRecord);
    // ASSERT
    assertThat(person.getId().getFirstName()).isEqualTo(FIRSTNAME);
    assertThat(person.getId().getLastName()).isEqualTo(LASTNAME);
    assertThat(person.getBirthDate()).isEqualTo(BIRTHDATE);
    assertThat(person.getAllergies().size()).isNotZero();
    assertThat(person.getMedications().size()).isNotZero();
  }

  @Test
  @DisplayName("When updateMedicalRecord for an existing person then return the person updated")
  void updateMedicalRecord_for_an_existing_person() {
    // ARRANGE
    MedicalRecord medicalRecord = MedicalRecord.builder().firstName(FIRSTNAME).lastName(LASTNAME)
        .birthdate(BIRTHDATE)
        .medications(List.of("aznol:60mg", "hydrapermazol:900mg", "pharmacol:5000mg"))
        .allergies(List.of("peanut", "shellfish", "aznol")).build();
    Person personExpected = Person.builder()
        .id(MyCompositePK.builder().firstName(FIRSTNAME).lastName(LASTNAME).build())
        .medications(List.of(Medication.builder().person(Person.builder()
            .id(MyCompositePK.builder().firstName(FIRSTNAME).lastName(LASTNAME).build()).build())
            .medicationAndDosage("nillacilan:5000mg").build()))
        .allergies(List.of(Allergies.builder().person(Person.builder()
            .id(MyCompositePK.builder().firstName(FIRSTNAME).lastName(LASTNAME).build()).build())
            .allergy("butternut").build())).build();
    when(personService.savePerson(any())).thenReturn(personExpected);
    // ACT
    Person person = medicalRecordService.updateMedicalRecord(medicalRecord);
    // ASSERT
    assertThat(
        (int) person.getAllergies().stream().filter(allergies -> allergies.getAllergy().equals("butternut"))
            .count()).isEqualTo(1);
    assertThat((int) person.getMedications().stream()
        .filter(medication -> medication.getMedicationAndDosage().equals("nillacilan:5000mg")).count()).isEqualTo(1);
  }

  @Test
  @DisplayName("When saveMedicalRecord for an existing person then return the person updated")
  void saveMedicalRecord_for_an_existing_person() {
    // ARRANGE
    MedicalRecord medicalRecord = MedicalRecord.builder().firstName(FIRSTNAME).lastName(LASTNAME)
        .birthdate(BIRTHDATE)
        .medications(List.of("aznol:60mg", "hydrapermazol:900mg", "pharmacol:5000mg"))
        .allergies(List.of("peanut", "shellfish", "aznol")).build();
    Person personExpected = Person.builder()
        .id(MyCompositePK.builder().firstName(FIRSTNAME).lastName(LASTNAME).build())
        .medications(List.of(Medication.builder().person(Person.builder()
            .id(MyCompositePK.builder().firstName(FIRSTNAME).lastName(LASTNAME).build()).build())
            .medicationAndDosage("nillacilan:5000mg").build()))
        .allergies(List.of(Allergies.builder().person(Person.builder()
            .id(MyCompositePK.builder().firstName(FIRSTNAME).lastName(LASTNAME).build()).build())
            .allergy("butternut").build())).build();
    when(personService.savePerson(any())).thenReturn(personExpected);
    // ACT
    Person person = medicalRecordService.saveMedicalRecord(medicalRecord);
    // ASSERT
    assertThat(
        (int) person.getAllergies().stream().filter(allergies -> allergies.getAllergy().equals("butternut"))
            .count()).isEqualTo(1);
    assertThat((int) person.getMedications().stream()
        .filter(medication -> medication.getMedicationAndDosage().equals("nillacilan:5000mg")).count()).isEqualTo(1);
  }

  @Test
  @DisplayName("When saveMedicalRecords for an existing person then return the person updated")
  void saveMedicalRecords_for_an_existing_person() {
    // ARRANGE
    List<MedicalRecord> medicalRecord = List.of(MedicalRecord.builder().firstName(FIRSTNAME).lastName(LASTNAME)
        .birthdate(BIRTHDATE)
        .medications(List.of("aznol:60mg", "hydrapermazol:900mg", "pharmacol:5000mg"))
        .allergies(List.of("peanut", "shellfish", "aznol")).build());
    when(personService.getPersonByName(FIRSTNAME, LASTNAME))
        .thenReturn(Person.builder()
            .id(MyCompositePK.builder().firstName(FIRSTNAME).lastName(LASTNAME).build())
            .medications(List.of(Medication.builder().person(Person.builder()
                .id(MyCompositePK.builder().firstName(FIRSTNAME).lastName(LASTNAME).build()).build())
                .medicationAndDosage("nillacilan:5000mg").build()))
            .allergies(List.of(Allergies.builder().person(Person.builder()
                .id(MyCompositePK.builder().firstName(FIRSTNAME).lastName(LASTNAME).build()).build())
                .allergy("butternut").build())).build());
    // ACT
    medicalRecordService.saveMedicalRecords(medicalRecord);
    // ASSERT
    verify(personService, times(1)).savePersons(any());
  }

  @Test
  @DisplayName("When deleteMedicalRecord for an existing person then return the person with medial record deleted")
  void deleteMedicalRecord_for_an_existing_person() {
    // ARRANGE
    Person personWithMedicalRecord = Person.builder()
        .id(MyCompositePK.builder().firstName(FIRSTNAME).lastName(LASTNAME).build())
        .build();
    List<Medication> medications = new ArrayList<>();
    medications
        .add(Medication.builder().medicationAndDosage("nillacilan:5000mg").person(personWithMedicalRecord).build());
    List<Allergies> allergies = new ArrayList<>();
    allergies.add(Allergies.builder().allergy("butternut").person(personWithMedicalRecord).build());
    personWithMedicalRecord.setMedications(medications);
    personWithMedicalRecord.setAllergies(allergies);
    when(personService.getPersonByName(FIRSTNAME, LASTNAME)).thenReturn(personWithMedicalRecord);
    medicalRecordService.deleteMedicalRecord(FIRSTNAME, LASTNAME);
    // ASSERT
    verify(allergiesService, times(1)).deleteAllergiesByPerson(any());
    verify(medicationService, times(1)).deleteMedicalRecordByPerson(any());
  }
}
