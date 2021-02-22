package com.safetynet.safetyalerts.controller;

import com.safetynet.safetyalerts.dto.FirestationDto;
import com.safetynet.safetyalerts.dto.PersonInfo;
import com.safetynet.safetyalerts.model.Firestation;
import com.safetynet.safetyalerts.model.Persons;
import java.util.Collection;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * The interface FirestationController.
 */
public interface FirestationController {

  /**
   * Method for GET /phoneAlert endpoint.
   * this method will take one station number as parameter
   * and return all the phone number from user covered by this station.
   *
   * @param station the station number @NotBlank @RequestParam
   * @return the collection of user's phone number
   */
  Collection<Persons> phoneAlert(@NotBlank @RequestParam String station);

  /**
   * Method for GET /firestation endpoint.
   * this method will take one station number as parameter
   * and return the information for each person covered by it.
   *
   * @param stationNumber the station number @NotBlank @RequestParam
   * @return the collection of user's information
   */
  Collection<PersonInfo> fireStationCoverage(@NotBlank @RequestParam String stationNumber);

  /**
   * Method for GET /flood/stations.
   * this method will take a list of stations numbers as parameter
   * and return the collection of user's information covered by those fire stations
   * Collection must regroup user by address
   * and user's information are : name, phone, age, medication and allergies
   *
   * @param stations the list of station number @NotEmpty @RequestParam
   * @return the collection of user information
   */
  Collection<FirestationDto> floodStations(@NotEmpty @RequestParam List<String> stations);

  /**
   * Method for POST /firestation.
   * this method add one firestation Mapping to DB.
   *
   * @param firestation the firestation to add, station and address are mandatory
   */
  void createFirestation(@Valid @RequestBody Firestation firestation);

  /**
   * Method for DELETE /firestation.
   * this method will remove the given firestation from DB.
   *
   * @param firestation the firestation to remove, address is mandatory
   */
  void deleteFirestation(@Valid @RequestBody Firestation firestation);

  /**
   * Method for PUT /firestation.
   * this method will update the station number of the firestation address.
   *
   * @param firestation the firestation to update, address and station are mandatory.
   */
  void updateFirestation(@Valid @RequestBody Firestation firestation);
}
