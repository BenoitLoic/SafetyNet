package com.benoit.safetyAlert.model;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Model for Medicalrecords.
 */
public class Medicalrecords {

  @NotBlank
  private String firstName;
  @NotBlank
  private String lastName;
  private String birthdate;
  private List<String> medications = new ArrayList<>();
  private List<String> allergies = new ArrayList<>();

  public Medicalrecords() {
  }

  public Medicalrecords(String firstName, String lastName, String birthdate) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.birthdate = birthdate;
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

  public String getBirthdate() {
    return birthdate;
  }

  public void setBirthdate(String birthdate) {
    this.birthdate = birthdate;
  }

  public List<String> getMedications() {
    return medications;
  }

  public void setMedications(List<String> medications) {
    this.medications = medications;
  }

  public List<String> getAllergies() {
    return allergies;
  }

  public void setAllergies(List<String> allergies) {
    this.allergies = allergies;
  }

  /**
   * Override on equals to take only firstName and lastName.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Medicalrecords that = (Medicalrecords) o;
    return Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName);
  }

  /**
   * Override on hash to take only firstName and lastName.
   */
  @Override
  public int hashCode() {
    return Objects.hash(firstName, lastName);
  }

  @Override
  public String toString() {
    return "Medicalrecords{"
        + "firstName='"
        + firstName
        + '\''
        + ", lastName='"
        + lastName
        + '\''
        + ", birthdate='"
        + birthdate
        + '\''
        + ", medications="
        + medications
        + ", allergies="
        + allergies
        + '}';
  }
}
