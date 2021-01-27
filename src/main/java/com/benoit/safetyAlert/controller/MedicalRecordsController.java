package com.benoit.safetyAlert.controller;

import com.benoit.safetyAlert.dto.PersonInfo;
import com.benoit.safetyAlert.model.Medicalrecords;

public interface MedicalRecordsController {

  PersonInfo personInfo(String firstName, String lastName);

  void createMedicalRecord(Medicalrecords medicalrecord);

  void deleteMedicalRecord(Medicalrecords medicalrecords);

  void updateMedicalRecord(Medicalrecords medicalrecord);
}
