package com.benoit.safetyAlert.controller;

import com.benoit.safetyAlert.dto.PersonInfo;
import com.benoit.safetyAlert.exceptions.DataAlreadyExistException;
import com.benoit.safetyAlert.exceptions.DataNotFindException;
import com.benoit.safetyAlert.model.Persons;
import com.benoit.safetyAlert.services.PersonServiceImpl;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.util.NestedServletException;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = PersonControllerImpl.class)
public class PersonControllerTest {

  @Autowired
  MockMvc mockMvc;
  @MockBean
  PersonServiceImpl personServiceMock;

  String firstNameTest = "Homer Jay";
  String lastNameTest = "Simpson";
  String addressTest = "742 Evergreen Terrace";
  String cityTest = "Springfield";
  String zipTest = "56800";
  String phoneTest = "00112233";
  String emailTest = "donut.test@email.com";

  @Test
  void communityEmailValid() throws Exception {
//    GIVEN
    Collection<Persons> emailList = new ArrayList<>();
//    WHEN
    when(personServiceMock.getCommunityEmail(any())).thenReturn(emailList);
//    THEN
    mockMvc.perform(get("/communityEmail").param("city", cityTest))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  void communityEmailInvalid() {
//    GIVEN

//    WHEN

//    THEN
    assertThrows(NestedServletException.class,
        () -> mockMvc.perform(get("/communityEmail")
            .param("city", "")));
  }

  @Test
  void fireValid() throws Exception {
//    GIVEN
    Collection<PersonInfo> testList = new ArrayList<>();

//    WHEN
    when(personServiceMock.getFireAddress(any())).thenReturn(testList);
//    THEN
    mockMvc.perform(get("/fire").param("address", addressTest))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  void fireInvalid() {
//    GIVEN

//    WHEN

//    THEN
    assertThrows(NestedServletException.class,
        () -> mockMvc.perform(get("/fire")
            .param("address", "")));
  }

  @Test
  void childAlertValid() throws Exception {
//    GIVEN
    Collection<PersonInfo> testlist = new ArrayList<>();

//    WHEN
    when(personServiceMock.getChildAlert(any())).thenReturn(testlist);
//    THEN
    mockMvc.perform(get("/childAlert").param("address", addressTest))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  void childAlertInvalid() {
//    GIVEN

//    WHEN

//    THEN
    assertThrows(NestedServletException.class,
        () -> mockMvc.perform(get("/childAlert")
            .param("address", "")));
  }


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
            post("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonPerson.toString()))
        .andExpect(status().isCreated());
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
            post("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonPerson.toString()))
        .andExpect(status().isBadRequest());
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
        .when(personServiceMock)
        .createPerson(Mockito.any());
    //        THEN
    mockMvc
        .perform(
            post("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonPerson.toString()))
        .andExpect(status().isConflict());
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
            delete("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonPerson.toString()))
        .andExpect(status().isOk());
  }

  @Test
  public void deletePersonInvalid() throws Exception {
    //   GIVEN
    ObjectMapper objectMapper = new ObjectMapper();
    ObjectNode jsonPerson = objectMapper.createObjectNode();
    jsonPerson.set("firstName", TextNode.valueOf("firstNameTest"));
    jsonPerson.set("lastName", TextNode.valueOf(""));
    //    WHEN

    //    THEN
    mockMvc
        .perform(
            delete("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonPerson.toString()))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void deletePersonWhenDataNotFindException() throws Exception {

    //    GIVEN
    ObjectMapper objectMapper = new ObjectMapper();
    ObjectNode jsonPerson = objectMapper.createObjectNode();
    jsonPerson.set("firstName", TextNode.valueOf(firstNameTest));
    jsonPerson.set("lastName", TextNode.valueOf(lastNameTest));
    //    WHEN
    Mockito.doThrow(DataNotFindException.class).when(personServiceMock).deletePerson(Mockito.any());
    //  THEN
    mockMvc
        .perform(
            delete("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonPerson.toString()))
        .andExpect(status().isNotFound());
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
            put("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonPerson.toString()))
        .andExpect(status().isCreated());
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
            put("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonPerson.toString()))
        .andExpect(status().isBadRequest());
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
    Mockito.doThrow(DataNotFindException.class)
        .when(personServiceMock).updatePerson(Mockito.any());
    //    THEN
    mockMvc
        .perform(
            put("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonPerson.toString()))
        .andExpect(status().isNotFound());
  }

}
