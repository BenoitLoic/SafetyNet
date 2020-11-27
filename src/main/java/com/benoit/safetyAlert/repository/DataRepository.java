package com.benoit.safetyAlert.repository;


import com.benoit.safetyAlert.model.DatabaseJson;
import com.benoit.safetyAlert.model.Firestation;
import com.benoit.safetyAlert.model.Medicalrecords;
import com.benoit.safetyAlert.model.Persons;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Repository
public class DataRepository {
    //cet obj va permettre de mapper du json en obj java
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    //c'est le fichier Json en memoire
    private static DatabaseJson databaseJson;


    public DataRepository() throws IOException {
        //lecture du json
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("data.json");
        databaseJson = OBJECT_MAPPER.readerFor(DatabaseJson.class).readValue(inputStream);
//        databaseJson = OBJECT_MAPPER.readValue(inputStream, DatabaseJson.class);

    }

    public List<Persons> getPersonByCity(String city) {

        List<Persons> personsCollection = new ArrayList<>();

        for (Persons person : databaseJson.getPersons()) {
            if (person.getCity().equalsIgnoreCase(city)) {
                personsCollection.add(person);
            }
        }
        return personsCollection;
    }

    //recupere les firestations avec le numero "station" et renvoi une list
    public List<Firestation> getFireStationByStationNumber(String stationNumber) {

        List<Firestation> firestationAddress = new ArrayList<>();
        for (Firestation station : databaseJson.getFirestations()) {
            if (station.getStation().equals(stationNumber)) {
                firestationAddress.add(station);
            }
        }
        return firestationAddress;
    }

    public List<Firestation> getFirestationByAddress(String address) {

        List<Firestation> stationNumber = new ArrayList<>();
        for (Firestation station : databaseJson.getFirestations()) {
            if (station.getStation().equalsIgnoreCase(address)) {
                stationNumber.add(station);
            }
        }
        return stationNumber;
    }


    public List<Persons> getPersonByAddress(String address) {
        List<Persons> personsCollection = new ArrayList<>();
        for (Persons person : databaseJson.getPersons()) {
            if (person.getAddress().equalsIgnoreCase(address)) {
                personsCollection.add(person);
            }
        }
        return personsCollection;

    }

    public List<Persons> getPersonByID(String firstName, String lastName) {
        List<Persons> personsCollection = new ArrayList<>();
        for (Persons person : databaseJson.getPersons()) {
            if (person.getFirstName().equalsIgnoreCase(firstName) && person.getLastName().equalsIgnoreCase(lastName)) {
                personsCollection.add(person);
            }
        }
        return personsCollection;
    }

    public List<Medicalrecords> getMedicalRecordByID(String firstName, String lastName) {
        List<Medicalrecords> medicalRecordsCollection = new ArrayList<>();
        for (Medicalrecords medicalrecords : databaseJson.getMedicalrecords()) {
            if (medicalrecords.getFirstName().equalsIgnoreCase(firstName) && medicalrecords.getLastName().equalsIgnoreCase(lastName)) {
                medicalRecordsCollection.add(medicalrecords);
            }
        }
        return medicalRecordsCollection;
    }


}
