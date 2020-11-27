package com.benoit.safetyAlert.services;

import com.benoit.safetyAlert.model.Medicalrecords;
import com.benoit.safetyAlert.repository.DataRepository;
import com.benoit.safetyAlert.utility.CalculateAge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MedicalRecordsService {

    @Autowired
    DataRepository dataRepository;


    public int getAge(String firstName, String lastName) {

        List<Medicalrecords> personAge = dataRepository.getMedicalRecordByID(firstName, lastName);
        for (Medicalrecords medicalrecords : personAge) {
            return CalculateAge.calculateAge(medicalrecords.getBirthdate());
        }
        return 0;
    }


}
