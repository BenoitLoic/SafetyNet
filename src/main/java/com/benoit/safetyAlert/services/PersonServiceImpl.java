package com.benoit.safetyAlert.services;

import com.benoit.safetyAlert.dao.PersonDao;
import com.benoit.safetyAlert.dto.PersonInfo;
import com.benoit.safetyAlert.exceptions.DataAlreadyExistException;
import com.benoit.safetyAlert.exceptions.DataNotFindException;
import com.benoit.safetyAlert.exceptions.InvalidArgumentException;
import com.benoit.safetyAlert.model.Persons;
import com.benoit.safetyAlert.repository.DataRepository;
import com.benoit.safetyAlert.utility.CalculateAge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PersonServiceImpl implements PersonService {

  private final DataRepository dataRepository;
  private final PersonDao personDao;

  @Autowired
  public PersonServiceImpl(DataRepository dataRepository, PersonDao personDao) {
    this.dataRepository = dataRepository;
    this.personDao = personDao;
  }

  @Override
  public Collection<Persons> getCommunityEmail(String city) {

    Collection<Persons> collectionEmail = new HashSet<>();
    for (Persons person : dataRepository.getPersons()) {
      if (person.getCity().equalsIgnoreCase(city)) {

        Persons personInfo = new Persons();
        personInfo.setEmail(person.getEmail());

        collectionEmail.add(personInfo);
      }
    }
    return collectionEmail;
  }

  @Override
  public Collection<PersonInfo> getFireAddress(String address) {
    List<PersonInfo> returnList = new ArrayList<>();
    CalculateAge calc = new CalculateAge();
    for (Persons person : dataRepository.getPersons()) {
      if (person.getAddress().equals(address)) {
        PersonInfo personInfo = new PersonInfo();

        personInfo.setFirstName(person.getFirstName());
        personInfo.setLastName(person.getLastName());
        personInfo.setAge(calc.calculateAge(person.getMedicalrecords().getBirthdate()));
        personInfo.setMedication(person.getMedicalrecords().getMedications());
        personInfo.setAllergies(person.getMedicalrecords().getAllergies());
        personInfo.setStation(person.getFirestation().getStation());
        returnList.add(personInfo);
      }
    }
    return returnList;
  }

  @Override
  public Collection<PersonInfo> getChildAlert(String address) {
    final int adultAge = 18;
    Collection<PersonInfo> childAlertList = new ArrayList<>();
    List<Persons> personsList = dataRepository.getPersons();
    CalculateAge calc = new CalculateAge();
    for (Persons person : personsList) {

      // add child for this address
      if (calc.calculateAge(person.getMedicalrecords().getBirthdate()) <= adultAge
          && person.getAddress().equalsIgnoreCase(address)) {
        PersonInfo childInfo = new PersonInfo();
        childInfo.setFirstName(person.getFirstName());
        childInfo.setLastName(person.getLastName());
        childInfo.setAge(calc.calculateAge(person.getMedicalrecords().getBirthdate()));
        childInfo.setAllergies(null);
        childInfo.setMedication(null);

        // add child's family member
        for (Persons family : personsList) {
          if (family.getAddress().equalsIgnoreCase(address)
              && family.getLastName().equalsIgnoreCase(childInfo.getLastName())
              && calc.calculateAge(family.getMedicalrecords().getBirthdate()) > adultAge) {
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

  @Override
  public boolean createPerson(Persons person) {
    try {

      // on verifie que la personne n'existe pas dans la dao
      if (!dataRepository.getPersons().contains(person)
          && person != null
          && person.getFirstName() != null
          && person.getLastName() != null) {
        personDao.createPerson(person);
        return true;
      } else if (dataRepository.getPersons().contains(person)) {
        throw new DataAlreadyExistException(
            "this person "
                + person.getFirstName()
                + " "
                + person.getLastName()
                + " already exist.");
      } else {
        throw new NullPointerException();
      }
    } catch (NullPointerException nullPointerException) {
      throw new InvalidArgumentException(
          "Invalid Argument : Person | firstName | lastName can't be null.", nullPointerException);
    }
  }

  @Override
  public boolean deletePerson(Persons person) {
    try {
      if (dataRepository.getPersons().contains(person)
          && person != null
          && person.getFirstName() != null
          && person.getLastName() != null) {
        personDao.deletePerson(person);
        return true;

      } else if (!dataRepository.getPersons().contains(person)
          && person != null
          && person.getFirstName() != null
          && person.getLastName() != null) {
        throw new DataNotFindException(
            "this person : "
                + person.getFirstName()
                + " "
                + person.getLastName()
                + " do not exist.");
      } else {
        throw new NullPointerException();
      }
    } catch (NullPointerException nullPointerException) {
      throw new InvalidArgumentException(
          "Invalid Argument : Person | firstName | lastName can't be null.", nullPointerException);
    }
  }

  @Override
  public boolean updatePerson(Persons person) {

    deletePerson(person);
    return createPerson(person);
  }
}
