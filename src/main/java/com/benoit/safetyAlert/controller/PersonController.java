package com.benoit.safetyAlert.controller;

import com.benoit.safetyAlert.dto.PersonInfo;
import com.benoit.safetyAlert.model.Persons;

import java.util.Collection;

public interface PersonController {

  Collection<Persons> communityEmail(String city);

  Collection<PersonInfo> fire(String address);

  Collection<PersonInfo> childAlert(String address);

  void createPerson(Persons person);

  void deletePerson(Persons person);

  void updatePerson(Persons person);
}
