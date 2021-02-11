package com.benoit.safetyAlert.controller;

import com.benoit.safetyAlert.dto.PersonInfo;
import com.benoit.safetyAlert.model.Medicalrecords;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

/**
 * The interface for Medical records controller.
 */
public interface MedicalRecordsController {


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
  PersonInfo personInfo(@NotBlank @RequestParam String firstName,
                        @NotBlank @RequestParam String lastName);


  /**
   * Method for POST /medicalRecord.
   * this method will add the given Medical Record to DB if the person already exist
   *
   * @param medicalrecord the medicalrecord to add. firstName, lastName are mandatory.
   */
  void createMedicalRecord(@Valid @RequestBody Medicalrecords medicalrecord);


  /**
   * Method for DELETE /medicalRecord.
   * this method will remove the given Medical Record from DB.
   *
   * @param medicalRecord the medical record to remove. firstName, lastName are mandatory.
   */
  void deleteMedicalRecord(@Valid @RequestBody Medicalrecords medicalRecord);


  /**
   * Method for PUT /medicalRecord.
   * this method will update the given Medical Record from DB.
   *
   * @param medicalrecord the medicalrecord to update. firstName, lastName are mandatory.
   */
  void updateMedicalRecord(@Valid @RequestBody Medicalrecords medicalrecord);
}
