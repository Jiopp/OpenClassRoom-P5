package com.safetynet.alerts.service;

import com.safetynet.alerts.model.Allergies;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Medication;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.model.primarykey.MyCompositePK;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Data
@Service
@Transactional
public class MedicalRecordService {

  @Autowired
  private AllergiesService allergiesService;
  @Autowired
  private MedicationService medicationService;
  @Autowired
  private PersonService personService;

  /**
   * This method will save a medical record for a person
   *
   * @param medicalRecord The medical record you want to save
   * @return The person with the new medical record
   */
  public Person saveMedicalRecord(MedicalRecord medicalRecord) {
    return personService.savePerson(addMedicalRecordToAPerson(medicalRecord));
  }

  /**
   * This method will save a list of medical record for multiple persons
   *
   * @param medicalRecords A list of medical record to save
   */
  public void saveMedicalRecords(List<MedicalRecord> medicalRecords) {
    personService
        .savePersons(medicalRecords.stream().map(this::addMedicalRecordToAPerson).collect(Collectors.toList()));
  }

  /**
   * This method will delete a medical record for a person
   *
   * @param firstName The firstname of the person
   * @param lastName The lastname of the person
   */
  public void deleteMedicalRecord(String firstName, String lastName) {
    Person person = personService.getPersonByName(firstName, lastName);
    allergiesService.deleteAllergiesByPerson(person);
    medicationService.deleteMedicalRecordByPerson(person);
  }

  /**
   * This method will update a medical record for a person. It will start by deleting the previous medical record and save the new one
   *
   * @param medicalRecord The medical record to update
   * @return The person with the medical record updated
   */
  public Person updateMedicalRecord(MedicalRecord medicalRecord) {
    Person person = getPersonAndSetBirthdate(medicalRecord);
    medicationService.deleteMedicalRecordByPerson(person);
    allergiesService.deleteAllergiesByPerson(person);
    person.setMedications(addMedicationsToAList(medicalRecord, person));
    person.setAllergies(addAllergiesToAList(medicalRecord, person));
    return personService.savePerson(person);
  }

  /**
   * This method will add a medical record to a person by setting the medications, the allergies and the birthdate to a person (existing or not)
   *
   * @param medicalRecord The medical record to add
   * @return The person with the medical record
   */
  public Person addMedicalRecordToAPerson(MedicalRecord medicalRecord) {
    Person person = getPersonAndSetBirthdate(medicalRecord);
    person.setMedications(addMedicationsToAList(medicalRecord, person));
    person.setAllergies(addAllergiesToAList(medicalRecord, person));
    return person;
  }

  /**
   * This method will first search for a person, if it doesn't exist, it will create it. Once it's found or created, it will add the birthdate to the person
   *
   * @param medicalRecord The medical record to add
   * @return The person with the birthdate
   */
  public Person getPersonAndSetBirthdate(MedicalRecord medicalRecord) {
    Person person = personService
        .getPersonByName(medicalRecord.getFirstName(), medicalRecord.getLastName());

    if (person == null) {
      person = Person.builder().build();
      if (medicalRecord.getFirstName() != null && medicalRecord.getLastName() != null) {
        person.setId(
            MyCompositePK.builder().firstName(medicalRecord.getFirstName()).lastName(medicalRecord.getLastName())
                .build());
      }
    }
    person.setBirthDate(medicalRecord.getBirthdate());
    return person;
  }

  /**
   * This method will create a list of allergies from the medical record
   *
   * @param medicalRecord The medical record to add
   * @param person The person for which we add a list of allergies
   * @return The list of allergies for a person
   */
  public List<Allergies> addAllergiesToAList(MedicalRecord medicalRecord, Person person) {
    return medicalRecord.getAllergies().stream()
        .map(allergy -> setAllergyAndPersonToAllergies(allergy, person))
        .collect(Collectors.toList());
  }

  /**
   * This method will an allergy for a person
   *
   * @param allergy The allergy to add
   * @param person The person for which we add an allergy
   * @return An allergy for a person
   */
  private Allergies setAllergyAndPersonToAllergies(String allergy, Person person) {
    return Allergies.builder().allergy(allergy).person(person).build();
  }

  /**
   * This method will create a list of medication from the medical record
   *
   * @param medicalRecord The medical record to add
   * @param person The person for which we add a list of medications
   * @return The list of medications for a person
   */
  public List<Medication> addMedicationsToAList(MedicalRecord medicalRecord, Person person) {
    return medicalRecord.getMedications().stream()
        .map(medication -> setMedicationAndPersonToMedications(medication, person)).collect(Collectors.toList());
  }

  /**
   * This method will a medication for a person
   *
   * @param medicationName The medication to add
   * @param person The person for which we add a medication
   * @return A medication for a person
   */
  private Medication setMedicationAndPersonToMedications(String medicationName, Person person) {
    return Medication.builder().medicationAndDosage(medicationName).person(person).build();
  }
}
