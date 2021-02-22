package com.safetynet.safetyalerts.IntegrationTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class MedicalRecordIT {

  @Autowired
  public MockMvc mockMvc;

  @Test
  public void testGetPersonInfo() throws Exception {


    mockMvc.perform(get("/personInfo").param("firstName", "Mario").param("lastName", "Red"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("firstName", is("Mario")))
        .andExpect(jsonPath("lastName", is("Red")))
        .andExpect(jsonPath("email", is("mario@email.com")));

    mockMvc.perform(get("/personInfo").param("firstName", "Mario")).andExpect(status().isBadRequest());
  }

  @Test
  public void testCreateUpdateDeleteMedicalRecord() throws Exception {

    ObjectMapper objectMapperCreate = new ObjectMapper();
    ObjectNode objectNodeCreate = objectMapperCreate.createObjectNode();
    objectNodeCreate.set("firstName", TextNode.valueOf("Bowser"));
    objectNodeCreate.set("lastName", TextNode.valueOf("Yellow"));
    objectNodeCreate.set("birthdate", TextNode.valueOf("08/06/1945"));

    mockMvc.perform(post("/medicalRecord")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectNodeCreate.toString()))
        .andExpect(status().isCreated());

    ObjectMapper objectMapperUpdate = new ObjectMapper();
    ObjectNode objectNodeUpdate = objectMapperUpdate.createObjectNode();
    objectNodeUpdate.set("firstName", TextNode.valueOf("Bowser"));
    objectNodeUpdate.set("lastName", TextNode.valueOf("Yellow"));
    objectNodeUpdate.set("birthdate", TextNode.valueOf("08/06/1900"));

    mockMvc.perform(put("/medicalRecord")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectNodeUpdate.toString()))
        .andExpect(status().isCreated());

    mockMvc.perform(delete("/medicalRecord")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectNodeCreate.toString()))
        .andExpect(status().isOk());

  }

}
