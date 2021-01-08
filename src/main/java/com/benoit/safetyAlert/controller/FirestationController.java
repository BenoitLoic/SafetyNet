package com.benoit.safetyAlert.controller;

import com.benoit.safetyAlert.model.Firestation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Collection;
import java.util.List;

public interface FirestationController {

  Collection<String> phoneAlert(String station);

  Collection<Object> fireStationCoverage(String stationNumber);

  Collection<Object> floodStations(List<String> stations);


  void addFirestation( Firestation firestation);
}
