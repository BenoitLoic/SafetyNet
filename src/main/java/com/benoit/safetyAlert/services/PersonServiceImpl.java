package com.benoit.safetyAlert.services;

import com.benoit.safetyAlert.dao.PersonDao;
import com.benoit.safetyAlert.dao.PersonDaoImpl;
import com.benoit.safetyAlert.dto.PersonInfo;
import com.benoit.safetyAlert.exceptions.DataAlreadyExistException;
import com.benoit.safetyAlert.model.Persons;
import com.benoit.safetyAlert.repository.DataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
public class PersonServiceImpl implements PersonService {

  @Autowired DataRepository dataRepository;
  @Autowired FirestationServiceImpl firestationService;
  @Autowired MedicalRecordsService medicalRecordsService;
  @Autowired
  PersonDao personDao;

  @Override
  public Collection<String> getCommunityEmail(String city) {

    List<Persons> personByCity = dataRepository.getPersonByCity(city);
    Collection<String> collectionEmail = new ArrayList<>();
    for (Persons person : personByCity) {
      collectionEmail.add(person.getEmail());
    }
    return collectionEmail;
  }

  @Override
  public Collection<Object> getFireAddress(String address) {
    List<Persons> personByAddress = dataRepository.getPersonByAddress(address);
    Collection firestation = firestationService.getFirestationNumber(address);
    Collection<Object> personInfo = new ArrayList<>();
    for (Persons person : personByAddress) {
      Collection<Object> user = new ArrayList<>();
      PersonInfo tmp = medicalRecordsService.getFullPersonInfo(person.getFirstName(), person.getLastName());

      user.add(tmp.getFirstName());
      user.add(tmp.getLastName());
      user.add(tmp.getAge());
      user.add("Medication: " + tmp.getMedication());
      user.add("Allergies: " + tmp.getAllergies());
      user.add("Firestation: " + firestation);
      personInfo.add(user);
    }
    return personInfo;
  }

  @Override
  public Collection<Object> getChildAlert(String address) {

    final int adultAge = 18;
    Collection<Object> childAlertList = new ArrayList<>();
    // recupération de la liste des personnes à cette adresse
    Collection<Persons> listOfPersons = dataRepository.getPersonByAddress(address);
    // recupération des info de chaque personnes
    for (Persons person : listOfPersons) {
      // verification de l'age
      PersonInfo personInfo =
          medicalRecordsService.getFullPersonInfo(person.getFirstName(), person.getLastName());
      if (personInfo.getAge() <= adultAge) {
        // si age<= 18 on ajoute firstName, lastName, age à la collection
        Collections.addAll(
            childAlertList,
            personInfo.getFirstName(),
            personInfo.getLastName(),
            personInfo.getAge());
        // recupération des autres personnes avec le mm lastName ajoutés en temps que List
        // dans la collection

        for (Persons family : listOfPersons) {
          if (personInfo.getLastName().equalsIgnoreCase(family.getLastName())
              && !personInfo.getFirstName().equalsIgnoreCase(family.getFirstName())) {
            List<String> childFamily = new ArrayList<>();
            Collections.addAll(childFamily, family.getFirstName(), family.getLastName());
            childAlertList.add(childFamily);
          }
        }
      }
      // si aucun enfant (age<=18) on renvoi une liste vide
    }

    return childAlertList;
  }

  @Override
  public boolean createPerson(Persons person) {
//on verifie que la personne n'existe pas dans la dao
    if (!dataRepository.getDatabaseJson().getPersons().contains(person)){
      personDao.createPerson(person);
      return true;
    }else {
      throw new DataAlreadyExistException("la personne "+person.getFirstName()+" "+person.getLastName()+" existe déjà.");
    }

  }

  @Override
  public boolean deletePerson(Persons person) {
    return false;
  }

  @Override
  public boolean updatePerson(Persons person) {
    return false;
  }
}
