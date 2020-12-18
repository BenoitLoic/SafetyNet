package com.benoit.safetyAlert.controller;

import com.benoit.safetyAlert.model.Persons;

import java.util.Collection;

public interface PersonController {

  Collection<String> communityEmail(String city);

  Collection<Object> fire(String address);

  Collection<Object> childAlert(String address);

  void createPerson(Persons person);
}
