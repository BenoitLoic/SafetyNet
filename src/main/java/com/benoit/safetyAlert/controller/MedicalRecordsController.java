package com.benoit.safetyAlert.controller;

import com.benoit.safetyAlert.dto.PersonInfo;
import com.benoit.safetyAlert.model.Medicalrecords;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

public interface MedicalRecordsController {


  PersonInfo personInfo(@NotBlank @RequestParam String firstName,
                        @NotBlank @RequestParam String lastName);


  void createMedicalRecord(@Valid @RequestBody Medicalrecords medicalrecord);


  void deleteMedicalRecord(@Valid @RequestBody Medicalrecords medicalRecord);


  void updateMedicalRecord(@Valid @RequestBody Medicalrecords medicalrecord);
}
