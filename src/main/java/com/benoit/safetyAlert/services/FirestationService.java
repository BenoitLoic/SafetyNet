package com.benoit.safetyAlert.services;

import com.benoit.safetyAlert.model.Firestation;
import com.benoit.safetyAlert.repository.DataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FirestationService implements IFirestationService {

    @Autowired
    DataRepository dataRepository;

    //extract address from firestation
    public List<String> getFirestationAddress(String station) {

        List<Firestation> firestations = dataRepository.getFirestationByStationNumber(station);
        List<String> listOfFirestationAddress = new ArrayList<>();
        for (Firestation firestation : firestations) {
            listOfFirestationAddress.add(firestation.getAddress());
        }

        return listOfFirestationAddress;
    }
    //get station number with its address
    public List<String> getFirestationStation(String address) {

        List<Firestation> firestationsAddress = dataRepository.getFirestationByStationNumber(address);
        List<String> listOfFirestationStation = new ArrayList<>();
        for (Firestation firestation : firestationsAddress) {
            listOfFirestationStation.add(firestation.getStation());
        }

        return listOfFirestationStation;
    }
}
