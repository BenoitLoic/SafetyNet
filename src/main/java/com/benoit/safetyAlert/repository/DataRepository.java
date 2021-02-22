package com.benoit.safetyAlert.repository;

import com.benoit.safetyAlert.exceptions.DataRepositoryException;
import com.benoit.safetyAlert.model.DatabaseJson;
import com.benoit.safetyAlert.model.Firestation;
import com.benoit.safetyAlert.model.Medicalrecords;
import com.benoit.safetyAlert.model.Persons;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Data repository.
 * This class contains method to retrieve, write, link data from DB
 */
@Repository
public class DataRepository {
  private final JsonPath jsonPath;
  // pour log4j
  private static final Logger LOGGER = LogManager.getLogger(DataRepository.class);
  // c'est le fichier Json en memoire
  private DatabaseJson databaseJson;
  // cet obj va permettre de mapper du json en obj java
  private final ObjectMapper objectMapper = new ObjectMapper();

  private final String dataJson;
  // pour éviter de commit dans les tests
  private boolean commit = true;


  private List<Persons> persons = new ArrayList<>();
  private List<Medicalrecords> medicalrecords = new ArrayList<>();
  private List<Firestation> firestations = new ArrayList<>();


  /**
   * Instantiates a new Data repository.
   */
  public DataRepository(JsonPath jsonPath) {

    this.jsonPath = jsonPath;
    dataJson = jsonPath.getDataJson();
  }


  /**
   * This method initialize the repository.
   * this method Load the Json (DB) and call method linkDataBase to link the table in DB.
   * Can throw DataRepositoryException  if it can't access DB
   */
  @PostConstruct
  public void init() {

    try (InputStream inputStream = ClassLoader.getSystemResourceAsStream(dataJson)) {

      databaseJson = objectMapper.readValue(inputStream, DatabaseJson.class);
      linkDataBase();
      LOGGER.info("OK - file_open :" + dataJson);
    } catch (FileNotFoundException fnfe) {
      LOGGER.info("KO - file_not_found :" + dataJson);
      throw new DataRepositoryException("KO - file_not_found (init)", fnfe);
    } catch (IOException ioe) {
      LOGGER.info("KO - I/O error :" + dataJson);
      throw new DataRepositoryException("KO - I/O error (init)", ioe);
    }
  }

  /**
   * Method to write Data in DB (Json).
   * Can throw DataRepositoryException  if it can't access DB
   */
  public void commit() {
    if (commit) {

      //            on récupére le path du json
      URL url = ClassLoader.getSystemResource(dataJson);
      try (OutputStream outputStream = new FileOutputStream(url.getFile())) {

        //  ecrire sur le fichier json avec formatage
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(outputStream, databaseJson);
        LOGGER.info("OK - fichier_json_mis_a_jour :" + dataJson);
      } catch (FileNotFoundException fnfe) {

        LOGGER.info("KO - file_not_found :" + dataJson);
        throw new DataRepositoryException("KO - file_not_found (commit)", fnfe);

      } catch (IOException ioe) {

        LOGGER.info("KO - I/O error :" + dataJson);
        throw new DataRepositoryException("KO - I/O error (commit)", ioe);
      }
    }
  }


  public void setCommit(boolean commit) {
    this.commit = commit;
  }

  /**
   * Gets the list of persons saved in DB.
   *
   * @return the persons in DB
   */
  public List<Persons> getPersons() {
    return persons;
  }

  /**
   * Gets the list of medicalrecords saved in DB.
   *
   * @return the medicalrecords in DB
   */
  public List<Medicalrecords> getMedicalrecords() {
    return medicalrecords;
  }

  /**
   * Gets the list of firestations saved in DB.
   *
   * @return the firestations in DB
   */
  public List<Firestation> getFirestations() {
    return firestations;
  }

  /**
   * This method will link all table from DB for convenience.
   * person -> firestations
   * medicalrecords + firestation -> person
   */
  public void linkDataBase() {

    persons = databaseJson.getPersons();
    medicalrecords = databaseJson.getMedicalrecords();
    firestations = databaseJson.getFirestations();

    for (Persons person : persons) {
      for (Medicalrecords medicalrecord : medicalrecords) {
        if (person.getFirstName().equalsIgnoreCase(medicalrecord.getFirstName())
            && person.getLastName().equalsIgnoreCase(medicalrecord.getLastName())) {
          person.setMedicalrecords(medicalrecord);
        }
      }
      for (Firestation firestation : firestations) {
        if (person.getAddress().equalsIgnoreCase(firestation.getAddress())) {
          person.setFirestation(firestation);
          firestation.getPersons().add(person);
        }
      }
    }


  }


}
