package com.benoit.safetyAlert.controller;

import com.benoit.safetyAlert.model.Firestation;
import com.benoit.safetyAlert.services.FirestationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

@RestController
public class FirestationControllerImpl implements FirestationController {

  @Autowired private FirestationService firestationService;

  @Override
  @GetMapping("/phoneAlert")
  @ResponseStatus(HttpStatus.OK)
  public Collection<String> phoneAlert(@RequestParam String station) {

    return firestationService.getPhoneNumber(station);
  }

  @Override
  @GetMapping("/firestation")
  @ResponseStatus(HttpStatus.OK)
  public Collection<Object> fireStationCoverage(@RequestParam String stationNumber) {

    return firestationService.getPersonCoveredByFireStation(stationNumber);
  }

  @Override
  @GetMapping("/flood/stations")
  @ResponseStatus(HttpStatus.OK)
  public Collection<Object> floodStations(@RequestParam List<String> stations) {

    return firestationService.getFloodStations(stations);
  }

  @Override
  @PostMapping("/firestation")
  @ResponseStatus(HttpStatus.CREATED)
  public void createFirestation(@RequestBody @Valid Firestation firestation) {

    firestationService.createFirestation(firestation);
  }

  @Override
  @DeleteMapping("/firestation")
  @ResponseStatus(HttpStatus.OK)
  public void deleteFirestation(@RequestBody @Valid Firestation firestation) {

    firestationService.deleteFirestation(firestation);
  }
}
