package com.benoit.safetyAlert.model;

import java.util.ArrayList;
import java.util.List;

public class DatabaseJson {

  private List<Persons> persons = new ArrayList<>();

  private List<Medicalrecords> medicalrecords = new ArrayList<>();

  private List<Firestation> firestations = new ArrayList<>();

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
  public int hashCode() {
    return getPersons().hashCode();
  }
}
