package com.benoit.safetyAlert.dao;

import com.benoit.safetyAlert.model.Medicalrecords;

public interface MedicalRecordsDao {

  boolean createMedicalRecords(Medicalrecords medicalrecords);

  boolean deleteMedicalRecords(Medicalrecords medicalrecords);

  boolean updateMedicalRecords(Medicalrecords medicalrecords);
}
