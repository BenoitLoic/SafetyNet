package com.benoit.safetyAlert.IntegrationTest;

import com.benoit.safetyAlert.model.Persons;
import com.benoit.safetyAlert.repository.DataRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class PersonIT {
  @Autowired
  public MockMvc mockMvc;

  @Test
  public void testCreatePerson() throws Exception {

    ObjectMapper objectMapper = new ObjectMapper();
    ObjectNode objectNode = objectMapper.createObjectNode();
    objectNode.set("firstName", TextNode.valueOf("Loic"));
    objectNode.set("lastName", TextNode.valueOf("Benoit"));
    objectNode.set("address", TextNode.valueOf("1 rue du Test"));
    objectNode.set("city", TextNode.valueOf("Testcity"));
    objectNode.set("zip", TextNode.valueOf("01234"));
    objectNode.set("phone", TextNode.valueOf("000-222-1111"));
    objectNode.set("email", TextNode.valueOf("testmail@mail.com"));

    mockMvc.perform(post("/person").contentType(MediaType.APPLICATION_JSON).content(objectNode.toString()))
        .andExpect(status().isCreated());
    Persons person = new Persons();
    person.setFirstName("Loic");
    person.setLastName("Benoit");
    DataRepository dataRepository = new DataRepository();
    Assertions.assertThat(dataRepository.getPersons().contains(person)).isTrue();
  }

  @Test
  public void testUpdatePerson() throws Exception {

    ObjectMapper objectMapper = new ObjectMapper();
    ObjectNode objectNode = objectMapper.createObjectNode();
    objectNode.set("firstName", TextNode.valueOf("Loic"));
    objectNode.set("lastName", TextNode.valueOf("Benoit"));
    objectNode.set("address", TextNode.valueOf("3 rue du Test"));

    objectNode.set("email", TextNode.valueOf("update@mail.com"));

    mockMvc.perform(put("/person").contentType(MediaType.APPLICATION_JSON).content(objectNode.toString()))
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
