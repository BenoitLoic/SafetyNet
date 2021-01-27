package com.benoit.safetyAlert.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Persons {
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    private String address;
    private String zip;
    private String city;
    private String phone;
    private String email;
    private Firestation firestation;
    private Medicalrecords medicalrecords;

    public Persons() {
    }

    public Persons(String firstName, String lastName, String address, String zip, String city, String phone, String email, Firestation firestation, Medicalrecords medicalrecords) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.zip = zip;
        this.city = city;
        this.phone = phone;
        this.email = email;
        this.firestation = firestation;
        this.medicalrecords = medicalrecords;
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


}
