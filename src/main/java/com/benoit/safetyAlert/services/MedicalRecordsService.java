package com.benoit.safetyAlert.services;

import com.benoit.safetyAlert.dto.PersonInfo;
import com.benoit.safetyAlert.model.Medicalrecords;

import java.util.Collection;

public interface MedicalRecordsService {

  Collection<Object> getPersonInfo(String firstName, String lastName);

  /**
   * Gets personInformation from both Person and MedicalRecords repository
   *
   * @param firstName first name in repository
   * @param lastName last name in repository
   * @return Dto PersonInfo
   */
  PersonInfo getFullPersonInfo(String firstName, String lastName);

  boolean createMedicalRecord(Medicalrecords medicalrecord);

  boolean deleteMedicalRecord(Medicalrecords medicalRecord);

  boolean updateMedicalRecord(Medicalrecords medicalrecord);
}
