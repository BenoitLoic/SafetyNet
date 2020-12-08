package com.benoit.safetyAlert.repository;

import com.benoit.safetyAlert.exceptions.DataRepositoryException;
import com.benoit.safetyAlert.model.DatabaseJson;
import com.benoit.safetyAlert.model.Firestation;
import com.benoit.safetyAlert.model.Medicalrecords;
import com.benoit.safetyAlert.model.Persons;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Repository
public class DataRepository {
  // cet obj va permettre de mapper du json en obj java
  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
  // pour log4j
  private static final Logger logger = LogManager.getLogger(DataRepository.class);
  // c'est le fichier Json en memoire
  private static DatabaseJson databaseJson;
  private final String DATA_JSON = "data.json";
  // pour éviter de commit dans les tests
  private boolean commit = true;

  public DataRepository() {
    this.init();
  }

  public void init() {
    try (InputStream inputStream = ClassLoader.getSystemResourceAsStream(DATA_JSON)) {

      databaseJson = OBJECT_MAPPER.readerFor(DatabaseJson.class).readValue(inputStream);
      logger.info("OK - file_open :" + DATA_JSON);
    } catch (FileNotFoundException fnfe) {
      logger.info("KO - file_not_found :" + DATA_JSON);
      throw new DataRepositoryException("KO - file_not_found", fnfe);
    } catch (IOException ioe) {
      logger.info("KO - I/O error :" + DATA_JSON);
      throw new DataRepositoryException("KO - I/O error", ioe);
    }
  }

  public void commit() {
    if (commit) {

      //            on récupére le path du json
      URL url = ClassLoader.getSystemResource(DATA_JSON);
      try (OutputStream outputStream = new FileOutputStream(url.getFile())) {

        //  ecrire sur le fichier json avec formatage
        OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValue(outputStream, databaseJson);
        logger.info("OK - fichier_json_mis_a_jour :" + DATA_JSON);
      } catch (FileNotFoundException fnfe) {

        logger.info("KO - file_not_found :" + DATA_JSON);
        throw new DataRepositoryException("KO - file_not_found", fnfe);

      } catch (IOException ioe) {

        logger.info("KO - I/O error :" + DATA_JSON);
        throw new DataRepositoryException("KO - I/O error", ioe);
      }
    }
  }

  public void setCommit(boolean commit) {
    this.commit = commit;
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

  // recupere les firestations avec le numero de station et renvoi une list
  public List<Firestation> getFirestationByStationNumber(String stationNumber) {

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
      if (station.getAddress().equalsIgnoreCase(address)) {
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
      if (person.getFirstName().equalsIgnoreCase(firstName)
          && person.getLastName().equalsIgnoreCase(lastName)) {
        personsCollection.add(person);
      }
    }
    return personsCollection;
  }

  public List<Medicalrecords> getMedicalRecordByID(String firstName, String lastName) {
    List<Medicalrecords> medicalRecordsCollection = new ArrayList<>();
    for (Medicalrecords medicalrecords : databaseJson.getMedicalrecords()) {
      if (medicalrecords.getFirstName().equalsIgnoreCase(firstName)
          && medicalrecords.getLastName().equalsIgnoreCase(lastName)) {
        medicalRecordsCollection.add(medicalrecords);
      }
    }
    return medicalRecordsCollection;
  }
}
