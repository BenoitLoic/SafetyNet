package com.benoit.safetyAlert.IntegrationTest;

import com.benoit.safetyAlert.repository.DataRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MedicalRecordIT {

  @Autowired
  public MockMvc mockMvc;


  @Test
  public void testGetPersonInfo() throws Exception {


    mockMvc.perform(MockMvcRequestBuilders.get("/personInfo").param("firstName", "John").param("lastName", "Boyd"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(jsonPath("firstName", is("John")))
        .andExpect(jsonPath("lastName", is("Boyd")))
        .andExpect(jsonPath("email", is("jaboyd@email.com")));
  }

//  @Test
//  public void testCreateMedicalRecord() throws Exception {
//
//    ObjectMapper objectMapper = new ObjectMapper();
//    ObjectNode objectNode = objectMapper.createObjectNode();
//    objectNode.set("firstName", TextNode.valueOf("Loic"));
//    objectNode.set("lastName",TextNode.valueOf("Benoit"));
////    objectNode.set("birthdate",TextNode.valueOf("03/12/2018"));
////    objectNode.set("medications",TextNode.valueOf("milk"));
//
//    mockMvc.perform(MockMvcRequestBuilders.post("/person").contentType(MediaType.APPLICATION_JSON).content(objectNode.toString()))
//        .andExpect(status().isCreated());
//  }

}
