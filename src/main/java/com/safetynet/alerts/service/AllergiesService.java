package com.safetynet.alerts.service;

import com.safetynet.alerts.model.Allergies;
import com.safetynet.alerts.repository.AllergiesRepository;
import java.util.List;
import java.util.Optional;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Data
@Service
public class AllergiesService {

  @Autowired
  private AllergiesRepository allergiesRepository;

  public Optional<Allergies> getAllergy(final Long id) {
    return allergiesRepository.findById(id);
  }

  public List<Allergies> getAllergiesForOnePerson(final Long person_id) {
    return allergiesRepository.findByPersonId(person_id);
  }

  public Iterable<Allergies> getAllergies() {
    return allergiesRepository.findAll();
  }

  public void deleteAllergy(final Long id) {
    allergiesRepository.deleteById(id);
  }

  public Allergies saveAllergy(Allergies allergy) {
    return allergiesRepository.save(allergy);
  }

}
