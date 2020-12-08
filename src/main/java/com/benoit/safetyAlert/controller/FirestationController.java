package com.benoit.safetyAlert.controller;

import java.util.Collection;
import java.util.List;

public interface FirestationController {

    Collection<String> phoneAlert(String station);

    Collection<Object> fireStationCoverage(String stationNumber);

    Collection<Object> floodStations(List<String> stations);
}
