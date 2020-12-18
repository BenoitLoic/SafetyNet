package com.benoit.safetyAlert.dao;

import com.benoit.safetyAlert.model.Firestation;

public interface FirestationDao {

  boolean createFirestation(Firestation firestation);

  boolean updateFirestation(Firestation firestation);

  boolean deleteFirestation(Firestation firestation);
}
