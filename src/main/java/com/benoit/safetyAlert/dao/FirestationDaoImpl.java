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

        dataRepository.getDatabaseJson().getFirestations().add(firestation);

        dataRepository.commit();
        return true;
    }

    @Override
    public boolean updateFirestation(Firestation firestation) {
        deleteFirestation(firestation);
        return createFirestation(firestation);
    }

    @Override
    public boolean deleteFirestation(Firestation firestation) {
        dataRepository.getDatabaseJson().getFirestations().remove(firestation);
        dataRepository.commit();
        return true;
    }
}
