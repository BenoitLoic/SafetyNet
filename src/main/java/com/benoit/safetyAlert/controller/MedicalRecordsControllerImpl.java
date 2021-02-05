package com.benoit.safetyAlert.controller;

import com.benoit.safetyAlert.dto.PersonInfo;
import com.benoit.safetyAlert.model.Medicalrecords;
import com.benoit.safetyAlert.services.MedicalRecordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@Validated
@RestController
public class MedicalRecordsControllerImpl implements MedicalRecordsController {

  private final MedicalRecordsService medicalRecordsService;

  @Autowired
  public MedicalRecordsControllerImpl(MedicalRecordsService medicalRecordsService) {
    this.medicalRecordsService = medicalRecordsService;
  }

  @Override
  @GetMapping("/personInfo")
  @ResponseStatus(HttpStatus.OK)
  public PersonInfo personInfo(@NotBlank @RequestParam String firstName,
                               @NotBlank @RequestParam String lastName) {

    return medicalRecordsService.getPersonInfo(firstName, lastName);
  }

  @Override
  @PostMapping("/medicalRecord")
  @ResponseStatus(HttpStatus.CREATED)
  public void createMedicalRecord(@Valid @RequestBody Medicalrecords medicalrecord) {

    medicalRecordsService.createMedicalRecord(medicalrecord);
  }

  @Override
  @DeleteMapping("/medicalRecord")
  public void deleteMedicalRecord(@Valid @RequestBody Medicalrecords medicalRecord) {

    medicalRecordsService.deleteMedicalRecord(medicalRecord);
  }

  @Override
  @PutMapping("/medicalRecord")
  @ResponseStatus(HttpStatus.CREATED)
  public void updateMedicalRecord(@Valid @RequestBody Medicalrecords medicalrecord) {
    medicalRecordsService.updateMedicalRecord(medicalrecord);
  }
}
