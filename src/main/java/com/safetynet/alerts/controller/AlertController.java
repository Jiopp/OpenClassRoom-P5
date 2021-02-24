package com.safetynet.alerts.controller;

import com.safetynet.alerts.model.dto.ChildrenDTO;
import com.safetynet.alerts.model.dto.PhoneNumberDTO;
import com.safetynet.alerts.service.AlertService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Endpoint for all alert
 */
@RestController
public class AlertController {

  @Autowired
  private AlertService alertService;

  /**
   * This method return a list of children (any individual 18 years of age or younger) living a the address with their entire family.
   * If they're no children for this address, an ChildrenNotFoundException will be throw
   *
   * @param address The address where to look for the children
   * @return A list of the children and their family
   */
  @GetMapping("childAlert")
  public ChildrenDTO getAllChildrenAndTheirFamiliesForAnAddress(@RequestParam String address) {
    return alertService.getAllChildrenAndTheirFamiliesForAnAddress(address);
  }

  /**
   * This method return a list of phone number for all person linked to this firestation
   *
   * @param firestation The number of the firestation
   * @return A list of residents' phone numbers
   */
  @GetMapping("phoneAlert")
  public List<PhoneNumberDTO> getAllPhoneNumberForAFirestation(@RequestParam int firestation) {
    return alertService.getAllPhoneNumberForAFirestation(firestation);
  }
}
