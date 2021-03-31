package com.safetynet.alerts.repository;

import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.model.primarykey.MyCompositePK;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends CrudRepository<Person, Long> {

  Optional<Person> findPersonById(MyCompositePK id);

  List<Person> findAllByAddressAndBirthDateAfter(String address, LocalDate birthDate);

  List<Person> findAllByAddress(String address);

  List<Person> findAllByCity(String city);
}
