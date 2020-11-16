package com.benoit.safetyAlert.model;

import java.util.ArrayList;
import java.util.List;

public class DatabaseJson {

    private List<Persons> persons = new ArrayList<Persons>();
    private List<Medicalrecords> medicalrecords = new ArrayList<Medicalrecords>();
    private List<Firestation> firestations = new ArrayList<Firestation>();

    public List<Persons> getPersons() {
        return persons;
    }

    public void setPersons(List<Persons> persons) {
        this.persons = persons;
    }

    public List<Medicalrecords> getMedicalrecords() {
        return medicalrecords;
    }

    public void setMedicalrecords(List<Medicalrecords> medicalrecords) {
        this.medicalrecords = medicalrecords;
    }

    public List<Firestation> getFirestations() {
        return firestations;
    }

    public void setFirestations(List<Firestation> firestations) {
        this.firestations = firestations;
    }
}
