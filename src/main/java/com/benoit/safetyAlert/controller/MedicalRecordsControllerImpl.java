package com.benoit.safetyAlert.controller;

import com.benoit.safetyAlert.dto.PersonInfo;
import com.benoit.safetyAlert.exceptions.InvalidArgumentException;
import com.benoit.safetyAlert.model.Medicalrecords;
import com.benoit.safetyAlert.services.MedicalRecordsService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
  public PersonInfo personInfo(@RequestParam String firstName,
                               @RequestParam String lastName) {

    return medicalRecordsService.getPersonInfo(firstName, lastName);
  }

  @Override
  @PostMapping("/medicalRecord")
  @ResponseStatus(HttpStatus.CREATED)
  public void createMedicalRecord(@Valid @RequestBody Medicalrecords medicalrecord) {
    if (medicalrecord.getBirthdate() == null) {
      Logger LOGGER = LogManager.getLogger(MedicalRecordsControllerImpl.class);
      LOGGER.info("error - birthdate is mandatory.");
      throw new InvalidArgumentException("birthdate is mandatory.");
    }
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
