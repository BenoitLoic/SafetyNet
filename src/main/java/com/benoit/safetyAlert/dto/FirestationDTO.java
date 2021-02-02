package com.benoit.safetyAlert.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class FirestationDTO {

  private String station;
  private String address;
  private List<PersonInfo> personInfos = new ArrayList<>();

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

  public List<PersonInfo> getPersonInfos() {
    return personInfos;
  }

  public void setPersonInfos(List<PersonInfo> personInfos) {
    this.personInfos = personInfos;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    FirestationDTO that = (FirestationDTO) o;
    return Objects.equals(station, that.station) && Objects.equals(address, that.address);
  }

  @Override
  public int hashCode() {
    return Objects.hash(station, address);
  }

  public static Comparator<FirestationDTO> comparator = new Comparator<FirestationDTO>() {
    @Override
    public int compare(FirestationDTO o1, FirestationDTO o2) {
      return o1.getAddress().compareTo(o2.getAddress());
    }
  };


}
