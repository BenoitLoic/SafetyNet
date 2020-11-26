package com.benoit.safetyAlert.services;

import java.util.List;

public interface IFirestationService {

    List<String> getFirestationAddress(String station);

    List<String> getFirestationStation(String address);
}
