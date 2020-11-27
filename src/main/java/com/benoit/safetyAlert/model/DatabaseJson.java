package com.benoit.safetyAlert.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

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

    @Override
    public String toString() {
        return "{" +
                "persons=" + persons +
                ", medicalrecords=" + medicalrecords +
                ", firestations=" + firestations +
                '}';
    }
}
