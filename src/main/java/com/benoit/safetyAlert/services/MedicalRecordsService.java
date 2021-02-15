package com.benoit.safetyAlert.services;

import com.benoit.safetyAlert.dto.PersonInfo;
import com.benoit.safetyAlert.model.Medicalrecords;

/**
 * The interface Medical records service.
 */
public interface MedicalRecordsService {

  /**
   * Gets person info from repository.
   *
   * @param firstName the first name
   * @param lastName  the last name
   * @return the person info
   */
  PersonInfo getPersonInfo(String firstName, String lastName);


  /**
   * Create medical record boolean by calling Dao.
   *
   * @param medicalrecord the medicalrecord
   * @return true if success
   */
  boolean createMedicalRecord(Medicalrecords medicalrecord);

  /**
   * Delete medical record by calling Dao.
   *
   * @param medicalRecord the medical record
   * @return true if success
   */
  boolean deleteMedicalRecord(Medicalrecords medicalRecord);

  /**
   * Update medical record boolean by calling Dao.
   *
   * @param medicalrecord the medicalrecord
   * @return true if success
   */
  boolean updateMedicalRecord(Medicalrecords medicalrecord);
}
