package com.benoit.safetyAlert.controller;

import com.benoit.safetyAlert.model.Firestation;

import java.util.Collection;
import java.util.List;

public interface FirestationController {

  Collection<String> phoneAlert(String station);

  Collection<Object> fireStationCoverage(String stationNumber);

  Collection<Object> floodStations(List<String> stations);

  void createFirestation(Firestation firestation);

  void deleteFirestation(Firestation firestation);
}
