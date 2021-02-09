package com.benoit.safetyAlert.controller;

import com.benoit.safetyAlert.dto.PersonInfo;
import com.benoit.safetyAlert.exceptions.DataAlreadyExistException;
import com.benoit.safetyAlert.exceptions.DataNotFindException;
import com.benoit.safetyAlert.model.Medicalrecords;
import com.benoit.safetyAlert.services.MedicalRecordsServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.util.NestedServletException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = MedicalRecordsControllerImpl.class)
class MedicalRecordsControllerTest {

  @Autowired
  MockMvc mockMvc;

  @MockBean
  MedicalRecordsServiceImpl medicalRecordsServiceMock;


  String firstNameTest = "Homer Jay";
  String lastNameTest = "Simpson";
  String birthdateTest = "01/01/1987";
  List<String> medicationsTest = new ArrayList<>();
  List<String> allergiesTest = new ArrayList<>();


  @Test
  void personInfoValidation_Valid() throws Exception {
//on verifie que le @Valid fonctionne bien
//    GIVEN

//    WHEN
    when(medicalRecordsServiceMock.getPersonInfo(any(), any())).thenReturn(new PersonInfo());
//    THEN
    mockMvc.perform(get("/personInfo")
        .param("firstName", firstNameTest)
        .param("lastName", lastNameTest))
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  void personInfoValidation_Invalid() {

    assertThrows(NestedServletException.class,
        () -> mockMvc.perform(get("/personInfo")
            .param("firstName", "")
            .param("lastName", lastNameTest)));

  }

  @Test
  void personInfo() throws Exception {
//    GIVEN

//    WHEN
    doThrow(DataNotFindException.class)
        .when(medicalRecordsServiceMock).getPersonInfo(any(), any());
//    THEN
    mockMvc.perform(get("/personInfo")
        .param("firstName", firstNameTest)
        .param("lastName", lastNameTest))
        .andExpect(status().isNotFound());

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
        .andExpect(status().isCreated());
  }

  @Test
  public void createMedicalRecordInvalid() throws Exception {
    //        GIVEN
    ObjectMapper objectMapper = new ObjectMapper();
    ObjectNode jsonMedicalRecords = objectMapper.createObjectNode();
    jsonMedicalRecords.set("firstName", TextNode.valueOf(" "));
    jsonMedicalRecords.set("lastName", TextNode.valueOf(lastNameTest));

    //        WHEN

    //        THEN
    mockMvc
        .perform(
            post("/medicalRecord")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMedicalRecords.toString()))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void createMedicalRecordInvalidBirthdate() throws Exception {
    //        GIVEN
    ObjectMapper objectMapper = new ObjectMapper();
    ObjectNode jsonMedicalRecords = objectMapper.createObjectNode();
    jsonMedicalRecords.set("firstName", TextNode.valueOf(firstNameTest));
    jsonMedicalRecords.set("lastName", TextNode.valueOf(lastNameTest));

    //        WHEN

    //        THEN
    mockMvc
        .perform(
            post("/medicalRecord")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMedicalRecords.toString()))
        .andExpect(status().isBadRequest());
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
    doThrow(DataAlreadyExistException.class)
        .when(medicalRecordsServiceMock)
        .createMedicalRecord(any());
    //        THEN
    mockMvc
        .perform(
            post("/medicalRecord")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMedicalRecords.toString()))
        .andExpect(status().isConflict());
  }

  @Test
  public void createMedicalRecordWhenDataNotFound() throws Exception {
    //        GIVEN
    ObjectMapper objectMapper = new ObjectMapper();
    ObjectNode jsonMedicalRecords = objectMapper.createObjectNode();
    jsonMedicalRecords.set("firstName", TextNode.valueOf(firstNameTest));
    jsonMedicalRecords.set("lastName", TextNode.valueOf(lastNameTest));
    jsonMedicalRecords.set("birthdate", TextNode.valueOf(birthdateTest));
    //        WHEN
    doThrow(DataNotFindException.class)
        .when(medicalRecordsServiceMock)
        .createMedicalRecord(any());
    //        THEN
    mockMvc
        .perform(
            post("/medicalRecord")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMedicalRecords.toString()))
        .andExpect(status().isNotFound());
  }

  @Test
  public void deleteMedicalRecordValid() throws Exception {
    //    GIVEN
    ObjectMapper objectMapper = new ObjectMapper();
    ObjectNode jsonMedicalRecord = objectMapper.createObjectNode();
    jsonMedicalRecord.set("firstName", TextNode.valueOf(firstNameTest));
    jsonMedicalRecord.set("lastName", TextNode.valueOf(lastNameTest));


    //    WHEN

    //    THEN
    mockMvc
        .perform(
            delete("/medicalRecord")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMedicalRecord.toString()))
        .andExpect(status().isOk());
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
        .andExpect(status().isBadRequest());
  }

  @Test
  public void deleteMedicalRecordWhenDataNotFindException() throws Exception {
    //    GIVEN
    ObjectMapper objectMapper = new ObjectMapper();
    ObjectNode jsonMedicalRecord = objectMapper.createObjectNode();
    jsonMedicalRecord.set("firstName", TextNode.valueOf(firstNameTest));
    jsonMedicalRecord.set("lastName", TextNode.valueOf(lastNameTest));

    //    WHEN
    doThrow(DataNotFindException.class)
        .when(medicalRecordsServiceMock)
        .deleteMedicalRecord(any());
    //    THEN
    mockMvc
        .perform(
            delete("/medicalRecord")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMedicalRecord.toString()))
        .andExpect(status().isNotFound());
  }

  @Test
  public void updateMedicalRecordValid() throws Exception {
    //    GIVEN
    ObjectMapper objectMapper = new ObjectMapper();
    ObjectNode jsonMedicalRecord = objectMapper.createObjectNode();
    jsonMedicalRecord.set("firstName", TextNode.valueOf(firstNameTest));
    jsonMedicalRecord.set("lastName", TextNode.valueOf(lastNameTest));

    //    WHEN

    //    THEN
    mockMvc
        .perform(
            put("/medicalRecord")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMedicalRecord.toString()))
        .andExpect(status().isCreated());
  }

  @Test
  public void updateMedicalRecordInvalid() throws Exception {
    //    GIVEN
    ObjectMapper objectMapper = new ObjectMapper();
    ObjectNode jsonMedicalRecord = objectMapper.createObjectNode();
    jsonMedicalRecord.set("firstName", TextNode.valueOf(firstNameTest));
    jsonMedicalRecord.set("lastName", TextNode.valueOf(" "));

    //    WHEN

    //    THEN
    mockMvc
        .perform(
            put("/medicalRecord")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMedicalRecord.toString()))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void updateMedicalRecordWhenDataNotFindException() throws Exception {
    //    GIVEN
    ObjectMapper objectMapper = new ObjectMapper();
    ObjectNode jsonMedicalRecord = objectMapper.createObjectNode();
    jsonMedicalRecord.set("firstName", TextNode.valueOf(firstNameTest));
    jsonMedicalRecord.set("lastName", TextNode.valueOf(lastNameTest));

    //    WHEN
    doThrow(DataNotFindException.class)
        .when(medicalRecordsServiceMock)
        .updateMedicalRecord(any());
    //    THEN
    mockMvc
        .perform(
            put("/medicalRecord")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMedicalRecord.toString()))
        .andExpect(status().isNotFound());
  }
}
