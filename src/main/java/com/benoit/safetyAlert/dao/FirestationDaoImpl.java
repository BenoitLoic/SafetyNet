package com.benoit.safetyAlert.dao;

import com.benoit.safetyAlert.model.Firestation;
import com.benoit.safetyAlert.repository.DataRepository;

public class FirestationDaoImpl implements FirestationDao {


  @Override
  public boolean createFirestation(Firestation firestation) {
    DataRepository dataRepository = new DataRepository();
    dataRepository.setCommit(true);
    dataRepository.commit();
    return true;
  }

  @Override
  public boolean updateFirestation(Firestation firestation) {
    return false;
  }

  @Override
  public boolean deleteFirestation(Firestation firestation) {
    return false;
  }
}
