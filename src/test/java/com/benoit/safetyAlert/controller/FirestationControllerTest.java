package com.benoit.safetyAlert.controller;

import com.benoit.safetyAlert.dto.FirestationDto;
import com.benoit.safetyAlert.dto.PersonInfo;
import com.benoit.safetyAlert.exceptions.DataAlreadyExistException;
import com.benoit.safetyAlert.exceptions.DataNotFindException;
import com.benoit.safetyAlert.model.Persons;
import com.benoit.safetyAlert.services.FirestationServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.util.NestedServletException;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = FirestationControllerImpl.class)
class FirestationControllerTest {

  @Autowired
  MockMvc mockMvc;
  @MockBean
  FirestationServiceImpl firestationServiceMock;

  String stationTest = "8";
  String addressTest = "742 Evergreen Terrace";

  @Test
  void phoneAlertValid() throws Exception {
//    GIVEN
    Collection<Persons> phoneListTest = new ArrayList<>();

//    WHEN
    when(firestationServiceMock.getPhoneNumber(any())).thenReturn(phoneListTest);
//    THEN
    mockMvc.perform(get("/phoneAlert").param("station", stationTest))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  void phoneAlertInvalid() {
//    GIVEN

//    WHEN

//    THEN
    assertThrows(NestedServletException.class,
        () -> mockMvc.perform(get("/phoneAlert")
            .param("station", "")));
  }

  @Test
  void fireStationCoverageValid() throws Exception {
//    GIVEN
    Collection<PersonInfo> personInfos = new ArrayList<>();

//  WHEN
    when(firestationServiceMock.getPersonCoveredByFireStation(any())).thenReturn(personInfos);
//  THEN
    mockMvc.perform(get("/firestation")
        .param("stationNumber", stationTest))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  void fireStationCoverageInvalid() {
//    GIVEN
//    WHEN
//    THEN
    assertThrows(NestedServletException.class,
        () -> mockMvc.perform(get("/firestation")
            .param("stationNumber", "")));
  }

  @Test
  void floodStationValid() throws Exception {
//    GIVEN
    Collection<FirestationDto> personList = new ArrayList<>();

//  WHEN
    when(firestationServiceMock.getFloodStations(any())).thenReturn(personList);
//  THEN
    mockMvc.perform(get("/flood/stations")
        .param("stations", stationTest))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  void floodStationInvalid() {
//    GIVEN
//    WHEN
//    THEN
    assertThrows(NestedServletException.class,
        () -> mockMvc.perform(get("/flood/stations")
            .param("stations", "")));
  }


  @Test
  void createFirestationValid() throws Exception {
    //        GIVEN
    ObjectMapper objectMapper = new ObjectMapper();
    ObjectNode jsonFirestation = objectMapper.createObjectNode();
    jsonFirestation.set("station", TextNode.valueOf(stationTest));
    jsonFirestation.set("address", TextNode.valueOf(addressTest));
    //        WHEN

    //        THEN
    mockMvc
        .perform(
            post("/firestation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonFirestation.toString()))
        .andExpect(status().isCreated());
  }

  @Test
  void createFirestationInvalid() throws Exception {
    //        GIVEN
    ObjectMapper objectMapper = new ObjectMapper();
    ObjectNode jsonFirestation = objectMapper.createObjectNode();
    jsonFirestation.set("station", TextNode.valueOf(" "));
    jsonFirestation.set("address", TextNode.valueOf(addressTest));
    //        WHEN

    //        THEN
    mockMvc
        .perform(
            post("/firestation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonFirestation.toString()))
        .andExpect(status().isBadRequest());
  }

  @Test
  void createFirestationWhenDataAlreadyExist() throws Exception {
    //        GIVEN
    ObjectMapper objectMapper = new ObjectMapper();
    ObjectNode jsonFirestation = objectMapper.createObjectNode();
    jsonFirestation.set("station", TextNode.valueOf(stationTest));
    jsonFirestation.set("address", TextNode.valueOf(addressTest));
    //        WHEN
    doThrow(DataAlreadyExistException.class)
        .when(firestationServiceMock)
        .createFirestation(any());
    //        THEN
    mockMvc
        .perform(
            post("/firestation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonFirestation.toString()))
        .andExpect(status().isConflict());
  }

  @Test
  public void deleteFirestationValid() throws Exception {
    //    GIVEN
    ObjectMapper objectMapper = new ObjectMapper();
    ObjectNode jsonFirestation = objectMapper.createObjectNode();
    jsonFirestation.set("station", TextNode.valueOf(stationTest));
    jsonFirestation.set("address", TextNode.valueOf(addressTest));
    //    WHEN

    //    THEN
    mockMvc
        .perform(
            delete("/firestation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonFirestation.toString()))
        .andExpect(status().isOk());
  }

  @Test
  public void deleteFirestationInvalid() throws Exception {
    //    GIVEN
    ObjectMapper objectMapper = new ObjectMapper();
    ObjectNode jsonFirestation = objectMapper.createObjectNode();
    jsonFirestation.set("station", TextNode.valueOf(stationTest));
    jsonFirestation.set("address", TextNode.valueOf(" "));
    //    WHEN

    //    THEN
    mockMvc
        .perform(
            delete("/firestation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonFirestation.toString()))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void deleteFirestationWhenDataNotFindException() throws Exception {
    //    GIVEN
    ObjectMapper objectMapper = new ObjectMapper();
    ObjectNode jsonFirestation = objectMapper.createObjectNode();
    jsonFirestation.set("station", TextNode.valueOf(stationTest));
    jsonFirestation.set("address", TextNode.valueOf(addressTest));
    //    WHEN
    doThrow(DataNotFindException.class)
        .when(firestationServiceMock)
        .deleteFirestation(any());
    //    THEN
    mockMvc
        .perform(
            delete("/firestation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonFirestation.toString()))
        .andExpect(status().isNotFound());
  }

  @Test
  public void updateFirestationValid() throws Exception {
    //    GIVEN
    ObjectMapper objectMapper = new ObjectMapper();
    ObjectNode jsonFirestation = objectMapper.createObjectNode();
    jsonFirestation.set("station", TextNode.valueOf(stationTest));
    jsonFirestation.set("address", TextNode.valueOf(addressTest));
    //    WHEN

    //    THEN
    mockMvc
        .perform(
            put("/firestation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonFirestation.toString()))
        .andExpect(status().isCreated());
  }

  @Test
  public void updateFirestationInvalid() throws Exception {
    //    GIVEN
    ObjectMapper objectMapper = new ObjectMapper();
    ObjectNode jsonFirestation = objectMapper.createObjectNode();
    jsonFirestation.set("station", TextNode.valueOf(stationTest));
    jsonFirestation.set("address", TextNode.valueOf(" "));
    //    WHEN

    //    THEN
    mockMvc
        .perform(
            put("/firestation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonFirestation.toString()))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void updateFirestationWhenDataNotFindException() throws Exception {
    //    GIVEN
    ObjectMapper objectMapper = new ObjectMapper();
    ObjectNode jsonFirestation = objectMapper.createObjectNode();
    jsonFirestation.set("station", TextNode.valueOf(stationTest));
    jsonFirestation.set("address", TextNode.valueOf(addressTest));
    //    WHEN
    doThrow(DataNotFindException.class)
        .when(firestationServiceMock)
        .updateFirestation(any());
    //    THEN
    mockMvc
        .perform(
            put("/firestation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonFirestation.toString()))
        .andExpect(status().isNotFound());
  }

}
