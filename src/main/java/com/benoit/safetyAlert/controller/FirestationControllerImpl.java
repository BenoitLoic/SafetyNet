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
import javax.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.List;

@Validated
@RestController
public class FirestationControllerImpl implements FirestationController {

  private final FirestationService firestationService;

  @Autowired
  public FirestationControllerImpl(FirestationService firestationService) {
    this.firestationService = firestationService;
  }

  @Override
  @GetMapping("/phoneAlert")
  @ResponseStatus(HttpStatus.OK)
  public Collection<Persons> phoneAlert(@RequestParam String station) {

    return firestationService.getPhoneNumber(station);
  }

  @Override
  @GetMapping("/firestation")
  @ResponseStatus(HttpStatus.OK)
  public Collection<PersonInfo> fireStationCoverage(@RequestParam String stationNumber) {

    return firestationService.getPersonCoveredByFireStation(stationNumber);
  }

  @Override
  @GetMapping("/flood/stations")
  @ResponseStatus(HttpStatus.OK)
  public Collection<FirestationDto> floodStations(@RequestParam List<String> stations) {

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

  @Override
  @PutMapping("/firestation")
  @ResponseStatus(HttpStatus.CREATED)
  public void updateFirestation(@RequestBody @Valid Firestation firestation) {
    firestationService.updateFirestation(firestation);
  }
}
