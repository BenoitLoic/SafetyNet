package com.safetynet.safetyalerts.IntegrationTest;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.safetynet.safetyalerts.model.Persons;
import com.safetynet.safetyalerts.repository.DataRepository;
import java.time.LocalDate;
import java.time.Period;
import org.assertj.core.api.Assertions;
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
public class PersonIT {

  @Autowired
  public MockMvc mockMvc;

  @Autowired
  DataRepository dataRepository;


  @Test
  public void testChildAlert() throws Exception {

    mockMvc.perform(get("/childAlert").param("address", "1 castle rd"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].firstName", is("Luigi")))
        .andExpect(jsonPath("$[0].lastName", is("Green")))
        .andExpect(jsonPath("$[0].age", is(Period.between(LocalDate.of(2014, 3, 6), LocalDate.now()).getYears())));
  }

  @Test
  public void testFire() throws Exception {

    mockMvc.perform(get("/fire").param("address", "5 mushroom av"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].firstName", is("Toad")))
        .andExpect(jsonPath("$[0].lastName", is("Redandwhite")))
        .andExpect(jsonPath("$[0].age", is(Period.between(LocalDate.of(2000, 3, 6), LocalDate.now()).getYears())));
  }

  @Test
  public void testCommunityEmail() throws Exception {

    mockMvc.perform(get("/communityEmail").param("city", "Nintendo"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].email", is("mario@email.com")));

  }


  @Test
  public void testCreateUpdateDeletePerson() throws Exception {


    //création d'une personne en Json
    ObjectMapper objectMapperCreate = new ObjectMapper();
    ObjectNode objectNode = objectMapperCreate.createObjectNode();
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

    //vérification si la personne a bien été ajouté dans les données
    Persons personCreated = new Persons();
    personCreated.setFirstName("Loic");
    personCreated.setLastName("Benoit");
    Assertions.assertThat(dataRepository.getPersons().contains(personCreated)).isTrue();

    //test d'ajout de la meme personne et verif si il y a bien conflit
    mockMvc.perform(post("/person").contentType(MediaType.APPLICATION_JSON).content(objectNode.toString()))
        .andExpect(status().isConflict());

    //test update de cette personne
    ObjectMapper objectMapperUpdate = new ObjectMapper();
    ObjectNode updatePerson = objectMapperUpdate.createObjectNode();
    updatePerson.set("firstName", TextNode.valueOf("Loic"));
    updatePerson.set("lastName", TextNode.valueOf("Benoit"));
    updatePerson.set("address", TextNode.valueOf("3 rue du Test"));
    updatePerson.set("email", TextNode.valueOf("update@mail.com"));

    mockMvc.perform(put("/person").contentType(MediaType.APPLICATION_JSON).content(updatePerson.toString()))
        .andExpect(status().isCreated());

    ObjectMapper objectMapperDelete = new ObjectMapper();
    ObjectNode personDelete = objectMapperDelete.createObjectNode();
    personDelete.set("firstName", TextNode.valueOf("Loic"));
    personDelete.set("lastName", TextNode.valueOf("Benoit"));


    mockMvc.perform(delete("/person")
        .contentType(MediaType.APPLICATION_JSON)
        .content(personDelete.toString()))
        .andExpect(status().isOk());
    Assertions.assertThat(dataRepository.getPersons().contains(personCreated)).isFalse();

  }


}
