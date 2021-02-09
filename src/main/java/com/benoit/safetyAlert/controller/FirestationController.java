package com.benoit.safetyAlert.controller;

import com.benoit.safetyAlert.dto.FirestationDto;
import com.benoit.safetyAlert.dto.PersonInfo;
import com.benoit.safetyAlert.model.Firestation;
import com.benoit.safetyAlert.model.Persons;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Collection;
import java.util.List;

public interface FirestationController {

  Collection<Persons> phoneAlert(@NotBlank @RequestParam String station);

  Collection<PersonInfo> fireStationCoverage(@NotBlank @RequestParam String stationNumber);

  Collection<FirestationDto> floodStations(@NotEmpty @RequestParam List<String> stations);

  void createFirestation(@Valid @RequestBody Firestation firestation);

  void deleteFirestation(@Valid @RequestBody Firestation firestation);

  void updateFirestation(@Valid @RequestBody Firestation firestation);
}
