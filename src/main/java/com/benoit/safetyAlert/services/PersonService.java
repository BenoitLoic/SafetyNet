package com.benoit.safetyAlert.services;

import com.benoit.safetyAlert.dto.PersonInfo;
import com.benoit.safetyAlert.model.Medicalrecords;
import com.benoit.safetyAlert.model.Persons;
import com.benoit.safetyAlert.repository.DataRepository;
import com.benoit.safetyAlert.utility.CalculateAge;
import com.benoit.safetyAlert.utility.Counter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PersonService implements IPersonService {

  @Autowired DataRepository dataRepository;
  @Autowired FireStationService firestationService;

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
  public Collection<String> getPhoneNumber(String station) {

    // recuperation des adresses de la station
    List<String> fireStationAddress = firestationService.getFireStationAddress(station);
    // creation d'un hashset pour éviter les doublons
    Collection<String> phoneNumber = new HashSet<>();
    // boucle pour récupérer les tel des utilisateurs
    for (String address : fireStationAddress) {

      List<Persons> personByAddress = dataRepository.getPersonByAddress(address);

      for (Persons person : personByAddress) {
        phoneNumber.add(person.getPhone());
      }
    }
    return phoneNumber;
  }

  @Override
  public Collection<Object> getPersonCoveredByFireStation(String stationNumber) {

    // recupération des address de la station
    Counter counter = new Counter();
    Collection<Object> personCovered = new ArrayList<>();
    // on déroule la liste d'address
    for (String address : firestationService.getFireStationAddress(stationNumber)) {
      List<Persons> personByAddress = dataRepository.getPersonByAddress(address);
      for (Persons person : personByAddress) {
        List<String> user = new ArrayList<>();
        PersonInfo tmp = getFullPersonInfo(person.getFirstName(), person.getLastName());
        user.add(tmp.getFirstName());
        user.add(tmp.getLastName());
        user.add(tmp.getAddress());
        user.add(tmp.getPhone());
        personCovered.add(user);
        if (tmp.getAge() >= 18) {
          counter.incrementAdult();
        } else {
          counter.incrementChild();
        }
      }
    }

    personCovered.add("Adult: " + counter.getAdult());
    personCovered.add("Child: " + counter.getChild());

    counter.reset();

    return personCovered;
  }

  @Override
  public Collection<Object> getPersonInfo(String firstName, String lastName) {
    PersonInfo tmp = getFullPersonInfo(firstName, lastName);
    Collection<Object> personInfo = new ArrayList<>();
    Collections.addAll(
        personInfo,
        tmp.getFirstName(),
        tmp.getLastName(),
        tmp.getAddress(),
        tmp.getEmail(),
        tmp.getMedication(),
        tmp.getAllergies());
    return personInfo;
  }

  @Override
  public Collection getFireAddress(String address) {
    List<Persons> personByAddress = dataRepository.getPersonByAddress(address);
    Collection<Collection<String>> personInfo = new ArrayList<>();
    for (Persons person : personByAddress) {
      Collection<String> user = new ArrayList<>();
      PersonInfo tmp = getFullPersonInfo(person.getFirstName(), person.getLastName());

      user.add(tmp.getFirstName());
      user.add(tmp.getLastName());
      user.add(String.valueOf(tmp.getMedication()));
      user.add(String.valueOf(tmp.getAllergies().toString()));
      // FIXME retourne une liste vide au lieu du numero de station
      user.add(String.valueOf(tmp.getStation()));
      personInfo.add(user);
    }
    return personInfo;
  }

  @Override
  public Collection<Object> getFloodStations(List<String> station) {
    Counter counter = new Counter();
    // LVL 1 collection
    Collection<Object> floodStations = new ArrayList<>();
    Collection<String> fireStationsAddress;
    //        récupération des adresses des stations
    if (station.size() > 1) {
      fireStationsAddress = firestationService.getFireStationAddress(station);
    } else {
      fireStationsAddress = firestationService.getFireStationAddress(station);
    }
    for (String address : fireStationsAddress) {
      // LVL2 collection
      Collection<Object> floodStationsAddress = new ArrayList<>();
      //        récupération des personnes
      Collection<Persons> personsList = dataRepository.getPersonByAddress(address);
      //          récupération des info perso de chaque personne
      for (Persons person : personsList) {
        // LVL3 collection
        Collection<Object> personInfos = new ArrayList<>();
        PersonInfo tmpPersonInfo = getFullPersonInfo(person.getFirstName(), person.getLastName());
        // LVL 4 collection
        List<Object> medicalRecords = new ArrayList<>();
        Collections.addAll(
            medicalRecords, tmpPersonInfo.getMedication(), tmpPersonInfo.getAllergies());
        // LVL4->3
        Collections.addAll(
            personInfos,
            tmpPersonInfo.getFirstName(),
            tmpPersonInfo.getLastName(),
            medicalRecords,
            tmpPersonInfo.getAge(),
            tmpPersonInfo.getPhone());
        // LVL3->2
        floodStationsAddress.add(personInfos);
        counter.incrementAdult();
      }
      // LVL2->1
      Collections.addAll(floodStations, address, floodStationsAddress);
    }
    floodStations.add(counter.getAdult());
    counter.reset();
    return floodStations;
  }

  @Override
  public Collection<Object> getChildAlert(String address) {

    final int adultAge = 18;
    Collection<Object> childAlertList = new ArrayList<>();
    //      recupération de la liste des personnes à cette adresse
    Collection<Persons> listOfPersons = dataRepository.getPersonByAddress(address);
    //      recupération des info de chaque personnes
    for (Persons person : listOfPersons) {
      //          verification de l'age
      PersonInfo personInfo = getFullPersonInfo(person.getFirstName(), person.getLastName());
      if (personInfo.getAge() <= adultAge) {
        //                si age<= 18 on ajoute firstName, lastName, age à la collection
        Collections.addAll(
            childAlertList,
            personInfo.getFirstName(),
            personInfo.getLastName(),
            personInfo.getAge());
        //              recupération des autres personnes avec le mm lastName ajoutés en temps que
        // List
        //              dans la collection

        for (Persons family : listOfPersons) {
          if (personInfo.getLastName().equalsIgnoreCase(family.getLastName())
              && !personInfo.getFirstName().equalsIgnoreCase(family.getFirstName())) {
            List<String> childFamily = new ArrayList<>();
            Collections.addAll(childFamily, family.getFirstName(), family.getLastName());
            childAlertList.add(childFamily);
          }
        }
      }
      //          si aucun enfant (age<=18) on renvoi une liste vide
    }

    return childAlertList;
  }

  @Override
  public PersonInfo getFullPersonInfo(String firstName, String lastName) {

    PersonInfo personInfo = new PersonInfo();

    List<Persons> personByID = dataRepository.getPersonByID(firstName, lastName);

    for (Persons person : personByID) {
      personInfo.setFirstName(person.getFirstName());
      personInfo.setLastName(person.getLastName());
      personInfo.setAddress(person.getAddress());
      personInfo.setPhone(person.getPhone());
      personInfo.setEmail(person.getEmail());
      personInfo.setStation(firestationService.getFireStationStationNumber(person.getAddress()));
    }

    List<Medicalrecords> medicalRecordsByID =
        dataRepository.getMedicalRecordByID(firstName, lastName);

    for (Medicalrecords medicalrecords : medicalRecordsByID) {
      personInfo.setAge(CalculateAge.calculateAge(medicalrecords.getBirthdate()));
      personInfo.setMedication(medicalrecords.getMedications());
      personInfo.setAllergies(medicalrecords.getAllergies());
    }

    return personInfo;
  }
}
