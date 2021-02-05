package com.benoit.safetyAlert.controller;

import com.benoit.safetyAlert.dto.FirestationDto;
import com.benoit.safetyAlert.dto.PersonInfo;
import com.benoit.safetyAlert.model.Firestation;
import com.benoit.safetyAlert.model.Persons;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.List;

public interface FirestationController {

  Collection<Persons> phoneAlert(@NotBlank String station);

  Collection<PersonInfo> fireStationCoverage(@NotBlank String stationNumber);

  Collection<FirestationDto> floodStations(List<String> stations);

  void createFirestation(@Valid Firestation firestation);

  void deleteFirestation(@Valid Firestation firestation);

  void updateFirestation(@Valid Firestation firestation);
}
