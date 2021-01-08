package com.benoit.safetyAlert.dao;

import com.benoit.safetyAlert.model.Medicalrecords;

public interface MedicalRecordDao {

  boolean createMedicalRecords(Medicalrecords medicalrecords);

  boolean deleteMedicalRecords(Medicalrecords medicalrecords);

  boolean updateMedicalRecords(Medicalrecords medicalrecords);
}
