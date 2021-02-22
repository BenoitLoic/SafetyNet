package com.safetynet.safetyalerts.services;

import com.safetynet.safetyalerts.dto.FirestationDto;
import com.safetynet.safetyalerts.dto.PersonInfo;
import com.safetynet.safetyalerts.model.Firestation;
import com.safetynet.safetyalerts.model.Persons;
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
