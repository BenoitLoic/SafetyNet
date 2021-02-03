package com.benoit.safetyAlert.controller;

import com.benoit.safetyAlert.dto.FirestationDTO;
import com.benoit.safetyAlert.dto.PersonInfo;
import com.benoit.safetyAlert.model.Firestation;
import com.benoit.safetyAlert.model.Persons;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface FirestationController {

  Collection<Persons> phoneAlert(String station);

  Collection<PersonInfo> fireStationCoverage(String stationNumber);

  Collection<FirestationDTO> floodStations(List<String> stations);

  void createFirestation(Firestation firestation);

  void deleteFirestation(Firestation firestation);

  void updateFirestation(Firestation firestation);
}
