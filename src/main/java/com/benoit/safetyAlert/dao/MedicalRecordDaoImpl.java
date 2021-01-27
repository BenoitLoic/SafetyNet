package com.benoit.safetyAlert.dao;

import com.benoit.safetyAlert.model.Medicalrecords;
import com.benoit.safetyAlert.repository.DataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MedicalRecordDaoImpl implements MedicalRecordDao {

  @Autowired DataRepository dataRepository;

  @Override
  public boolean createMedicalRecords(Medicalrecords medicalrecords) {
    dataRepository.getDatabaseJson().getMedicalrecords().add(medicalrecords);
    dataRepository.commit();

    return true;
  }

  @Override
  public boolean deleteMedicalRecords(Medicalrecords medicalrecord) {
    dataRepository.getDatabaseJson().getMedicalrecords().remove(medicalrecord);
    dataRepository.commit();
    return true;
  }

  @Override
  public boolean updateMedicalRecords(Medicalrecords medicalrecords) {
    deleteMedicalRecords(medicalrecords);
    return createMedicalRecords(medicalrecords);
  }
}
