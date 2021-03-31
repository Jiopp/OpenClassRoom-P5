package com.safetynet.alerts.model.dto;

import com.safetynet.alerts.model.Children;
import com.safetynet.alerts.model.Person;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;

/**
 * Represent a list of children with their family
 */
@Data
public class ChildrenDTO {

  private List<Children> children;
  private List<PersonForChildrenDTO> family;

  public ChildrenDTO() {

  }

  /**
   * Convert a list of person(children under 18 years old) and a list of persons for an address into a ChildrenDTO
   *
   * @param childrenToConvert The children for an address
   * @param familyToConvert The all family for an address
   */
  public ChildrenDTO(List<Person> childrenToConvert, List<Person> familyToConvert) {
    this.children = childrenToConvert.stream().map(Person::convertPersonToChildren)
        .collect(Collectors.toList());
    familyToConvert.removeAll(childrenToConvert);
    this.family = familyToConvert.stream().map(Person::convertPersonToPersonForChildrenDTO)
        .collect(Collectors.toList());
  }
}
