package com.benoit.safetyAlert.controller;

import com.benoit.safetyAlert.model.Medicalrecords;

import java.util.Collection;

public interface MedicalRecordsController {

  Collection<Object> personInfo(String firstName, String lastName);

  void createMedicalRecord(Medicalrecords medicalrecord);

    void deleteMedicalRecord(Medicalrecords medicalrecords);
}
