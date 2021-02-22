package com.safetynet.safetyalerts.dao;

import com.safetynet.safetyalerts.model.Medicalrecords;

/**
 * Interface MedialRecordDao.
 * Contains method to create / update / delete Medical Record in DB.
 */
public interface MedicalRecordDao {

  boolean createMedicalRecords(Medicalrecords medicalrecords);

  boolean deleteMedicalRecords(Medicalrecords medicalrecords);

  boolean updateMedicalRecords(Medicalrecords medicalrecords);
}
