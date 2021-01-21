package com.benoit.safetyAlert.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.constraints.NotBlank;
import java.util.Objects;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Persons {
  @NotBlank private String firstName;
  @NotBlank private String lastName;

  private String address;
  private String zip;
  private String city;
  private String phone;
  private String email;
  private Firestation firestation;
  private Medicalrecords medicalrecords;

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getZip() {
    return zip;
  }

  public void setZip(String zip) {
    this.zip = zip;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Firestation getFirestation() {
    return firestation;
  }

  public void setFirestation(Firestation firestation) {
    this.firestation = firestation;
  }

  public Medicalrecords getMedicalrecords() {
    return medicalrecords;
  }

  public void setMedicalrecords(Medicalrecords medicalrecords) {
    this.medicalrecords = medicalrecords;
  }

  @Override
  public String toString() {
    return "Persons{"
        + "firstName='"
        + firstName
        + '\''
        + ", lastName='"
        + lastName
        + '\''
        + ", address='"
        + address
        + '\''
        + ", zip='"
        + zip
        + '\''
        + ", city='"
        + city
        + '\''
        + ", phone='"
        + phone
        + '\''
        + ", email='"
        + email
        + '\''
        + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Persons)) return false;
    Persons persons = (Persons) o;
    return Objects.equals(getFirstName(), persons.getFirstName()) && Objects.equals(getLastName(), persons.getLastName()) && Objects.equals(getPhone(), persons.getPhone()) && Objects.equals(getEmail(), persons.getEmail());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getFirstName(), getLastName(), getPhone(), getEmail());
  }
}
