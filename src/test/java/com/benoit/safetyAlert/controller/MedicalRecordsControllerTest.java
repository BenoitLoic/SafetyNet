package com.benoit.safetyAlert.controller;

import com.benoit.safetyAlert.dto.PersonInfo;
import com.benoit.safetyAlert.exceptions.DataAlreadyExistException;
import com.benoit.safetyAlert.exceptions.DataNotFindException;
import com.benoit.safetyAlert.services.MedicalRecordsServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import jdk.jfr.ContentType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@SpringBootTest
class MedicalRecordsControllerTest {

  @Autowired
  MockMvc mockMvc;
  @MockBean
  MedicalRecordsControllerImpl medicalRecordsControllerMock;
  @Mock
  MedicalRecordsServiceImpl medicalRecordsServiceMock;
  @InjectMocks
  MedicalRecordsControllerImpl medicalRecordsController;

  String firstNameTest = "Homer Jay";
  String lastNameTest = "Simpson";
  String birthdateTest = "01/01/1987";
  List<String> medicationsTest = new ArrayList<>();
  List<String> allergiesTest = new ArrayList<>();

  @Test
  void personInfoValid() throws Exception {
//    GIVEN


//    WHEN
//when(medicalRecordsServiceMock.getPersonInfo(firstNameTest,lastNameTest)).thenReturn(new PersonInfo());

//    THEN
    mockMvc.perform(get("/personInfo"))
        .andExpect(MockMvcResultMatchers.status().isOk());
  }

  @Test
  void personInfoInvalid(){
//    GIVEN


//    WHEN
    when(medicalRecordsServiceMock.getPersonInfo(any(),any())).thenThrow(DataNotFindException.class);

//    THEN
    Assertions.assertThrows(DataNotFindException.class,()->medicalRecordsController.personInfo(firstNameTest,lastNameTest));
  }

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
            post("/medicalRecord")
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
            post("/medicalRecord")
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
        .when(medicalRecordsControllerMock)
        .createMedicalRecord(Mockito.any());
    //        THEN
    mockMvc
        .perform(
            post("/medicalRecord")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMedicalRecords.toString()))
        .andExpect(MockMvcResultMatchers.status().isConflict());
  }

  @Test
  public void deleteMedicalRecordValid() throws Exception {
    //    GIVEN
    ObjectMapper objectMapper = new ObjectMapper();
    ObjectNode jsonMedicalRecord = objectMapper.createObjectNode();
    jsonMedicalRecord.set("firstName", TextNode.valueOf(firstNameTest));
    jsonMedicalRecord.set("lastName", TextNode.valueOf(lastNameTest));
    jsonMedicalRecord.set("birthdate", TextNode.valueOf(birthdateTest));
    //    WHEN

    //    THEN
    mockMvc
        .perform(
            delete("/medicalRecord")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMedicalRecord.toString()))
        .andExpect(MockMvcResultMatchers.status().isOk());
  }

  @Test
  public void deleteMedicalRecordInvalid() throws Exception {
    //    GIVEN
    ObjectMapper objectMapper = new ObjectMapper();
    ObjectNode jsonMedicalRecord = objectMapper.createObjectNode();
    jsonMedicalRecord.set("firstName", TextNode.valueOf(firstNameTest));
    jsonMedicalRecord.set("lastName", TextNode.valueOf(" "));
    //    WHEN

    //    THEN
    mockMvc
        .perform(
            delete("/medicalRecord")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMedicalRecord.toString()))
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }

  @Test
  public void deleteMedicalRecordWhenDataNotFindException() throws Exception {
    //    GIVEN
    ObjectMapper objectMapper = new ObjectMapper();
    ObjectNode jsonMedicalRecord = objectMapper.createObjectNode();
    jsonMedicalRecord.set("firstName", TextNode.valueOf(firstNameTest));
    jsonMedicalRecord.set("lastName", TextNode.valueOf(lastNameTest));
    jsonMedicalRecord.set("birthdate", TextNode.valueOf(birthdateTest));
    //    WHEN
    Mockito.doThrow(DataNotFindException.class)
        .when(medicalRecordsControllerMock)
        .deleteMedicalRecord(Mockito.any());
    //    THEN
    mockMvc
        .perform(
            delete("/medicalRecord")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMedicalRecord.toString()))
        .andExpect(MockMvcResultMatchers.status().isNotFound());
  }

  @Test
  public void updateMedicalRecordValid() throws Exception {
    //    GIVEN
    ObjectMapper objectMapper = new ObjectMapper();
    ObjectNode jsonMedicalRecord = objectMapper.createObjectNode();
    jsonMedicalRecord.set("firstName", TextNode.valueOf(firstNameTest));
    jsonMedicalRecord.set("lastName", TextNode.valueOf(lastNameTest));
    jsonMedicalRecord.set("birthdate", TextNode.valueOf(birthdateTest));
    //    WHEN

    //    THEN
    mockMvc
        .perform(
            put("/medicalRecord")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMedicalRecord.toString()))
        .andExpect(MockMvcResultMatchers.status().isCreated());
  }

  @Test
  public void updateMedicalRecordInvalid() throws Exception {
    //    GIVEN
    ObjectMapper objectMapper = new ObjectMapper();
    ObjectNode jsonMedicalRecord = objectMapper.createObjectNode();
    jsonMedicalRecord.set("firstName", TextNode.valueOf(firstNameTest));
    jsonMedicalRecord.set("lastName", TextNode.valueOf(" "));
    jsonMedicalRecord.set("birthdate", TextNode.valueOf(birthdateTest));
    //    WHEN

    //    THEN
    mockMvc
        .perform(
            put("/medicalRecord")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMedicalRecord.toString()))
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }

  @Test
  public void updateMedicalRecordWhenDataNotFindException() throws Exception {
    //    GIVEN
    ObjectMapper objectMapper = new ObjectMapper();
    ObjectNode jsonMedicalRecord = objectMapper.createObjectNode();
    jsonMedicalRecord.set("firstName", TextNode.valueOf(firstNameTest));
    jsonMedicalRecord.set("lastName", TextNode.valueOf(lastNameTest));
    jsonMedicalRecord.set("birthdate", TextNode.valueOf(birthdateTest));
    //    WHEN
    Mockito.doThrow(DataNotFindException.class)
        .when(medicalRecordsControllerMock)
        .updateMedicalRecord(Mockito.any());
    //    THEN
    mockMvc
        .perform(
            put("/medicalRecord")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMedicalRecord.toString()))
        .andExpect(MockMvcResultMatchers.status().isNotFound());
  }
}
