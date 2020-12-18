package com.benoit.safetyAlert.services;

import com.benoit.safetyAlert.dto.PersonInfo;

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
}
