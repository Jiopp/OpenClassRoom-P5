package com.safetynet.alerts.repository;

import com.safetynet.alerts.model.MedicalRecord;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicalRecordRepository extends CrudRepository<MedicalRecord, Long> {
  List<MedicalRecord> findByPersonId(Long person_id);
}
