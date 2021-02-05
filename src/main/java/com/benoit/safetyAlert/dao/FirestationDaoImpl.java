package com.benoit.safetyAlert.dao;

import com.benoit.safetyAlert.model.Firestation;
import com.benoit.safetyAlert.repository.DataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FirestationDaoImpl implements FirestationDao {

  private final DataRepository dataRepository;

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
