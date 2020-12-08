package com.benoit.safetyAlert.services;

import com.benoit.safetyAlert.dto.PersonInfo;
import com.benoit.safetyAlert.model.Firestation;
import com.benoit.safetyAlert.model.Persons;
import com.benoit.safetyAlert.repository.DataRepository;
import com.benoit.safetyAlert.utility.Counter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/** This class contains some method to extract fire station data from DataRepository. */
@Service
public class FirestationServiceImpl implements FirestationService {

  /** The Data repository. */
  @Autowired private DataRepository dataRepository;

  @Autowired private MedicalRecordsService medicalRecordsService;

  /**
   * This method take the station number to extract its addresses
   *
   * @param station the station number
   * @return list of address
   */
  @Override
  public List<String> getFirestationAddress(String station) {

    List<Firestation> firestations = dataRepository.getFirestationByStationNumber(station);
    List<String> listOfFirestationAddress = new ArrayList<>();
    for (Firestation firestation : firestations) {
      listOfFirestationAddress.add(firestation.getAddress());
    }

    return listOfFirestationAddress;
  }

  /**
   * This method take the addresses to find all station that cover it
   *
   * @param address the address
   * @return list of station that cover this address
   */
  @Override
  public Collection<String> getFirestationNumber(String address) {

    List<Firestation> firestationList = dataRepository.getFirestationByAddress(address);
    List<String> listOfFirestationNumber = new ArrayList<>();
    for (Firestation firestation : firestationList) {
      listOfFirestationNumber.add(firestation.getStation());
    }
    return listOfFirestationNumber;
  }

  /**
   * This method take a list of station number to extract all the addresses
   *
   * @param stationNumber list of station number
   * @return list of address
   */
  @Override
  public Collection<String> getFirestationAddress(List<String> stationNumber) {

    Set<String> stationNumberNoDuplicate = new HashSet<>(stationNumber);
    Collection<String> listOfFirestationAddress = new HashSet<>();

    for (String station : stationNumberNoDuplicate) {
      List<Firestation> firestations = dataRepository.getFirestationByStationNumber(station);
      for (Firestation firestation : firestations) {
        listOfFirestationAddress.add(firestation.getAddress());
      }
    }
    return listOfFirestationAddress;
  }

  @Override
  public Collection<String> getPhoneNumber(String station) {

    // recuperation des adresses de la station
    List<String> fireStationAddress = getFirestationAddress(station);
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
    Counter counter = new Counter();
    Collection<Object> personCovered = new ArrayList<>();
    // on déroule la liste desadresses
    for (String address : getFirestationAddress(stationNumber)) {
      List<Persons> personByAddress = dataRepository.getPersonByAddress(address);
      for (Persons person : personByAddress) {
        List<String> user = new ArrayList<>();
        PersonInfo tmp =
            medicalRecordsService.getFullPersonInfo(person.getFirstName(), person.getLastName());
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
  public Collection<Object> getFloodStations(List<String> station) {
    Counter counter = new Counter();
    // LVL 1 collection
    Collection<Object> floodStations = new ArrayList<>();
    Collection<String> fireStationsAddress;
    //        récupération des adresses des stations
    if (station.size() > 1) {
      fireStationsAddress = getFirestationAddress(station);
    } else {
      fireStationsAddress = getFirestationAddress(station);
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
        PersonInfo tmpPersonInfo =
            medicalRecordsService.getFullPersonInfo(person.getFirstName(), person.getLastName());
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
}
