package com.benoit.safetyAlert.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Firestation {

  @NotBlank
  private String station;
  @NotBlank
  private String address;
  @JsonIgnore
  private List<Persons> persons = new ArrayList<>();

  public Firestation() {
  }

  public Firestation(String station, String address, List<Persons> persons) {
    this.station = station;
    this.address = address;
    this.persons = persons;
  }

  public String getStation() {
    return station;
  }

  public void setStation(String station) {
    this.station = station;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public List<Persons> getPersons() {
    return persons;
  }

  public void setPersons(List<Persons> persons) {
    this.persons = persons;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Firestation)) return false;
    Firestation that = (Firestation) o;
    return Objects.equals(getStation(), that.getStation())
        && Objects.equals(getAddress(), that.getAddress());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getStation(), getAddress());
  }

  @Override
  public String toString() {
    return "Firestation{" +
        "station='" + station + '\'' +
        ", address='" + address + '\'' +
        ", persons=" + persons +
        '}';
  }
}
