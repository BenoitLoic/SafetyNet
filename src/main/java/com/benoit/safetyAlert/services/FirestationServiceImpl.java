package com.benoit.safetyAlert.services;

import com.benoit.safetyAlert.dao.FirestationDao;
import com.benoit.safetyAlert.dto.FirestationDto;
import com.benoit.safetyAlert.dto.PersonInfo;
import com.benoit.safetyAlert.exceptions.DataAlreadyExistException;
import com.benoit.safetyAlert.exceptions.DataNotFindException;
import com.benoit.safetyAlert.model.Firestation;
import com.benoit.safetyAlert.model.Persons;
import com.benoit.safetyAlert.repository.DataRepository;
import com.benoit.safetyAlert.utility.CalculateAge;
import com.benoit.safetyAlert.utility.Counter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;


/**
 * This class contains method to process data and logic for Firestation Controller.
 */
@Service
public class FirestationServiceImpl implements FirestationService {

  private final DataRepository dataRepository;
  private final FirestationDao firestationDao;
  private static final Logger LOGGER = LogManager.getLogger(FirestationServiceImpl.class);


  /**
   * Instantiates a new Firestation service.
   *
   * @param dataRepository the data repository
   * @param firestationDao the firestation dao
   */
  @Autowired
  public FirestationServiceImpl(DataRepository dataRepository, FirestationDao firestationDao) {
    this.dataRepository = dataRepository;
    this.firestationDao = firestationDao;
  }

  /**
   * This method gets the list of firestation from the linked repository and return the list of
   * phone number for all person covered by this station.
   *
   * @param station the station number
   * @return Collection of phone number
   */
  @Override
  public Collection<Persons> getPhoneNumber(String station) {
    Collection<Persons> listOfPhoneNumber = new HashSet<>();
    for (Firestation firestation : dataRepository.getFirestations()) {
      if (firestation.getStation().equals(station)) {
        for (Persons person : firestation.getPersons()) {
          Persons personPhone = new Persons();
          personPhone.setPhone(person.getPhone());
          listOfPhoneNumber.add(personPhone);
        }
      }
    }

    return listOfPhoneNumber;
  }

  /**
   * This method gets the list of firestation from the linked repository and return a list of
   * person (name, age, address, phone) and the number of child and adult present.
   *
   * @param stationNumber the station number
   * @return Collection of PersonInfo Dto
   */
  @Override
  public Collection<PersonInfo> getPersonCoveredByFireStation(String stationNumber) {
    Collection<PersonInfo> listOfPersonCovered = new ArrayList<>();
    Counter counter = new Counter();
    CalculateAge calc = new CalculateAge();
    List<Firestation> firestations = dataRepository.getFirestations();
    for (Firestation firestation : firestations) {
      if (firestation.getStation().equals(stationNumber)) {
        List<Persons> persons = firestation.getPersons();
        for (Persons person : persons) {
          PersonInfo personInfo = new PersonInfo();
          personInfo.setFirstName(person.getFirstName());
          personInfo.setLastName(person.getLastName());
          personInfo.setAddress(person.getAddress());
          personInfo.setPhone(person.getPhone());
          personInfo.setAllergies(null);
          personInfo.setMedication(null);
          listOfPersonCovered.add(personInfo);

          if (person.getMedicalrecords().getBirthdate() != null) {
            counter.process(calc.calculateAge(person.getMedicalrecords().getBirthdate()));
          }
        }
      }
    }
    PersonInfo count = new PersonInfo();
    count.setNumberOfChild(counter.getChild());
    count.setNumberOfAdult(counter.getAdult());
    count.setAllergies(null);
    count.setMedication(null);
    listOfPersonCovered.add(count);
    return listOfPersonCovered;
  }

  /**
   * This method gets the list of firestation from the linked repository and return a list of
   * person (name, age, phone and medical records) gathered by address.
   *
   * @param stations the list of station number
   * @return Collection of FirestationDto
   */
  @Override
  public Collection<FirestationDto> getFloodStations(List<String> stations) {


    Collection<FirestationDto> firestationDtoList = new HashSet<>();
    CalculateAge calc = new CalculateAge();

    for (Firestation firestation : dataRepository.getFirestations()) {
      for (String station : stations) {
        if (firestation.getStation().equals(station)) {
          FirestationDto firestationDto = new FirestationDto();
          Collection<PersonInfo> floodStations = new HashSet<>();
          for (Persons person : firestation.getPersons()) {
            PersonInfo personInfo = new PersonInfo();
            personInfo.setFirstName(person.getFirstName());
            personInfo.setLastName(person.getLastName());
            personInfo.setPhone(person.getPhone());
            personInfo.setMedication(person.getMedicalrecords().getMedications());
            personInfo.setAllergies(person.getMedicalrecords().getAllergies());
            if (person.getMedicalrecords().getBirthdate() != null) {
              personInfo.setAge(calc.calculateAge(person.getMedicalrecords().getBirthdate()));
            }
            floodStations.add(personInfo);
          }

          firestationDto.setAddress(firestation.getAddress());
          firestationDto.setPersonInfos(new ArrayList<>(floodStations));
          firestationDtoList.add(firestationDto);

        }
      }
    }

    List<FirestationDto> firestationDtoListSorted = new ArrayList<>(firestationDtoList);
    firestationDtoListSorted.sort(FirestationDto.comparator);

    return firestationDtoListSorted;
  }

  /**
   * Create firestation by calling Dao.
   * This method call for repository to check if the firestation already exist.
   * if firestation doesn't exist : call Dao.
   * Else, it will throw DataAlreadyExistException
   *
   * @param firestation the firestation
   * @return true if success
   */
  @Override
  public boolean createFirestation(Firestation firestation) {


    if (!dataRepository.getFirestations().contains(firestation)) {

      firestationDao.createFirestation(firestation);
      return true;

    } else {
      throw new DataAlreadyExistException(
          "this firestation "
              + firestation.getStation()
              + " / address : "
              + firestation.getAddress()
              + " already exist");
    }

  }

  /**
   * Delete firestation by calling Dao.
   * This method call for repository to check if the firestation already exist.
   * if firestation doesn't exist : call Dao.
   * Else, it will throw DataNotFindException
   *
   * @param firestation the firestation
   * @return true if success
   */
  @Override
  public boolean deleteFirestation(Firestation firestation) {

    if (dataRepository.getFirestations().contains(firestation)) {
      firestationDao.deleteFirestation(firestation);
      return true;
    } else {
      LOGGER.info("this firestation "
          + firestation.getStation()
          + "/ address : "
          + firestation.getAddress()
          + " doesn't exist.");
      throw new DataNotFindException(
          "this firestation "
              + firestation.getStation()
              + "/ address : "
              + firestation.getAddress()
              + " doesn't exist.");
    }

  }

  /**
   * Update firestation by calling Dao.
   * This method call for repository to check if the firestation already exist.
   * if firestation exist : call Dao.
   * Else, it will throw DataNotFindException
   *
   * @param firestation the firestation
   * @return true if success
   */
  @Override
  public boolean updateFirestation(Firestation firestation) {


    for (Firestation fire : dataRepository.getFirestations()) {
      if (fire.getAddress().equalsIgnoreCase(firestation.getAddress())) {
        return firestationDao.updateFirestation(firestation);
      }
    }
    LOGGER.info("error updating firestation ["
        + firestation.getStation()
        + "] - address : "
        + firestation.getAddress()
        + " doesn't exist.");
    throw new DataNotFindException("error updating firestation ["
        + firestation.getStation()
        + "] - address : "
        + firestation.getAddress()
        + " doesn't exist.");

  }
}
