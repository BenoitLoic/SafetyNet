package com.safetynet.safetyalerts.dao;

import com.safetynet.safetyalerts.model.Firestation;
import com.safetynet.safetyalerts.repository.DataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation of FirestationDao.
 * Contains method to create / update / delete Firestation mapping in DB.
 */
@Service
public class FirestationDaoImpl implements FirestationDao {

  private final DataRepository dataRepository;

  /**
   * Instantiates a new Firestation dao.
   *
   * @param dataRepository the repository
   */
  @Autowired
  public FirestationDaoImpl(DataRepository dataRepository) {
    this.dataRepository = dataRepository;
  }

  @Override
  public boolean createFirestation(Firestation firestation) {

    dataRepository.getFirestations().add(firestation);
    dataRepository.commit();
    return true;
  }

  @Override
  public boolean deleteFirestation(Firestation firestation) {
    dataRepository.getFirestations().remove(firestation);
    dataRepository.commit();
    return true;
  }

  @Override
  public boolean updateFirestation(Firestation firestation) {

    //suppression des firestations avec la mm address
    dataRepository.getFirestations()
        .removeIf(firestationToUpdate ->
            firestationToUpdate.getAddress().equalsIgnoreCase(firestation.getAddress()));
    //ajout de la station
    dataRepository.getFirestations().add(firestation);
    dataRepository.commit();
    return true;
  }
}
