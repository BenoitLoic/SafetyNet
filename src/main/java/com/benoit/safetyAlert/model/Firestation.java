package com.benoit.safetyAlert.model;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Firestation {

  @NotBlank private String station;
  @NotBlank private String address;
  private List<Persons> persons = new ArrayList<>();

  public List<Persons> getPersons() {
    return persons;
  }

  public void setPersons(List<Persons> persons) {
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
}
