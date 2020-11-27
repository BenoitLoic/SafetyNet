package com.benoit.safetyAlert.services;

import com.benoit.safetyAlert.model.Firestation;
import com.benoit.safetyAlert.repository.DataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

/**
 * This class contains some method to extract fire station data from DataRepository.
 */
@Service
public class FireStationService implements IFireStationService {

    /**
     * The Data repository.
     */
    @Autowired
    DataRepository dataRepository;


    /**
     * This method take the station number to extract its addresses
     * @param station the station number
     * @return list of address
     */
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
     * @param address the address
     * @return list of station that cover this address
     */
    public List<String> getFireStationStationNumber(String address) {

        List<Firestation> fireStationsAddress = dataRepository.getFireStationByStationNumber(address);
        List<String> listOfFireStationNumber = new ArrayList<>();
        for (Firestation fireStation : fireStationsAddress) {
            listOfFireStationNumber.add(fireStation.getStation());
        }

        return listOfFireStationNumber;
    }

    /**
     * This method take a list of station number to extract all the addresses
     * @param station list of station number
     * @return list of address
     */
    @Override
    public List<String> getFireStationAddress(List<String> station) {
        List<String> listOfFireStationAddress = new ArrayList<>();

        for (int i = 0; i < station.size(); i++) {
            List<Firestation> fireStations = dataRepository.getFireStationByStationNumber(String.valueOf(i));
            for (Firestation fireStation : fireStations) {
                listOfFireStationAddress.add(fireStation.getAddress());
            }
        }
        return listOfFireStationAddress;


    }

}
