package com.benoit.safetyAlert.IntegrationTest;

import com.benoit.safetyAlert.model.Persons;
import com.benoit.safetyAlert.repository.DataRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import netscape.javascript.JSObject;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PersonIT {
  @Autowired
  public MockMvc mockMvc;

  @Test
  public void testChildAlert() throws Exception {

    mockMvc.perform(MockMvcRequestBuilders.get("/childAlert").param("address", "947 E. Rose Dr"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(jsonPath("$[0].firstName", is("Kendrik")))
        .andExpect(jsonPath("$[0].lastName", is("Stelzer")))
        .andExpect(jsonPath("$[0].age", is(6)));
  }


  @Test
  public void testCreatePerson() throws Exception {

    //création d'une personne en Json
    ObjectMapper objectMapper = new ObjectMapper();
    ObjectNode objectNode = objectMapper.createObjectNode();
    objectNode.set("firstName", TextNode.valueOf("Loic"));
    objectNode.set("lastName", TextNode.valueOf("Benoit"));
    objectNode.set("address", TextNode.valueOf("1 rue du Test"));
    objectNode.set("city", TextNode.valueOf("Testcity"));
    objectNode.set("zip", TextNode.valueOf("01234"));
    objectNode.set("phone", TextNode.valueOf("000-222-1111"));
    objectNode.set("email", TextNode.valueOf("testmail@mail.com"));

    //post de la personne et vérification si le code retour est OK
    mockMvc.perform(post("/person").contentType(MediaType.APPLICATION_JSON).content(objectNode.toString()))
        .andExpect(status().isCreated());

    //vérification si la personne a bien été ajouté dans le repository
    Persons person = new Persons();
    person.setFirstName("Loic");
    person.setLastName("Benoit");
    DataRepository dataRepository = new DataRepository();
    Assertions.assertThat(dataRepository.getPersons().contains(person)).isTrue();

    //test d'ajout de la meme personne et verif si il y a bien conflit
    mockMvc.perform(post("/person").contentType(MediaType.APPLICATION_JSON).content(objectNode.toString()))
        .andExpect(status().isConflict());
  }

  @Test
  public void testUpdatePerson() throws Exception {

//    ObjectMapper objectMapper1 = new ObjectMapper();
//    ObjectNode createPerson = objectMapper1.createObjectNode();
//    createPerson.set("firstName", TextNode.valueOf("Loic"));
//    createPerson.set("lastName", TextNode.valueOf("Benoit"));
//    createPerson.set("address", TextNode.valueOf("1 rue du Test"));
//    createPerson.set("city", TextNode.valueOf("Testcity"));
//    createPerson.set("zip", TextNode.valueOf("01234"));
//    createPerson.set("phone", TextNode.valueOf("000-222-1111"));
//    createPerson.set("email", TextNode.valueOf("testmail@mail.com"));
//    mockMvc.perform(post("/person").contentType(MediaType.APPLICATION_JSON).content(createPerson.toString()));

    ObjectMapper objectMapper = new ObjectMapper();
    ObjectNode updatePerson = objectMapper.createObjectNode();
    updatePerson.set("firstName", TextNode.valueOf("Loic"));
    updatePerson.set("lastName", TextNode.valueOf("Benoit"));
    updatePerson.set("address", TextNode.valueOf("3 rue du Test"));
    updatePerson.set("email", TextNode.valueOf("update@mail.com"));

    mockMvc.perform(put("/person").contentType(MediaType.APPLICATION_JSON).content(updatePerson.toString()))
        .andExpect(status().isCreated());


  }

  @Test
  public void testDeletePerson() throws Exception {

    ObjectMapper objectMapper = new ObjectMapper();
    ObjectNode objectNode = objectMapper.createObjectNode();
    objectNode.set("firstName", TextNode.valueOf("Loic"));
    objectNode.set("lastName", TextNode.valueOf("Benoit"));


    mockMvc.perform(delete("/person").contentType(MediaType.APPLICATION_JSON).content(objectNode.toString()))
        .andExpect(status().isOk());

    Persons person = new Persons();
    person.setFirstName("Loic");
    person.setLastName("Benoit");
    DataRepository dataRepository = new DataRepository();
    Assertions.assertThat(dataRepository.getPersons().contains(person)).isFalse();
  }

}
