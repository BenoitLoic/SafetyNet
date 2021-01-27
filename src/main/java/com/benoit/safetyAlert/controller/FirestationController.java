package com.benoit.safetyAlert.controller;

import com.benoit.safetyAlert.dto.PersonInfo;
import com.benoit.safetyAlert.model.Firestation;
import com.benoit.safetyAlert.model.Persons;

import java.util.Collection;
import java.util.List;

public interface FirestationController {

  Collection<Persons> phoneAlert(String station);

  Collection<PersonInfo> fireStationCoverage(String stationNumber);

  Collection<PersonInfo> floodStations(List<String> stations);

  void createFirestation(Firestation firestation);

  void deleteFirestation(Firestation firestation);
}
