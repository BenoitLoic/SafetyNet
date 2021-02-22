package com.safetynet.safetyalerts.dao;

import com.safetynet.safetyalerts.model.Firestation;

/**
 * Interface FirestationDao.
 * Contains method to create / update / delete Firestation mapping in DB.
 */
public interface FirestationDao {

  boolean createFirestation(Firestation firestation);

  boolean updateFirestation(Firestation firestation);

  boolean deleteFirestation(Firestation firestation);
}
