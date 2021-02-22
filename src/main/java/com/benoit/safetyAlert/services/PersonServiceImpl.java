package com.benoit.safetyAlert.services;

import com.benoit.safetyAlert.dao.PersonDao;
import com.benoit.safetyAlert.dto.PersonInfo;
import com.benoit.safetyAlert.exceptions.DataAlreadyExistException;
import com.benoit.safetyAlert.exceptions.DataNotFindException;
import com.benoit.safetyAlert.model.Persons;
import com.benoit.safetyAlert.repository.DataRepository;
import com.benoit.safetyAlert.utility.CalculateAge;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

/**
 * The type Person service.
 */
@Service
public class PersonServiceImpl implements PersonService {

  private final DataRepository dataRepository;
  private final PersonDao personDao;
  private final CalculateAge calculateAge;
  private static final Logger LOGGER = LogManager.getLogger(PersonServiceImpl.class);

  /**
   * Instantiates a new Person service.
   *
   * @param dataRepository the data repository
   * @param personDao      the person dao
   * @param calculateAge   the calculate age
   */
  @Autowired
  public PersonServiceImpl(
      DataRepository dataRepository,
      PersonDao personDao,
      CalculateAge calculateAge) {
    this.dataRepository = dataRepository;
    this.personDao = personDao;
    this.calculateAge = calculateAge;
  }

  /**
   * This method gets all Person in repository and return a list of email
   * of all person living in the city given as parameter.
   *
   * @param city the city
   * @return a list of Email
   */
  @Override
  public Collection<Persons> getCommunityEmail(String city) {

    Collection<Persons> collectionEmail = new HashSet<>();
    for (Persons person : dataRepository.getPersons()) {
      if (person.getCity().equalsIgnoreCase(city)) {

        Persons persons = new Persons();
        persons.setEmail(person.getEmail());

        collectionEmail.add(persons);
      }
    }
    return collectionEmail;
  }

  /**
   * This method will process data from person repository
   * to give a list of person living at the @param address.
   * person information includes : firstName, lastName, Age, MedicalRecords and station.
   *
   * @param address the address
   * @return collection of personInfo Dto
   */
  @Override
  public Collection<PersonInfo> getFireAddress(String address) {
    List<PersonInfo> returnList = new ArrayList<>();

    for (Persons person : dataRepository.getPersons()) {
      if (person.getAddress().equals(address)) {
        PersonInfo personInfo = new PersonInfo();

        personInfo.setFirstName(person.getFirstName());
        personInfo.setLastName(person.getLastName());
        personInfo.setPhone(person.getPhone());
        personInfo.setMedication(person.getMedicalrecords().getMedications());
        personInfo.setAllergies(person.getMedicalrecords().getAllergies());
        personInfo.getStation().add(person.getFirestation().getStation());
        if (person.getMedicalrecords().getBirthdate() != null) {
          personInfo.setAge(calculateAge.calculateAge(person.getMedicalrecords().getBirthdate()));
        }
        returnList.add(personInfo);
      }
    }
    return returnList;
  }

  /**
   * This method process data from person repository
   * to return a list of child (age<18) living at the address.
   * child information includes : firstName, lastName, age
   * and a list of other person living at this address.
   *
   * @param address the address
   * @return collection of PersonInfo
   */
  @Override
  public Collection<PersonInfo> getChildAlert(String address) {
    final int adultAge = 18;
    Collection<PersonInfo> childAlertList = new ArrayList<>();
    List<Persons> personsList = dataRepository.getPersons();

    for (Persons person : personsList) {

      // add child for this address
      if (person.getAddress().equalsIgnoreCase(address)
          && calculateAge.calculateAge(person.getMedicalrecords().getBirthdate()) <= adultAge) {
        PersonInfo childInfo = new PersonInfo();
        childInfo.setFirstName(person.getFirstName());
        childInfo.setLastName(person.getLastName());
        childInfo.setAge(calculateAge.calculateAge(person.getMedicalrecords().getBirthdate()));
        childInfo.setAllergies(null);
        childInfo.setMedication(null);

        // add child's family member
        for (Persons family : personsList) {

          if (family.getAddress().equalsIgnoreCase(address)
              && family.getLastName().equalsIgnoreCase(childInfo.getLastName())
              && calculateAge.calculateAge(family.getMedicalrecords().getBirthdate()) > adultAge) {
            PersonInfo personInfo = new PersonInfo();
            personInfo.setFirstName(family.getFirstName());
            personInfo.setLastName(family.getLastName());
            personInfo.setMedication(null);
            personInfo.setAllergies(null);
            childInfo.getFamily().add(personInfo);
          }
        }
        childAlertList.add(childInfo);
      }
    }

    return childAlertList;
  }

  /**
   * Create person by calling Dao.
   * This method call for repository to check if the person already exist.
   * if person doesn't exist : call Dao.
   * Else, it will throw DataAlreadyExistException
   *
   * @param person the person to create
   * @return true if success
   */
  @Override
  public boolean createPerson(Persons person) {

    // on verifie que la personne n'existe pas dans le repo
    if (!dataRepository.getPersons().contains(person)) {
      personDao.createPerson(person);
      return true;
    } else {
      LOGGER.error("error :  "
          + person.getFirstName()
          + " "
          + person.getLastName()
          + " already exist.");
      throw new DataAlreadyExistException(
          "this person "
              + person.getFirstName()
              + " "
              + person.getLastName()
              + " already exist.");
    }
  }

  /**
   * Delete person by calling Dao.
   * This method call for repository to check if the person already exist.
   * if person exist : call Dao.
   * Else, it will throw DataNotFindException.
   *
   * @param person the person to delete
   * @return true if success
   */
  @Override
  public boolean deletePerson(Persons person) {

    if (dataRepository.getPersons().contains(person)) {
      personDao.deletePerson(person);
      return true;

    } else {
      LOGGER.error("error : "
          + person.getFirstName()
          + " "
          + person.getLastName()
          + " do not exist.");
      throw new DataNotFindException(
          "this person : "
              + person.getFirstName()
              + " "
              + person.getLastName()
              + " do not exist.");
    }
  }

  /**
   * Update person by calling Dao.
   * This method call for repository to check if the person already exist.
   * if person exist : call Dao.
   * Else, it will throw DataNotFindException
   *
   * @param person the person to update
   * @return true if success
   */
  @Override
  public boolean updatePerson(Persons person) {

    if (dataRepository.getPersons().contains(person)) {

      return personDao.updatePerson(person);
    } else {
      LOGGER.error(
          "error updating person : "
              + person.getFirstName()
              + " "
              + person.getLastName()
              + " doesn't exist.");
      throw new DataNotFindException(
          person.getFirstName()
              + " "
              + person.getLastName()
              + " doesn't exist in repository");
    }
  }


}

