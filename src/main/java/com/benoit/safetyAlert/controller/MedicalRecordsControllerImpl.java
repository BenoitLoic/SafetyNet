package com.benoit.safetyAlert.controller;

import com.benoit.safetyAlert.services.MedicalRecordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
public class MedicalRecordsControllerImpl implements MedicalRecordsController {

  @Autowired private MedicalRecordsService medicalRecordsService;

  @Override
  @GetMapping("/personInfo")
  public Collection<Object> personInfo(String firstName, String lastName) {
    return medicalRecordsService.getPersonInfo(firstName, lastName);
  }
}
