package com.benoit.safetyAlert.services;

import com.benoit.safetyAlert.dto.PersonInfo;
import com.benoit.safetyAlert.model.Medicalrecords;
import com.benoit.safetyAlert.model.Persons;
import com.benoit.safetyAlert.repository.DataRepository;
import com.benoit.safetyAlert.utility.CalculateAge;
import com.benoit.safetyAlert.utility.Counter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
public class PersonService implements IPersonService {

    @Autowired
    DataRepository dataRepository;
    @Autowired
    FirestationService firestationService;


    public Collection<String> getCommunityEmail(String city) {

        List<Persons> personByCity = dataRepository.getPersonByCity(city);
        Collection<String> collectionEmail = new ArrayList<>();
        for (Persons person : personByCity) {

            collectionEmail.add(person.getEmail());
        }
        return collectionEmail;
    }

    public Collection<String> getPhoneNumber(String address) {

        List<Persons> personByAddress = dataRepository.getPersonByAddress(address);
        Collection<String> collectionPhoneNumber = new ArrayList<>();
        for (Persons person : personByAddress) {
            collectionPhoneNumber.add(person.getPhone());
        }
        return collectionPhoneNumber;
    }

    public Collection<List<String>> getPersonCoveredByFirestation(String address) {
        List<Persons> personByAddress = dataRepository.getPersonByAddress(address);
        Collection<List<String>> personInfo = new ArrayList<>();
        Counter counter = new Counter();
        for (Persons person : personByAddress) {
            List<String> user = new ArrayList<>();
            PersonInfo tmp = getFullPersonInfo(person.getFirstName(), person.getLastName());

            user.add(tmp.getFirstName());
            user.add(tmp.getLastName());
            user.add(tmp.getAddress());
            user.add(tmp.getPhone());
            personInfo.add(user);
            if (tmp.getAge() >= 18) {
                counter.incrementAdult();
            } else {
                counter.incrementChild();
            }
        }
        return personInfo;
    }


    public Collection getPersonInfo(String firstName, String lastName) {
        PersonInfo tmp = getFullPersonInfo(firstName, lastName);
        Collection personInfo = new ArrayList<>();
        Collections.addAll(personInfo,
                tmp.getFirstName(),
                tmp.getLastName(),
                tmp.getAddress(),
                tmp.getEmail(),
                tmp.getMedication(),
                tmp.getAllergies());
        return personInfo;
    }

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
            user.add(String.valueOf(firestationService.getFirestationStation(address)));
            personInfo.add(user);

        }
        return personInfo;
    }

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

        List<Medicalrecords> medicalRecordsByID = dataRepository.getMedicalRecordByID(firstName, lastName);

        for (Medicalrecords medicalrecords : medicalRecordsByID) {
            personInfo.setFirstName(medicalrecords.getFirstName());
            personInfo.setLastName(medicalrecords.getLastName());
            personInfo.setAge(CalculateAge.calculateAge(medicalrecords.getBirthdate()));
            personInfo.setMedication(medicalrecords.getMedications());
            personInfo.setAllergies(medicalrecords.getAllergies());
        }

        return personInfo;

    }

}
