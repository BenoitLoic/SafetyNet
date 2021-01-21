package com.benoit.safetyAlert.controller;

import com.benoit.safetyAlert.dto.PersonInfo;
import com.benoit.safetyAlert.model.Persons;
import com.benoit.safetyAlert.services.PersonServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@RestController
public class PersonControllerImpl implements PersonController {

  @Autowired private PersonServiceImpl personService;

  @Override
  @GetMapping("/communityEmail")
  @ResponseStatus(HttpStatus.OK)
  public Collection<Persons> communityEmail(@RequestParam String city) {

    return personService.getCommunityEmail(city);
  }

  @Override
  @GetMapping("/fire")
  @ResponseStatus(HttpStatus.OK)
  public Collection<PersonInfo> fire(@RequestParam String address) {

    return personService.getFireAddress(address);
  }

  @Override
  @GetMapping("/childAlert")
  @ResponseStatus(HttpStatus.OK)
  public Collection<PersonInfo> childAlert(@RequestParam String address) {

    return personService.getChildAlert(address);
  }

  @Override
  @PostMapping("/person")
  @ResponseStatus(HttpStatus.CREATED)
  public void createPerson(@RequestBody @Valid Persons person) {
    personService.createPerson(person);
  }

  @Override
  @DeleteMapping("/person")
  @ResponseStatus(HttpStatus.OK)
  public void deletePerson(@RequestBody @Valid Persons person) {

    personService.deletePerson(person);
  }

  @Override
  @PutMapping("/person")
  @ResponseStatus(HttpStatus.CREATED)
  public void updatePerson(@RequestBody @Valid Persons person) {

    personService.updatePerson(person);
  }
}
