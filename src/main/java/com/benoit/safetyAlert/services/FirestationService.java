package com.benoit.safetyAlert.services;

import com.benoit.safetyAlert.dto.FirestationDto;
import com.benoit.safetyAlert.dto.PersonInfo;
import com.benoit.safetyAlert.model.Firestation;
import com.benoit.safetyAlert.model.Persons;

import java.util.Collection;
import java.util.List;

/**
 * Interface for FirestationService.
 */
public interface FirestationService {


  /**
   * Gets phone number.
   *
   * @param address the address
   * @return the phone number
   */
  Collection<Persons> getPhoneNumber(String address);

  /**
   * Gets person covered by firestation.
   *
   * @param address the address
   * @return the person covered by firestation
   */
  Collection<PersonInfo> getPersonCoveredByFireStation(String address);

  /**
   * Gets flood stations.
   *
   * @param station the station
   * @return the flood stations
   */
  Collection<FirestationDto> getFloodStations(List<String> station);

  boolean createFirestation(Firestation firestation);

  boolean deleteFirestation(Firestation firestation);

  boolean updateFirestation(Firestation firestation);
}
