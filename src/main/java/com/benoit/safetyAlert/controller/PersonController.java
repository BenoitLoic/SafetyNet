package com.benoit.safetyAlert.controller;

import com.benoit.safetyAlert.dto.PersonInfo;
import com.benoit.safetyAlert.model.Persons;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.Collection;

public interface PersonController {

  Collection<Persons> communityEmail(@NotBlank String city);

  Collection<PersonInfo> fire(@NotBlank String address);

  Collection<PersonInfo> childAlert(@NotBlank String address);

  void createPerson(@Valid Persons person);

  void deletePerson(@Valid Persons person);

  void updatePerson(@Valid Persons person);
}
