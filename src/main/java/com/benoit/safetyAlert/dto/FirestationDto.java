package com.benoit.safetyAlert.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * Data Transfer Object for firestation + personInfo.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FirestationDto {

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


  public static final Comparator<FirestationDto> comparator = new Comparator<FirestationDto>() {
    @Override
    public int compare(FirestationDto o1, FirestationDto o2) {
      return o1.getAddress().compareTo(o2.getAddress());
    }
  };

  @Override
  public int hashCode() {
    return Objects.hash(station, address);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    FirestationDto that = (FirestationDto) o;
    return Objects.equals(station, that.station) && Objects.equals(address, that.address);
  }


}
