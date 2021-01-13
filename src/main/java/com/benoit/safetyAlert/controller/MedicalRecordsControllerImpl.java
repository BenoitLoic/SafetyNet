package com.benoit.safetyAlert.controller;

import com.benoit.safetyAlert.model.Medicalrecords;
import com.benoit.safetyAlert.services.MedicalRecordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@RestController
public class MedicalRecordsControllerImpl implements MedicalRecordsController {

  @Autowired private MedicalRecordsService medicalRecordsService;

  @Override
  @GetMapping("/personInfo")
  @ResponseStatus(HttpStatus.OK)
  public Collection<Object> personInfo(String firstName, String lastName) {
    return medicalRecordsService.getPersonInfo(firstName, lastName);
  }

  @Override
  @PostMapping("/medicalRecord")
  @ResponseStatus(HttpStatus.CREATED)
  public void createMedicalRecord(@RequestBody @Valid Medicalrecords medicalrecord) {

    medicalRecordsService.createMedicalRecord(medicalrecord);
  }

  @Override
  @DeleteMapping("/medicalRecord")
  public void deleteMedicalRecord(@RequestBody @Valid Medicalrecords medicalRecord) {

    medicalRecordsService.deleteMedicalRecord(medicalRecord);
  }

  @Override
  @PutMapping("/medicalRecord")
  @ResponseStatus(HttpStatus.CREATED)
  public void updateMedicalRecord(@RequestBody @Valid Medicalrecords medicalrecord) {
    medicalRecordsService.updateMedicalRecord(medicalrecord);
  }
}
