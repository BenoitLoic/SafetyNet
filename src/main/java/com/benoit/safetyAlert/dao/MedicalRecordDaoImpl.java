package com.benoit.safetyAlert.dao;

import com.benoit.safetyAlert.model.Medicalrecords;
import com.benoit.safetyAlert.repository.DataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation of MedialRecordDao.
 * Contains method to create / update / delete Medical Record in DB.
 */
@Service
public class MedicalRecordDaoImpl implements MedicalRecordDao {

  private final DataRepository dataRepository;

  /**
   * Instantiates a new Medical record dao.
   *
   * @param dataRepository the repository
   */
  @Autowired
  public MedicalRecordDaoImpl(DataRepository dataRepository) {
    this.dataRepository = dataRepository;
  }

  @Override
  public boolean createMedicalRecords(Medicalrecords medicalrecords) {
    dataRepository.getMedicalrecords().add(medicalrecords);
    dataRepository.commit();

    return true;
  }

  @Override
  public boolean deleteMedicalRecords(Medicalrecords medicalrecord) {
    for (Medicalrecords medicalrecords : dataRepository.getMedicalrecords()) {
      if (medicalrecords.getFirstName().equalsIgnoreCase(medicalrecord.getFirstName())
          && medicalrecords.getLastName().equalsIgnoreCase(medicalrecord.getLastName())) {
        dataRepository.getMedicalrecords().remove(medicalrecords);
        dataRepository.commit();
        return true;
      }
    }

    return false;
  }

  /**
   * This method will update the given medical record if present in DB.
   * if medical record exist in DB it will compare all value from the existing medical record
   * and change them if they are different.
   *
   * @param medicalrecordToUpdate the data to update in the existing medical record
   */
  @Override
  public boolean updateMedicalRecords(Medicalrecords medicalrecordToUpdate) {

    for (Medicalrecords medicalrecord : dataRepository.getMedicalrecords()) {
      if (medicalrecord.getFirstName().equalsIgnoreCase(medicalrecordToUpdate.getFirstName())
          && medicalrecord.getLastName().equalsIgnoreCase(medicalrecordToUpdate.getLastName())) {
        if (medicalrecordToUpdate.getBirthdate() != null) {
          medicalrecord.setBirthdate(medicalrecordToUpdate.getBirthdate());
        }
        if (!medicalrecord.getMedications().containsAll(medicalrecordToUpdate.getMedications())) {
          medicalrecord.setMedications(medicalrecordToUpdate.getMedications());
        }
        if (!medicalrecord.getAllergies().containsAll(medicalrecordToUpdate.getAllergies())) {
          medicalrecord.setAllergies(medicalrecordToUpdate.getAllergies());
        }
        dataRepository.getMedicalrecords().remove(medicalrecordToUpdate);
        dataRepository.getMedicalrecords().add(medicalrecord);
        dataRepository.commit();
        return true;
      }
    }
    return false;
  }
}
