package com.benoit.safetyAlert.controller;

import com.benoit.safetyAlert.dto.FirestationDto;
import com.benoit.safetyAlert.dto.PersonInfo;
import com.benoit.safetyAlert.model.Firestation;
import com.benoit.safetyAlert.model.Persons;
import com.benoit.safetyAlert.services.FirestationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

/**
 * Implementation for FirestationController.
 */
@Validated
@RestController
public class FirestationControllerImpl implements FirestationController {

  private final FirestationService firestationService;

  @Autowired
  public FirestationControllerImpl(FirestationService firestationService) {
    this.firestationService = firestationService;
  }

  /**
   * Method for GET /phoneAlert endpoint.
   * this method will take one station number as parameter
   * and return all the phone number from user covered by this station.
   *
   * @param station the station number @NotBlank @RequestParam
   * @return the collection of user's phone number
   */
  @Override
  @GetMapping("/phoneAlert")
  @ResponseStatus(HttpStatus.OK)
  public Collection<Persons> phoneAlert(@RequestParam String station) {

    return firestationService.getPhoneNumber(station);
  }

  /**
   * Method for GET /firestation endpoint.
   * this method will take one station number as parameter
   * and return the information for each person covered by it.
   *
   * @param stationNumber the station number @NotBlank @RequestParam
   * @return the collection of user's information
   */
  @Override
  @GetMapping("/firestation")
  @ResponseStatus(HttpStatus.OK)
  public Collection<PersonInfo> fireStationCoverage(@RequestParam String stationNumber) {

    return firestationService.getPersonCoveredByFireStation(stationNumber);
  }

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
  @Override
  @GetMapping("/flood/stations")
  @ResponseStatus(HttpStatus.OK)
  public Collection<FirestationDto> floodStations(@RequestParam List<String> stations) {

    return firestationService.getFloodStations(stations);
  }

  /**
   * Method for POST /firestation.
   * this method add one firestation Mapping to DB.
   *
   * @param firestation the firestation to add, station and address are mandatory
   */
  @Override
  @PostMapping("/firestation")
  @ResponseStatus(HttpStatus.CREATED)
  public void createFirestation(@RequestBody @Valid Firestation firestation) {

    firestationService.createFirestation(firestation);
  }

  /**
   * Method for DELETE /firestation.
   * this method will remove the given firestation from DB.
   *
   * @param firestation the firestation to remove, address is mandatory
   */
  @Override
  @DeleteMapping("/firestation")
  @ResponseStatus(HttpStatus.OK)
  public void deleteFirestation(@RequestBody @Valid Firestation firestation) {

    firestationService.deleteFirestation(firestation);
  }

  /**
   * Method for PUT /firestation.
   * this method will update the station number of the firestation address.
   *
   * @param firestation the firestation to update, address and station are mandatory.
   */
  @Override
  @PutMapping("/firestation")
  @ResponseStatus(HttpStatus.CREATED)
  public void updateFirestation(@RequestBody @Valid Firestation firestation) {
    firestationService.updateFirestation(firestation);
  }
}
