package com.benoit.safetyAlert.services;

import com.benoit.safetyAlert.dao.MedicalRecordDao;
import com.benoit.safetyAlert.dto.PersonInfo;
import com.benoit.safetyAlert.exceptions.DataAlreadyExistException;
import com.benoit.safetyAlert.exceptions.DataNotFindException;
import com.benoit.safetyAlert.model.Medicalrecords;
import com.benoit.safetyAlert.model.Persons;
import com.benoit.safetyAlert.repository.DataRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation for MedicalRecordService.
 * Contains method to process CRUD from controller.
 */
@Service
public class MedicalRecordsServiceImpl implements MedicalRecordsService {

  private final DataRepository dataRepository;
  private final MedicalRecordDao medicalrecordDao;
  private static final Logger LOGGER = LogManager.getLogger(MedicalRecordsServiceImpl.class);

  /**
   * Instantiates a new Medical records service.
   *
   * @param dataRepository   the data repository
   * @param medicalrecordDao the medicalrecord dao
   */
  @Autowired
  public MedicalRecordsServiceImpl(
      DataRepository dataRepository, MedicalRecordDao medicalrecordDao) {
    this.dataRepository = dataRepository;
    this.medicalrecordDao = medicalrecordDao;
  }

  /**
   * Gets person info from repository.
   * This method takes firstName and lastName as parameters
   * and find the corresponding person in repository.
   * return the name, address, age, email, medication and allergies.
   *
   * @param firstName the first name
   * @param lastName  the last name
   * @return the person info
   */
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
    if (personInfo.getFirstName() == null || personInfo.getLastName() == null) {
      LOGGER.info("Can't find data for : " + firstName + " " + lastName + " in repository.");
      throw new DataNotFindException(
          "Can't find data for : " + firstName + " " + lastName + " in repository.");
    }
    return personInfo;
  }

  /**
   * Create medical record by calling Dao.
   * This method call for repository to check if the medical record and the person already exist.
   * if medical record Doesn't exist and person exist : call Dao.
   * Else, it will throw DataNotFindException or DataAlreadyExistException
   *
   * @param medicalrecord the medicalrecord
   * @return true if success
   */
  @Override
  public boolean createMedicalRecord(Medicalrecords medicalrecord) {

    List<Medicalrecords> medicalrecordsList = dataRepository.getMedicalrecords();
    List<Persons> personsList = dataRepository.getPersons();
    if (!medicalrecordsList.contains(medicalrecord)) {
      for (Persons person : personsList) {
        // on verifie que le medicalrecord n'existe pas dans le repo et que la personne existe

        if (person.getLastName().equalsIgnoreCase(medicalrecord.getLastName())
            && person.getFirstName().equalsIgnoreCase(medicalrecord.getFirstName())) {
          return medicalrecordDao.createMedicalRecords(medicalrecord);
        }
      }
      //cas ou il y a déja un medical record
      LOGGER.info(
          "error - can't find "
              + medicalrecord.getFirstName()
              + " "
              + medicalrecord.getLastName()
              + " in DB.");
      throw new DataNotFindException(
          "Can't create medical record for "
              + medicalrecord.getFirstName()
              + " "
              + medicalrecord.getLastName()
              + ", this person doesn't exist in Person DB.");
    }
    //cas ou il y a déja un medical record
    LOGGER.info("error - Medical Record already exist");
    throw new DataAlreadyExistException(
        "Medical record for "
            + medicalrecord.getFirstName()
            + " "
            + medicalrecord.getLastName()
            + " already exist.");
  }

  /**
   * Delete medical record by calling Dao.
   * This method call for repository to check if the medical record already exist.
   * if  medical record exist : call Dao.
   * Else, it will throw DataNotFindException.
   *
   * @param medicalRecord the medicalrecord
   * @return true if success
   */
  @Override
  public boolean deleteMedicalRecord(Medicalrecords medicalRecord) {


    if (dataRepository.getMedicalrecords().contains(medicalRecord)) {
      medicalrecordDao.deleteMedicalRecords(medicalRecord);
      return true;
    }

    LOGGER.info("error - can't find Medical Record.");
    throw new DataNotFindException(
        "medical record for "
            + medicalRecord.getFirstName()
            + " "
            + medicalRecord.getLastName()
            + " does not exist.");


  }

  /**
   * Update medical record by calling Dao.
   * This method call for repository to check if the medical record already exist.
   * if medical record exist : call Dao.
   * Else, it will throw DataNotFindException
   *
   * @param medicalrecord the medicalrecord
   * @return true if success
   */
  @Override
  public boolean updateMedicalRecord(Medicalrecords medicalrecord) {

    for (Medicalrecords mrec : dataRepository.getMedicalrecords()) {
      if (mrec.getFirstName().equalsIgnoreCase(medicalrecord.getFirstName())
          && mrec.getLastName().equalsIgnoreCase(medicalrecord.getLastName())) {
        return medicalrecordDao.updateMedicalRecords(medicalrecord);
      }
    }
    LOGGER.info(
        "Medical Record for "
            + medicalrecord.getFirstName()
            + " "
            + medicalrecord.getLastName()
            + " doesn't exist.");
    throw new DataNotFindException(
        "Medical Record for "
            + medicalrecord.getFirstName()
            + " "
            + medicalrecord.getLastName()
            + " doesn't exist.");
  }
}
