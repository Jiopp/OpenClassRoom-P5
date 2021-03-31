package com.safetynet.alerts.model.dto;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.service.InitService;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@WebMvcTest(MedicalRecordDTO.class)
class MedicalRecordDTOTest {

  @MockBean
  private InitService initService;

  @Test
  void convertToMedicalRecord_should_return_a_MedicalRecord() {
    // ARRANGE
    MedicalRecord validMedicalRecord = MedicalRecord.builder().firstName("John").lastName("Boyd")
        .birthdate(LocalDate.of(1988, 3, 6))
        .medications(List.of("aznol:60mg", "hydrapermazol:900mg", "pharmacol:5000mg"))
        .allergies(List.of("hydrapermazol:900mg", "pharmacol:5000mg")).build();
    // ACT
    MedicalRecord medicalRecord = MedicalRecordDTO.builder().firstName("John").lastName("Boyd")
        .birthdate("03/06/1988").medications(List.of("aznol:60mg", "hydrapermazol:900mg", "pharmacol:5000mg"))
        .allergies(List.of("hydrapermazol:900mg", "pharmacol:5000mg")).build().convertToMedicalRecord();

    // ASSERT
    assertThat(medicalRecord).isEqualTo(validMedicalRecord);
  }
}
