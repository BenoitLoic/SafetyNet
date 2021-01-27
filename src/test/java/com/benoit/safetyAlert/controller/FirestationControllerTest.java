package com.benoit.safetyAlert.controller;

import com.benoit.safetyAlert.exceptions.DataAlreadyExistException;
import com.benoit.safetyAlert.exceptions.DataNotFindException;
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

@AutoConfigureMockMvc
@SpringBootTest
@ExtendWith(SpringExtension.class)
class FirestationControllerTest {

  @Autowired
  MockMvc mockMvc;
  @MockBean
  FirestationControllerImpl firestationController;

  String stationTest = "8";
  String addressTest = "742 Evergreen Terrace";

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
            MockMvcRequestBuilders.post("/firestation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonFirestation.toString()))
        .andExpect(MockMvcResultMatchers.status().isCreated());
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
            MockMvcRequestBuilders.post("/firestation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonFirestation.toString()))
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }

  @Test
  void createFirestationWhenDataAlreadyExist() throws Exception {
    //        GIVEN
    ObjectMapper objectMapper = new ObjectMapper();
    ObjectNode jsonFirestation = objectMapper.createObjectNode();
    jsonFirestation.set("station", TextNode.valueOf(stationTest));
    jsonFirestation.set("address", TextNode.valueOf(addressTest));
    //        WHEN
    Mockito.doThrow(DataAlreadyExistException.class)
        .when(firestationController)
        .createFirestation(Mockito.any());
    //        THEN
    mockMvc
        .perform(
            MockMvcRequestBuilders.post("/firestation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonFirestation.toString()))
        .andExpect(MockMvcResultMatchers.status().isConflict());
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
            MockMvcRequestBuilders.delete("/firestation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonFirestation.toString()))
        .andExpect(MockMvcResultMatchers.status().isOk());
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
            MockMvcRequestBuilders.delete("/firestation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonFirestation.toString()))
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }

  @Test
  public void deleteFirestationWhenDataNotFindException() throws Exception {
    //    GIVEN
    ObjectMapper objectMapper = new ObjectMapper();
    ObjectNode jsonFirestation = objectMapper.createObjectNode();
    jsonFirestation.set("station", TextNode.valueOf(stationTest));
    jsonFirestation.set("address", TextNode.valueOf(addressTest));
    //    WHEN
    Mockito.doThrow(DataNotFindException.class)
        .when(firestationController)
        .deleteFirestation(Mockito.any());
    //    THEN
    mockMvc
        .perform(
            MockMvcRequestBuilders.delete("/firestation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonFirestation.toString()))
        .andExpect(MockMvcResultMatchers.status().isNotFound());
  }
}
