package com.safetynet.safetyalerts.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.validation.constraints.NotBlank;

/**
 * Model for Firestation.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Firestation {


  @NotBlank
  private String station;

  @NotBlank
  private String address;
  @JsonIgnore
  private List<Persons> persons = new ArrayList<>();

  public Firestation() {
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
    if (this == o) {
      return true;
    }
    if (!(o instanceof Firestation)) {
      return false;
    }
    Firestation that = (Firestation) o;
    return Objects.equals(getStation(), that.getStation())
        && Objects.equals(getAddress(), that.getAddress());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getStation(), getAddress());
  }


}
