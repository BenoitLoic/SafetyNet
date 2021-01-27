package com.benoit.safetyAlert.services;

import com.benoit.safetyAlert.dao.PersonDao;
import com.benoit.safetyAlert.dto.PersonInfo;
import com.benoit.safetyAlert.exceptions.DataAlreadyExistException;
import com.benoit.safetyAlert.exceptions.DataNotFindException;
import com.benoit.safetyAlert.model.Persons;
import com.benoit.safetyAlert.repository.DataRepository;
import com.benoit.safetyAlert.utility.CalculateAge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PersonServiceImpl implements PersonService {

  @Autowired DataRepository dataRepository;
  @Autowired PersonDao personDao;

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
    for (Persons person : dataRepository.getPersons()) {
      if (person.getAddress().equals(address)) {
        PersonInfo personInfo = new PersonInfo();

        personInfo.setFirstName(person.getFirstName());
        personInfo.setLastName(person.getLastName());
        personInfo.setAge(CalculateAge.calculateAge(person.getMedicalrecords().getBirthdate()));
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

    for (Persons person : personsList) {

      // add child for this address
      if (CalculateAge.calculateAge(person.getMedicalrecords().getBirthdate()) <= adultAge
              && person.getAddress().equalsIgnoreCase(address)) {
        PersonInfo childInfo = new PersonInfo();
        childInfo.setFirstName(person.getFirstName());
        childInfo.setLastName(person.getLastName());
        childInfo.setAge(CalculateAge.calculateAge(person.getMedicalrecords().getBirthdate()));
        childInfo.setAllergies(null);
        childInfo.setMedication(null);

        // add child's family member
        for (Persons family : personsList) {
          if (family.getAddress().equalsIgnoreCase(address)
                  && family.getLastName().equalsIgnoreCase(childInfo.getLastName())
                  && CalculateAge.calculateAge(family.getMedicalrecords().getBirthdate()) > adultAge) {
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
    // on verifie que la personne n'existe pas dans la dao
    if (!dataRepository.getPersons().contains(person)) {
      personDao.createPerson(person);
      return true;
    } else {
      throw new DataAlreadyExistException(
              "this person " + person.getFirstName() + " " + person.getLastName() + " already exist.");
    }
  }

  @Override
  public boolean deletePerson(Persons person) {
    if (dataRepository.getPersons().contains(person)) {
      personDao.deletePerson(person);
      return true;
    } else {
      throw new DataNotFindException(
              "this person : " + person.getFirstName() + " " + person.getLastName() + " do not exist.");
    }
  }

  @Override
  public boolean updatePerson(Persons person) {

    deletePerson(person);
    return createPerson(person);
  }
}
