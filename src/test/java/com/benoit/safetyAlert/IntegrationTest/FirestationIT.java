package com.benoit.safetyAlert.IntegrationTest;

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
public class FirestationIT {
  @Autowired
  MockMvc mockMvc;

  @Test
  public void testPhoneAlert() throws Exception {
    mockMvc.perform(get("/phoneAlert")
        .param("station", "3"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].phone", is("111-111-1111")))
        .andExpect(jsonPath("$[1].phone", is("000-000-0000")));
  }

  @Test
  public void testFirestationCoverage() throws Exception {
    mockMvc.perform(get("/firestation")
        .param("stationNumber", "2"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].firstName", is("Toad")))
        .andExpect(jsonPath("$[1].numberOfAdult", is(1)));
  }

  @Test
  public void testFloodStations() throws Exception {

    mockMvc.perform(get("/flood/stations")
        .param("stations", "2,3"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].address", is("1 Bad Turtle street")))
        .andExpect(jsonPath("$[1].address", is("1 castle rd")))
        .andExpect(jsonPath("$[0].personInfos.[0].firstName", is("Bowser")));

  }

  @Test
  public void testCreateUpdateDeleteFirestation() throws Exception {

    ObjectMapper objectMapperCreate = new ObjectMapper();
    ObjectNode createFirestation = objectMapperCreate.createObjectNode();
    createFirestation.set("station", TextNode.valueOf("5"));
    createFirestation.set("address", TextNode.valueOf("test address"));
    mockMvc.perform(post("/firestation")
        .contentType(MediaType.APPLICATION_JSON)
        .content(createFirestation.toString()))
        .andExpect(status().isCreated());

    ObjectMapper objectMapperUpdate = new ObjectMapper();
    ObjectNode updateFirestation = objectMapperUpdate.createObjectNode();
    updateFirestation.set("station", TextNode.valueOf("6"));
    updateFirestation.set("address", TextNode.valueOf("test address"));
    mockMvc.perform(put("/firestation")
        .contentType(MediaType.APPLICATION_JSON)
        .content(updateFirestation.toString()))
        .andExpect(status().isCreated());

    ObjectMapper objectMapperDelete = new ObjectMapper();
    ObjectNode deleteFirestation = objectMapperDelete.createObjectNode();
    deleteFirestation.set("station", TextNode.valueOf("6"));
    deleteFirestation.set("address", TextNode.valueOf("test address"));
    mockMvc.perform(delete("/firestation")
        .contentType(MediaType.APPLICATION_JSON)
        .content(deleteFirestation.toString()))
        .andExpect(status().isOk());

  }

}
