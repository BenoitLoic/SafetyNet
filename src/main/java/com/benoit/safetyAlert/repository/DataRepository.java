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
  // pour log4j
  private static final Logger LOGGER = LogManager.getLogger(DataRepository.class);
  // c'est le fichier Json en memoire
  private static DatabaseJson databaseJson;
  // cet obj va permettre de mapper du json en obj java
  private final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
  private final String dataJson = "data.json";
  // pour éviter de commit dans les tests
  private boolean commit = true;


  private List<Persons> persons = new ArrayList<>();
  private List<Medicalrecords> medicalrecords = new ArrayList<>();
  private List<Firestation> firestations = new ArrayList<>();


  public DataRepository() {
    init();
  }

  public DatabaseJson getDatabaseJson() {
    return DataRepository.databaseJson;
  }

  public void init() {
    try (InputStream inputStream = ClassLoader.getSystemResourceAsStream(dataJson)) {

      databaseJson = OBJECT_MAPPER.readValue(inputStream, DatabaseJson.class);
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

  public void commit() {
    if (commit) {

      //            on récupére le path du json
      URL url = ClassLoader.getSystemResource(dataJson);
      try (OutputStream outputStream = new FileOutputStream(url.getFile())) {

        //  ecrire sur le fichier json avec formatage
        OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValue(outputStream, databaseJson);
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

  public List<Persons> getPersons() {
    return persons;
  }

  public List<Medicalrecords> getMedicalrecords() {
    return medicalrecords;
  }

  public List<Firestation> getFirestations() {
    return firestations;
  }

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
