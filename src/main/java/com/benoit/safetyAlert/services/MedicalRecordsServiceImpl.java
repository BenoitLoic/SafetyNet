package com.benoit.safetyAlert.services;

import com.benoit.safetyAlert.dto.PersonInfo;
import com.benoit.safetyAlert.model.Medicalrecords;
import com.benoit.safetyAlert.model.Persons;
import com.benoit.safetyAlert.repository.DataRepository;
import com.benoit.safetyAlert.utility.CalculateAge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
public class MedicalRecordsServiceImpl implements MedicalRecordsService {

  @Autowired private DataRepository dataRepository;

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
  public PersonInfo getFullPersonInfo(String firstName, String lastName) {

    PersonInfo personInfo = new PersonInfo();

    List<Persons> personByID = dataRepository.getPersonByID(firstName, lastName);

    for (Persons person : personByID) {
      personInfo.setFirstName(person.getFirstName());
      personInfo.setLastName(person.getLastName());
      personInfo.setAddress(person.getAddress());
      personInfo.setPhone(person.getPhone());
      personInfo.setEmail(person.getEmail());
    }

    List<Medicalrecords> medicalRecordsByID =
        dataRepository.getMedicalRecordByID(firstName, lastName);

    for (Medicalrecords medicalRecords : medicalRecordsByID) {
      personInfo.setAge(CalculateAge.calculateAge(medicalRecords.getBirthdate()));
      personInfo.setMedication(medicalRecords.getMedications());
      personInfo.setAllergies(medicalRecords.getAllergies());
    }

    return personInfo;
  }
}
