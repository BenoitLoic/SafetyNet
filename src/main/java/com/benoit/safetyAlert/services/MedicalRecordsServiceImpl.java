package com.benoit.safetyAlert.services;

import com.benoit.safetyAlert.dao.MedicalRecordDao;
import com.benoit.safetyAlert.dto.PersonInfo;
import com.benoit.safetyAlert.exceptions.DataAlreadyExistException;
import com.benoit.safetyAlert.exceptions.DataNotFindException;
import com.benoit.safetyAlert.exceptions.InvalidArgumentException;
import com.benoit.safetyAlert.model.Medicalrecords;
import com.benoit.safetyAlert.model.Persons;
import com.benoit.safetyAlert.repository.DataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicalRecordsServiceImpl implements MedicalRecordsService {

  private final DataRepository dataRepository;
  private final MedicalRecordDao medicalrecordDao;

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
        if (person.getMedicalrecords() != null) {
          personInfo.setMedication(person.getMedicalrecords().getMedications());
          personInfo.setAllergies(person.getMedicalrecords().getAllergies());
        }
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
    try {
      List<Persons> persons = dataRepository.getPersons();
      for (Persons person : persons) {
        // on verifie que le medicalrecord n'existe pas dans le repo et que la personne existe

        if (person.getLastName().equalsIgnoreCase(medicalrecord.getLastName())
            && person.getFirstName().equalsIgnoreCase(medicalrecord.getFirstName())
            && person.getMedicalrecords() == null) {
          return medicalrecordDao.createMedicalRecords(medicalrecord);

        } else if (person.getLastName().equalsIgnoreCase(medicalrecord.getLastName())
            && person.getFirstName().equalsIgnoreCase(medicalrecord.getFirstName())
            && person.getMedicalrecords() != null) {

          throw new DataAlreadyExistException(
              "Medical record for "
                  + medicalrecord.getFirstName()
                  + " "
                  + medicalrecord.getLastName()
                  + " already exist.");
        } else if (medicalrecord.getFirstName() == null || medicalrecord.getLastName() == null) {
          throw new NullPointerException();
        } else {
          throw new DataNotFindException(
              "Can't create medical record for "
                  + medicalrecord.getFirstName()
                  + " "
                  + medicalrecord.getLastName()
                  + ", this person doesn't exist in Person DB.");
        }
      }
      return false;
    } catch (NullPointerException nullPointerException) {
      throw new IllegalArgumentException(
          "Invalid Argument : MedicalRecord | firstName | lastName can't be null.",
          nullPointerException);
    }
  }

  @Override
  public boolean deleteMedicalRecord(Medicalrecords medicalRecord) {
    try {

      if (dataRepository.getMedicalrecords().contains(medicalRecord)
          && medicalRecord != null
          && !medicalRecord.equals(new Medicalrecords())) {
        medicalrecordDao.deleteMedicalRecords(medicalRecord);
        return true;
      } else if (!dataRepository.getMedicalrecords().contains(medicalRecord)) {
        throw new DataNotFindException(
            "medical record for "
                + medicalRecord.getFirstName()
                + " "
                + medicalRecord.getLastName()
                + " does not exist.");
      } else {
        throw new NullPointerException();
      }
    } catch (NullPointerException nullPointerException) {
      throw new InvalidArgumentException(
          "Invalid Argument : MedicalRecord | firstName | lastName can't be null.",
          nullPointerException);
    }
  }

  @Override
  public boolean updateMedicalRecord(Medicalrecords medicalrecord) {
    deleteMedicalRecord(medicalrecord);
    return createMedicalRecord(medicalrecord);
  }
}
