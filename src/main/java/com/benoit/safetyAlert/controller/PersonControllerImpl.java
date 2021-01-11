package com.benoit.safetyAlert.controller;

import com.benoit.safetyAlert.dao.PersonDao;
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
  public Collection<String> communityEmail(@RequestParam String city) {

    return personService.getCommunityEmail(city);
  }

  @Override
  @GetMapping("/fire")
  @ResponseStatus(HttpStatus.OK)
  public Collection<Object> fire(@RequestParam String address) {

    return personService.getFireAddress(address);
  }

  @Override
  @GetMapping("/childAlert")
  @ResponseStatus(HttpStatus.OK)
  public Collection<Object> childAlert(@RequestParam String address) {

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
}
