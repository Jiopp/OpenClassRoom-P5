package com.safetynet.alerts.service;

import com.safetynet.alerts.model.Allergies;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Medication;
import com.safetynet.alerts.model.Person;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Data
@Service
public class MedicalRecordService {

  private final AllergiesService allergiesService;
  private final MedicationService medicationService;
  private final PersonService personService;

  @Autowired
  public MedicalRecordService(
      AllergiesService allergiesService, MedicationService medicationService, PersonService personService) {
    this.allergiesService = allergiesService;
    this.medicationService = medicationService;
    this.personService = personService;
  }

  public Person saveMedicalRecord(MedicalRecord medicalRecord) {

    Person person = getPersonAndSetBirthdate(medicalRecord);

    person.setMedications(addMedicationsToAList(medicalRecord, person));
    person.setAllergies(addAllergiesToAList(medicalRecord, person));

    personService.savePerson(person);

    return person;
  }

  public void deleteMedicalRecord(String firstName, String lastName) {
    Long personID = personService.getPersonByName(firstName, lastName).orElseThrow().getId();

    allergiesService.deleteAllergiesByPersonId(personID);
    medicationService.deleteMedicalRecordByPersonId(personID);
  }

  public Person updateMedicalRecord(MedicalRecord medicalRecord) {

    Person person = getPersonAndSetBirthdate(medicalRecord);

    medicationService.deleteMedicalRecordByPersonId(person.getId());
    allergiesService.deleteAllergiesByPersonId(person.getId());

    person.setMedications(addMedicationsToAList(medicalRecord, person));
    person.setAllergies(addAllergiesToAList(medicalRecord, person));

    personService.savePerson(person);

    return person;
  }

  private List<Allergies> addAllergiesToAList(MedicalRecord medicalRecord, Person person) {
    List<Allergies> allergies = new ArrayList<>();
    for (String allergiesToSave : medicalRecord.getAllergies()) {
      Allergies allergy = new Allergies();
      allergy.setAllergy(allergiesToSave);
      allergy.setPerson(person);
      allergies.add(allergy);
    }
    return allergies;
  }

  private List<Medication> addMedicationsToAList(MedicalRecord medicalRecord, Person person) {
    List<Medication> medications = new ArrayList<>();
    for (String medicationToSave : medicalRecord.getMedications()) {
      Medication medication = new Medication();
      medication.setMedication(medicationToSave);
      medication.setPerson(person);
      medications.add(medication);
    }
    return medications;
  }

  private Person getPersonAndSetBirthdate(MedicalRecord medicalRecord) {
    Optional<Person> optionalPerson = personService
        .getPersonByName(medicalRecord.getFirstName(), medicalRecord.getLastName());
    Person person = new Person();

    if (optionalPerson.isPresent()) {
      person = optionalPerson.get();
    } else {
      if (medicalRecord.getFirstName() != null && medicalRecord.getLastName() != null) {
        person.setFirstName(medicalRecord.getFirstName());
        person.setLastName(medicalRecord.getLastName());
      }
    }
    if (medicalRecord.getBirthdate() != null) {
      person.setBirthDate(medicalRecord.getBirthdate());
    }

    return person;
  }
}
