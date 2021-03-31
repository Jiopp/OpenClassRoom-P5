package com.safetynet.alerts.service;

import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.AllergiesRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Data
@Service
public class AllergiesService {

  @Autowired
  private AllergiesRepository allergiesRepository;

  /**
   * This method will delete every record of allergies for a person
   *
   * @param person The person for which we want to delete the allergies
   */
  public void deleteAllergiesByPerson(final Person person) {
    allergiesRepository.deleteByPerson(person);
  }
}
