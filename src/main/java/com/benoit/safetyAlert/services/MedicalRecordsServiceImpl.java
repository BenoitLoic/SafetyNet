package com.benoit.safetyAlert.services;

import com.benoit.safetyAlert.dao.MedicalRecordDao;
import com.benoit.safetyAlert.dto.PersonInfo;
import com.benoit.safetyAlert.exceptions.DataAlreadyExistException;
import com.benoit.safetyAlert.exceptions.DataNotFindException;
import com.benoit.safetyAlert.exceptions.InvalidArgumentException;
import com.benoit.safetyAlert.model.Medicalrecords;
import com.benoit.safetyAlert.model.Persons;
import com.benoit.safetyAlert.repository.DataRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicalRecordsServiceImpl implements MedicalRecordsService {

  private final DataRepository dataRepository;
  private final MedicalRecordDao medicalrecordDao;
  private static final Logger LOGGER = LogManager.getLogger(MedicalRecordsServiceImpl.class);

  @Autowired
  public MedicalRecordsServiceImpl(
      DataRepository dataRepository, MedicalRecordDao medicalrecordDao) {
    this.dataRepository = dataRepository;
    this.medicalrecordDao = medicalrecordDao;
  }

  @Override
  public PersonInfo getPersonInfo(String firstName, String lastName) {
    PersonInfo personInfo = new PersonInfo();

    for (Persons person : dataRepository.getPersons()) {
      if (person.getFirstName().equalsIgnoreCase(firstName)
          && person.getLastName().equalsIgnoreCase(lastName)) {
        personInfo.setFirstName(person.getFirstName());
        personInfo.setLastName(person.getLastName());
        personInfo.setAddress(person.getAddress());
        personInfo.setEmail(person.getEmail());

        personInfo.setMedication(person.getMedicalrecords().getMedications());
        personInfo.setAllergies(person.getMedicalrecords().getAllergies());

        break;
      }
    }
    if (personInfo.getFirstName() == null && personInfo.getLastName() == null) {
      throw new DataNotFindException(
          "Can't find data for : " + firstName + " " + lastName + " in repository.");
    }
    return personInfo;
  }

  @Override
  public boolean createMedicalRecord(Medicalrecords medicalrecord) {


    for (Persons person : dataRepository.getPersons()) {
      // on verifie que le medicalrecord n'existe pas dans le repo et que la personne existe

      if (person.getLastName().equalsIgnoreCase(medicalrecord.getLastName())
          && person.getFirstName().equalsIgnoreCase(medicalrecord.getFirstName())) {

        if (person.getMedicalrecords().getLastName() == null) {
          return medicalrecordDao.createMedicalRecords(medicalrecord);
        }

        //cas ou il y a déja un medical record
        if (person.getMedicalrecords().getLastName() != null) {
          LOGGER.info("error - Medical Record already exist");
          throw new DataAlreadyExistException(
              "Medical record for "
                  + medicalrecord.getFirstName()
                  + " "
                  + medicalrecord.getLastName()
                  + " already exist.");
        }

      }
    }
    //cas ou il y a déja un medical record
    LOGGER.info("error - can't find " + medicalrecord.getFirstName() + " " + medicalrecord.getLastName() + " in DB.");
    throw new DataNotFindException(
        "Can't create medical record for "
            + medicalrecord.getFirstName()
            + " "
            + medicalrecord.getLastName()
            + ", this person doesn't exist in Person DB.");
  }


  @Override
  public boolean deleteMedicalRecord(Medicalrecords medicalRecord) {


    if (dataRepository.getMedicalrecords().contains(medicalRecord)

        && !medicalRecord.equals(new Medicalrecords())) {
      medicalrecordDao.deleteMedicalRecords(medicalRecord);
      return true;
    }

    LOGGER.info("error - can't find Medical Record.");
    throw new DataNotFindException(
        "medical record for "
            + medicalRecord.getFirstName() + " "
            + medicalRecord.getLastName()
            + " does not exist.");


  }

  @Override
  public boolean updateMedicalRecord(Medicalrecords medicalrecord) {
    if (medicalrecord == null
        || medicalrecord.getFirstName() == null
        || medicalrecord.getLastName() == null) {
      LOGGER.info("error - Medical Record or its value are null.");
      throw new InvalidArgumentException("Medical Record or its value can't be null");
    }
    for (Medicalrecords mRec : dataRepository.getMedicalrecords()) {
      if (mRec.getFirstName().equalsIgnoreCase(medicalrecord.getFirstName())
          && mRec.getLastName().equalsIgnoreCase(medicalrecord.getLastName())) {
        return medicalrecordDao.updateMedicalRecords(medicalrecord);
      }
    }
    LOGGER.info("Medical Record for " + medicalrecord.getFirstName() + " " + medicalrecord.getLastName() + " doesn't exist.");
    throw new DataNotFindException("Medical Record for " + medicalrecord.getFirstName() + " " + medicalrecord.getLastName() + " doesn't exist.");
  }
}
