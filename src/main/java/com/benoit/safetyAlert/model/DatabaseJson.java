package com.benoit.safetyAlert.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"persons", "firestations", "medicalrecords"})
public class DatabaseJson {

    @JsonProperty("persons")
    private List<Persons> persons = new ArrayList<Persons>();

    @JsonProperty("medicalrecords")
    private List<Medicalrecords> medicalrecords = new ArrayList<Medicalrecords>();

    @JsonProperty("firestations")
    private List<Firestation> firestations = new ArrayList<Firestation>();

    @JsonProperty("persons")
    public List<Persons> getPersons() {
        return persons;
    }

    @JsonProperty("persons")
    public void setPersons(List<Persons> persons) {
        this.persons = persons;
    }

    @JsonProperty("medicalrecords")
    public List<Medicalrecords> getMedicalrecords() {
        return medicalrecords;
    }

    @JsonProperty("medicalrecords")
    public void setMedicalrecords(List<Medicalrecords> medicalrecords) {
        this.medicalrecords = medicalrecords;
    }

    @JsonProperty("firestations")
    public List<Firestation> getFirestations() {
        return firestations;
    }

    @JsonProperty("firestations")
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
