package com.benoit.safetyAlert.services;

import com.benoit.safetyAlert.dao.MedicalRecordDao;
import com.benoit.safetyAlert.dto.PersonInfo;
import com.benoit.safetyAlert.exceptions.DataAlreadyExistException;
import com.benoit.safetyAlert.exceptions.DataNotFindException;
import com.benoit.safetyAlert.model.Medicalrecords;
import com.benoit.safetyAlert.model.Persons;
import com.benoit.safetyAlert.repository.DataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MedicalRecordsServiceImpl implements MedicalRecordsService {

  @Autowired private DataRepository dataRepository;
  @Autowired private MedicalRecordDao medicalrecordDao;

  @Override
  public PersonInfo getPersonInfo(String firstName, String lastName) {
    PersonInfo personInfo = new PersonInfo();
    for (Persons person : dataRepository.linkDataBaseReturnPersons()) {
      if (person.getFirstName().equalsIgnoreCase(firstName)
          && person.getLastName().equalsIgnoreCase(lastName)) {
        personInfo.setFirstName(person.getFirstName());
        personInfo.setLastName(person.getLastName());
        personInfo.setAddress(person.getAddress());
        personInfo.setEmail(person.getEmail());
        personInfo.setMedication(person.getMedicalrecords().getMedications());
        personInfo.setAllergies(person.getMedicalrecords().getAllergies());
      }
    }

    return personInfo;
  }

  @Override
  public boolean createMedicalRecord(Medicalrecords medicalrecord) {
    Persons person =
        dataRepository.getPersonByName(medicalrecord.getFirstName(), medicalrecord.getLastName());
    // on verifie que le medicalrecord n'existe pas dans le repo et que la personne existe
    if (!dataRepository.getDatabaseJson().getMedicalrecords().contains(medicalrecord)
        && (person.getLastName() != null)) {
      medicalrecordDao.createMedicalRecords(medicalrecord);
      return true;
    } else {
      if (dataRepository.getDatabaseJson().getMedicalrecords().contains(medicalrecord)) {
        throw new DataAlreadyExistException(
            "Medical record for "
                + medicalrecord.getFirstName()
                + " "
                + medicalrecord.getLastName()
                + " already exist.");
      } else {
        throw new DataNotFindException(
            "Can't create medical record for "
                + medicalrecord.getFirstName()
                + " "
                + medicalrecord.getLastName()
                + ", this person doesn't exist in Person DB.");
      }
    }
  }

  @Override
  public boolean deleteMedicalRecord(Medicalrecords medicalRecord) {
    if (dataRepository.getDatabaseJson().getMedicalrecords().contains(medicalRecord)) {
      medicalrecordDao.deleteMedicalRecords(medicalRecord);
      return true;
    } else {
      throw new DataNotFindException(
          "medical record for "
              + medicalRecord.getFirstName()
              + " "
              + medicalRecord.getLastName()
              + " does not exist.");
    }
  }

  @Override
  public boolean updateMedicalRecord(Medicalrecords medicalrecord) {
    deleteMedicalRecord(medicalrecord);
    return createMedicalRecord(medicalrecord);
  }
}
