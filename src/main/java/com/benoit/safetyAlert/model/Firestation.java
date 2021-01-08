package com.benoit.safetyAlert.model;

import java.util.Objects;

public class Firestation {

  private String station;
  private String address;

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
    return Objects.equals(getStation(), that.getStation()) && Objects.equals(getAddress(), that.getAddress());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getStation(), getAddress());
  }
}
