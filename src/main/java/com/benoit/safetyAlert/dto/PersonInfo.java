package com.benoit.safetyAlert.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PersonInfo {

  @NotBlank
  private String firstName;
  @NotBlank private String lastName;
  private String address;

  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  private int age;

  private String phone;
  private String email;
  private List<String> medication = new ArrayList<>();
  private List<String> allergies = new ArrayList<>();
  private String station;

  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  private List<PersonInfo> family = new ArrayList<>();

  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  private int numberOfChild;

  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  private int numberOfAdult;

  public List<PersonInfo> getFamily() {
    return family;
  }

  public void setFamily(List<PersonInfo> family) {
    this.family = family;
  }

  public int getNumberOfChild() {
    return numberOfChild;
  }

  public void setNumberOfChild(int numberOfChild) {
    this.numberOfChild = numberOfChild;
  }

  public int getNumberOfAdult() {
    return numberOfAdult;
  }

  public void setNumberOfAdult(int numberOfAdult) {
    this.numberOfAdult = numberOfAdult;
  }

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

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
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

  public List<String> getMedication() {
    return medication;
  }

  public void setMedication(List<String> medication) {
    this.medication = medication;
  }

  public List<String> getAllergies() {
    return allergies;
  }

  public void setAllergies(List<String> allergies) {
    this.allergies = allergies;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof PersonInfo)) {
      return false;
    }
    PersonInfo that = (PersonInfo) o;
    return Objects.equals(getFirstName(),
            that.getFirstName()) && Objects.equals(getLastName(),
            that.getLastName());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getFirstName(), getLastName());
  }
}