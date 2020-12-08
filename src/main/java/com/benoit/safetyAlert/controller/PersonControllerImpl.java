package com.benoit.safetyAlert.controller;

import com.benoit.safetyAlert.services.PersonServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
public class PersonControllerImpl implements PersonController {

  @Autowired private PersonServiceImpl personService;

  @Override
  @GetMapping("/communityEmail")
  public Collection<String> communityEmail(@RequestParam String city) {

    return personService.getCommunityEmail(city);
  }

  @Override
  @GetMapping("/fire")
  public Collection<Object> fire(@RequestParam String address) {

    return personService.getFireAddress(address);
  }

  @Override
  @GetMapping("/childAlert")
  public Collection<Object> childAlert(@RequestParam String address) {

    return personService.getChildAlert(address);
  }
}
