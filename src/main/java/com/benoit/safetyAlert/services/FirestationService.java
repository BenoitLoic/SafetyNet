package com.benoit.safetyAlert.services;

import com.benoit.safetyAlert.model.Firestation;

import java.util.Collection;
import java.util.List;

public interface FirestationService {
  /**
   * This method take the station number to extract its addresses
   *
   * @param station the station number
   * @return list of address
   */
  Collection<String> getFirestationAddress(String station);

  /**
   * This method take the addresses to find all station that cover it
   *
   * @param address the address
   * @return list of station that cover this address
   */
  Collection<String> getFirestationNumber(String address);

  /**
   * This method take a list of station number to extract all the addresses
   *
   * @param station list of station number
   * @return list of address
   */
  Collection<String> getFirestationAddress(List<String> station);

  /**
   * Gets phone number.
   *
   * @param address the address
   * @return the phone number
   */
  Collection<String> getPhoneNumber(String address);

  /**
   * Gets person covered by firestation.
   *
   * @param address the address
   * @return the person covered by firestation
   */
  Collection<Object> getPersonCoveredByFireStation(String address);

  /**
   * Gets flood stations.
   *
   * @param station the station
   * @return the flood stations
   */
  Collection<Object> getFloodStations(List<String> station);

  boolean createFirestation(Firestation firestation);

  boolean deleteFirestation(Firestation firestation);
}
