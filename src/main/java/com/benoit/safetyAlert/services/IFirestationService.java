package com.benoit.safetyAlert.services;

import com.benoit.safetyAlert.model.Firestation;

import java.util.List;

public interface IFirestationService {

    List<String> getFirestationAddress(String station);

}
