package com.benoit.safetyAlert.services;

import java.util.List;

public interface IFireStationService {
    /**
     * This method take the station number to extract its addresses
     * @param station the station number
     * @return list of address
     */
    List<String> getFireStationAddress(String station);
    /**
     * This method take the addresses to find all station that cover it
     * @param address the address
     * @return list of station that cover this address
     */
    List<String> getFireStationStationNumber(String address);
    /**
     * This method take a list of station number to extract all the addresses
     * @param station list of station number
     * @return list of address
     */
    List<String> getFireStationAddress(List<String> station);
}
