package com.benoit.safetyAlert.services;

import com.benoit.safetyAlert.model.Firestation;
import com.benoit.safetyAlert.repository.DataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/** This class contains some method to extract fire station data from DataRepository. */
@Service
public class FireStationService implements IFireStationService {

  /** The Data repository. */
  @Autowired DataRepository dataRepository;

  /**
   * This method take the station number to extract its addresses
   *
   * @param station the station number
   * @return list of address
   */
  @Override
  public List<String> getFireStationAddress(String station) {

    List<Firestation> fireStations = dataRepository.getFireStationByStationNumber(station);
    List<String> listOfFireStationAddress = new ArrayList<>();
    for (Firestation fireStation : fireStations) {
      listOfFireStationAddress.add(fireStation.getAddress());
    }

    return listOfFireStationAddress;
  }

  /**
   * This method take the addresses to find all station that cover it
   *
   * @param address the address
   * @return list of station that cover this address
   */
  @Override
  public List<String> getFireStationStationNumber(String address) {

    List<Firestation> fireStationsAddress = dataRepository.getFirestationByAddress(address);
    List<String> listOfFireStationNumber = new ArrayList<>();
    for (Firestation fireStation : fireStationsAddress) {
      listOfFireStationNumber.add(fireStation.getStation());
    }

    return listOfFireStationNumber;
  }

  /**
   * This method take a list of station number to extract all the addresses
   *
   * @param stationNumber list of station number
   * @return list of address
   */
  @Override
  public Collection<String> getFireStationAddress(List<String> stationNumber) {
    Set<String> stationNumberNoDuplicate = new HashSet<>(stationNumber);
    Collection<String> listOfFireStationAddress = new HashSet<>();
    for (String station : stationNumberNoDuplicate) {
      List<Firestation> fireStations = dataRepository.getFireStationByStationNumber(station);
      for (Firestation firestation : fireStations) {
        listOfFireStationAddress.add(firestation.getAddress());
      }
    }
    return listOfFireStationAddress;
  }
}
