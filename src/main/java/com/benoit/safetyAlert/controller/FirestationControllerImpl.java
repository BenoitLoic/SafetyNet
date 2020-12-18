package com.benoit.safetyAlert.controller;

import com.benoit.safetyAlert.model.Firestation;
import com.benoit.safetyAlert.services.FirestationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
public class FirestationControllerImpl implements FirestationController {

  @Autowired private FirestationService firestationService;

  @Override
  @GetMapping("/phoneAlert")
  public Collection<String> phoneAlert(@RequestParam String station) {

    return firestationService.getPhoneNumber(station);
  }

  @Override
  @GetMapping("/firestation")
  public Collection<Object> fireStationCoverage(@RequestParam String stationNumber) {

    return firestationService.getPersonCoveredByFireStation(stationNumber);
  }

  @Override
  @GetMapping("/flood/stations")
  public Collection<Object> floodStations(@RequestParam List<String> stations) {

    return firestationService.getFloodStations(stations);
  }

  @Override
  @PostMapping("/firestations")
  public String addFirestation(@RequestBody Firestation firestation){
//    return firestationService.addFirestation(firestation);
    return null;
  }

}
