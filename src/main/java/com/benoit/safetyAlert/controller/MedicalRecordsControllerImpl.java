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

/**
 * The Implementation for MedicalRecordsController.
 */
@Validated
@RestController
public class MedicalRecordsControllerImpl implements MedicalRecordsController {

  private final MedicalRecordsService medicalRecordsService;

  @Autowired
  public MedicalRecordsControllerImpl(MedicalRecordsService medicalRecordsService) {
    this.medicalRecordsService = medicalRecordsService;
  }

  /**
   * Method for GET /personInfo.
   * this method takes the ID (firstName and lastName) of a user
   * and return its information.
   * information are : name, address, age, email, medications and allergies.
   *
   * @param firstName the first name @NotBlank @RequestParam
   * @param lastName  the last name @NotBlank @RequestParam
   * @return the information for this user
   */
  @Override
  @GetMapping("/personInfo")
  @ResponseStatus(HttpStatus.OK)
  public PersonInfo personInfo(@RequestParam String firstName,
                               @RequestParam String lastName) {

    return medicalRecordsService.getPersonInfo(firstName, lastName);
  }

  /**
   * Method for POST /medicalRecord.
   * this method will add the given Medical Record to DB if the person already exist
   *
   * @param medicalrecord the medicalrecord to add. firstName, lastName and birthdate are mandatory.
   */
  @Override
  @PostMapping("/medicalRecord")
  @ResponseStatus(HttpStatus.CREATED)
  public void createMedicalRecord(@Valid @RequestBody Medicalrecords medicalrecord) {
    if (medicalrecord.getBirthdate() == null) {
      Logger logger = LogManager.getLogger(MedicalRecordsControllerImpl.class);
      logger.info("error - birthdate is mandatory.");
      throw new InvalidArgumentException("birthdate is mandatory.");
    }
    medicalRecordsService.createMedicalRecord(medicalrecord);
  }

  /**
   * Method for DELETE /medicalRecord.
   * this method will remove the given Medical Record from DB.
   *
   * @param medicalRecord the medical record to remove. firstName, lastName are mandatory.
   */
  @Override
  @DeleteMapping("/medicalRecord")
  public void deleteMedicalRecord(@Valid @RequestBody Medicalrecords medicalRecord) {

    medicalRecordsService.deleteMedicalRecord(medicalRecord);
  }

  /**
   * Method for PUT /medicalRecord.
   * this method will update the given Medical Record from DB.
   *
   * @param medicalrecord the medicalrecord to update. firstName, lastName are mandatory.
   */
  @Override
  @PutMapping("/medicalRecord")
  @ResponseStatus(HttpStatus.CREATED)
  public void updateMedicalRecord(@Valid @RequestBody Medicalrecords medicalrecord) {
    medicalRecordsService.updateMedicalRecord(medicalrecord);
  }


}
