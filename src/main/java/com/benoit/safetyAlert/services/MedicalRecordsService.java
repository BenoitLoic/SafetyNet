package com.benoit.safetyAlert.services;

import com.benoit.safetyAlert.dto.PersonInfo;
import com.benoit.safetyAlert.model.Medicalrecords;
import com.benoit.safetyAlert.model.Persons;

import java.util.Collection;

public interface MedicalRecordsService {

  PersonInfo getPersonInfo(String firstName, String lastName);


  boolean createMedicalRecord(Medicalrecords medicalrecord);

  boolean deleteMedicalRecord(Medicalrecords medicalRecord);

  boolean updateMedicalRecord(Medicalrecords medicalrecord);
}
