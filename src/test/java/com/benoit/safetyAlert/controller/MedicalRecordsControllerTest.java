package com.benoit.safetyAlert.controller;

import com.benoit.safetyAlert.exceptions.DataAlreadyExistException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@SpringBootTest
class MedicalRecordsControllerTest {

  @Autowired MockMvc mockMvc;
  @MockBean MedicalRecordsControllerImpl medicalRecordsController;

  String firstNameTest = "Homer Jay";
  String lastNameTest = "Simpson";
  String birthdateTest = "01/01/1987";
  List<String> medicationsTest = new ArrayList<>();
  List<String> allergiesTest = new ArrayList<>();

  @Test
  public void createMedicalRecordValid() throws Exception {
    //        GIVEN
    ObjectMapper objectMapper = new ObjectMapper();
    ObjectNode jsonMedicalRecords = objectMapper.createObjectNode();
    jsonMedicalRecords.set("firstName", TextNode.valueOf(firstNameTest));
    jsonMedicalRecords.set("lastName", TextNode.valueOf(lastNameTest));
    jsonMedicalRecords.set("birthdate", TextNode.valueOf(birthdateTest));
    //        WHEN

    //        THEN
    mockMvc
        .perform(
            MockMvcRequestBuilders.post("/medicalRecord")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMedicalRecords.toString()))
        .andExpect(MockMvcResultMatchers.status().isCreated());
  }

  @Test
  public void createMedicalRecordInvalid() throws Exception {
    //        GIVEN
    ObjectMapper objectMapper = new ObjectMapper();
    ObjectNode jsonMedicalRecords = objectMapper.createObjectNode();
    jsonMedicalRecords.set("firstName", TextNode.valueOf(" "));
    jsonMedicalRecords.set("lastName", TextNode.valueOf(lastNameTest));
    jsonMedicalRecords.set("birthdate", TextNode.valueOf(birthdateTest));
    //        WHEN

    //        THEN
    mockMvc
        .perform(
            MockMvcRequestBuilders.post("/medicalRecord")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMedicalRecords.toString()))
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }

  @Test
  public void createMedicalRecordWhenDataAlreadyExist() throws Exception {
    //        GIVEN
    ObjectMapper objectMapper = new ObjectMapper();
    ObjectNode jsonMedicalRecords = objectMapper.createObjectNode();
    jsonMedicalRecords.set("firstName", TextNode.valueOf(firstNameTest));
    jsonMedicalRecords.set("lastName", TextNode.valueOf(lastNameTest));
    jsonMedicalRecords.set("birthdate", TextNode.valueOf(birthdateTest));
    //        WHEN
    Mockito.doThrow(DataAlreadyExistException.class)
        .when(medicalRecordsController)
        .createMedicalRecord(Mockito.any());
    //        THEN
    mockMvc.perform(MockMvcRequestBuilders.post("/medicalRecord").contentType(MediaType.APPLICATION_JSON).content(jsonMedicalRecords.toString())).andExpect(MockMvcResultMatchers.status().isConflict());
  }
}
