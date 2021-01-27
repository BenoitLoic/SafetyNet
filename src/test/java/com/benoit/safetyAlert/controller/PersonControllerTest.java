package com.benoit.safetyAlert.controller;

import com.benoit.safetyAlert.exceptions.DataAlreadyExistException;
import com.benoit.safetyAlert.exceptions.DataNotFindException;
import com.benoit.safetyAlert.model.Persons;
import com.benoit.safetyAlert.services.PersonServiceImpl;
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

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;

@AutoConfigureMockMvc
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class PersonControllerTest {

  @Autowired
  MockMvc mockMvc;
  @MockBean
  PersonControllerImpl personController;
  @MockBean
  PersonServiceImpl personService;
  String firstNameTest = "Homer Jay";
  String lastNameTest = "Simpson";
  String addressTest = "742 Evergreen Terrace";
  String cityTest = "Springfield";
  String zipTest = "56800";
  String phoneTest = "00112233";
  String emailTest = "donut.test@email.com";

  @Test
  public void createPersonValid() throws Exception {
    //        GIVEN
    ObjectMapper obm = new ObjectMapper();
    ObjectNode jsonPerson = obm.createObjectNode();
    jsonPerson.set("firstName", TextNode.valueOf(firstNameTest));
    jsonPerson.set("lastName", TextNode.valueOf(lastNameTest));
    //        WHEN

    //        THEN
    mockMvc
        .perform(
            MockMvcRequestBuilders.post("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonPerson.toString()))
        .andExpect(MockMvcResultMatchers.status().isCreated());
  }

  @Test
  public void createPersonInvalid() throws Exception {
    //        GIVEN
    ObjectMapper obm = new ObjectMapper();
    ObjectNode jsonPerson = obm.createObjectNode();
    jsonPerson.set("firstName", TextNode.valueOf(firstNameTest));
    jsonPerson.set("lastName", TextNode.valueOf(" "));
    //        WHEN

    //        THEN
    mockMvc
        .perform(
            MockMvcRequestBuilders.post("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonPerson.toString()))
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }

  @Test
  public void createPersonWhenPersonAlreadyExist() throws Exception {
    //        GIVEN
    ObjectMapper obm = new ObjectMapper();
    ObjectNode jsonPerson = obm.createObjectNode();
    jsonPerson.set("firstName", TextNode.valueOf(firstNameTest));
    jsonPerson.set("lastName", TextNode.valueOf(lastNameTest));
    //        WHEN
    // on provoque l'exception DataAlreadyExistExc pour uniquement vérifier le code status de la
    // requette
    Mockito.doThrow(DataAlreadyExistException.class)
        .when(personController)
        .createPerson(Mockito.any());
    //        THEN
    mockMvc
        .perform(
            MockMvcRequestBuilders.post("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonPerson.toString()))
        .andExpect(MockMvcResultMatchers.status().isConflict());
  }

  @Test
  public void deletePersonValid() throws Exception {
    //    GIVEN
    ObjectMapper objectMapper = new ObjectMapper();
    ObjectNode jsonPerson = objectMapper.createObjectNode();
    jsonPerson.set("firstName", TextNode.valueOf(firstNameTest));
    jsonPerson.set("lastName", TextNode.valueOf(lastNameTest));
    //    WHEN

    //    THEN
    mockMvc
        .perform(
            MockMvcRequestBuilders.delete("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonPerson.toString()))
        .andExpect(MockMvcResultMatchers.status().isOk());
  }

  @Test
  public void deletePersonInvalid() throws Exception {
    //   GIVEN
    ObjectMapper objectMapper = new ObjectMapper();
    ObjectNode jsonPerson = objectMapper.createObjectNode();
    jsonPerson.set("firstName", TextNode.valueOf("firstNameTest"));
    jsonPerson.set("lastName", TextNode.valueOf(" "));
    //    WHEN

    //    THEN
    mockMvc
        .perform(
            MockMvcRequestBuilders.delete("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonPerson.toString()))
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }

  @Test
  public void deletePersonWhenDataNotFindException() throws Exception {

    //    GIVEN
    ObjectMapper objectMapper = new ObjectMapper();
    ObjectNode jsonPerson = objectMapper.createObjectNode();
    jsonPerson.set("firstName", TextNode.valueOf(firstNameTest));
    jsonPerson.set("lastName", TextNode.valueOf(lastNameTest));
    //    WHEN
    Mockito.doThrow(DataNotFindException.class).when(personController).deletePerson(Mockito.any());
    //  THEN
    mockMvc
        .perform(
            MockMvcRequestBuilders.delete("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonPerson.toString()))
        .andExpect(MockMvcResultMatchers.status().isNotFound());
  }

  @Test
  public void updatePersonValid() throws Exception {

    //    GIVEN
    ObjectMapper objectMapper = new ObjectMapper();
    ObjectNode jsonPerson = objectMapper.createObjectNode();
    jsonPerson.set("firstName", TextNode.valueOf(firstNameTest));
    jsonPerson.set("lastName", TextNode.valueOf(lastNameTest));
    jsonPerson.set("address", TextNode.valueOf(addressTest));
    //    WHEN

    //    THEN
    mockMvc
        .perform(
            MockMvcRequestBuilders.put("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonPerson.toString()))
        .andExpect(MockMvcResultMatchers.status().isCreated());
  }

  @Test
  public void updatePersonInvalid() throws Exception {

    //    GIVEN
    ObjectMapper objectMapper = new ObjectMapper();
    ObjectNode jsonPerson = objectMapper.createObjectNode();
    jsonPerson.set("firstName", TextNode.valueOf(firstNameTest));
    jsonPerson.set("lastName", TextNode.valueOf(" "));
    jsonPerson.set("address", TextNode.valueOf(addressTest));
    //    WHEN

    //    THEN
    mockMvc
        .perform(
            MockMvcRequestBuilders.put("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonPerson.toString()))
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }

  @Test
  public void updatePersonWhenDataNotFindException() throws Exception {

    //    GIVEN
    ObjectMapper objectMapper = new ObjectMapper();
    ObjectNode jsonPerson = objectMapper.createObjectNode();
    jsonPerson.set("firstName", TextNode.valueOf(firstNameTest));
    jsonPerson.set("lastName", TextNode.valueOf(lastNameTest));
    jsonPerson.set("address", TextNode.valueOf(addressTest));
    //    WHEN
    Mockito.doThrow(DataNotFindException.class).when(personController).updatePerson(Mockito.any());
    //    THEN
    mockMvc
        .perform(
            MockMvcRequestBuilders.put("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonPerson.toString()))
        .andExpect(MockMvcResultMatchers.status().isNotFound());
  }
//
//  @Test
//  public void communityEmailValid() throws Exception {
//
//    //    GIVEN
//    Persons testPerson = new Persons();
//testPerson.setEmail(emailTest);
//    List <Persons> testList = Arrays.asList(testPerson);
//    //    WHEN
//Mockito.doReturn(testList).when(personService.getCommunityEmail(anyString()));
//    //    THEN
//mockMvc.perform(MockMvcRequestBuilders.get("/communityEmail").contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect();
//  }
}
