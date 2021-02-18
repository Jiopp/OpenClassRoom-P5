package com.safetynet.alerts.repository;

import com.safetynet.alerts.model.Allergies;
import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AllergiesRepository extends CrudRepository<Allergies, Long> {
  List<Allergies> findByPersonId(Long person_id);
}
