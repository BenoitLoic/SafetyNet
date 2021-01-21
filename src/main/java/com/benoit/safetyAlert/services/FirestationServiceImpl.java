package com.benoit.safetyAlert.services;

import com.benoit.safetyAlert.dao.FirestationDao;
import com.benoit.safetyAlert.dto.PersonInfo;
import com.benoit.safetyAlert.exceptions.DataAlreadyExistException;
import com.benoit.safetyAlert.exceptions.DataNotFindException;
import com.benoit.safetyAlert.model.Firestation;
import com.benoit.safetyAlert.model.Persons;
import com.benoit.safetyAlert.repository.DataRepository;
import com.benoit.safetyAlert.utility.CalculateAge;
import com.benoit.safetyAlert.utility.Counter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

/** This class contains some method to extract fire station data from DataRepository. */
@Service
public class FirestationServiceImpl implements FirestationService {


  @Autowired private DataRepository dataRepository;
  @Autowired private FirestationDao firestationDao;

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

  @Override
  public Collection<PersonInfo> getPersonCoveredByFireStation(String stationNumber) {
    Collection<PersonInfo> listOfPersonCovered = new ArrayList<>();
    Counter counter = new Counter();

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
          counter.process(CalculateAge.calculateAge(person.getMedicalrecords().getBirthdate()));
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

  @Override
  public Collection<PersonInfo> getFloodStations(List<String> stations) {
    Collection<PersonInfo> floodStations = new HashSet<>();

    for (Firestation firestation : dataRepository.getFirestations()) {
      for (String station : stations) {
        if (firestation.getStation().equals(station)) {
          for (Persons person : firestation.getPersons()) {
            PersonInfo personInfo = new PersonInfo();
            personInfo.setFirstName(person.getFirstName());
            personInfo.setLastName(person.getLastName());
            personInfo.setMedication(person.getMedicalrecords().getMedications());
            personInfo.setAllergies(person.getMedicalrecords().getAllergies());
            personInfo.setAge(CalculateAge.calculateAge(person.getMedicalrecords().getBirthdate()));
            floodStations.add(personInfo);
          }
        }
      }
    }
    return floodStations;
  }

  @Override
  public boolean createFirestation(Firestation firestation) {

    if (!dataRepository.getDatabaseJson().getFirestations().contains(firestation)) {

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

  @Override
  public boolean deleteFirestation(Firestation firestation) {

    if (dataRepository.getDatabaseJson().getFirestations().contains(firestation)) {

      firestationDao.deleteFirestation(firestation);
      return true;
    } else {
      throw new DataNotFindException(
          "this firestation "
              + firestation.getStation()
              + "/ address : "
              + firestation.getAddress()
              + " doesn't exist.");
    }
  }
}
