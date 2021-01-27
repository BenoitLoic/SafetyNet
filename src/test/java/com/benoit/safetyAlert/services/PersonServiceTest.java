package com.benoit.safetyAlert.services;

import com.benoit.safetyAlert.dao.PersonDao;
import com.benoit.safetyAlert.model.Persons;
import com.benoit.safetyAlert.repository.DataRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

  private final String cityTest = "city";
  @Mock
  DataRepository dataRepositoryMock;
  @Mock
  PersonDao personDaoMock;
  @InjectMocks
  PersonServiceImpl personService;

  @Test
  void getCommunityEmailValid_ShouldReturnAListWithTwoPersons() {

    //    GIVEN
    Persons person1 = new Persons();
    person1.setEmail("testEmail1");
    person1.setCity(cityTest);
    Persons person2 = new Persons();
    person2.setEmail("testEmail2");
    person2.setCity(cityTest);
    List<Persons> personsList = Arrays.asList(person1, person2);

    //    WHEN
    when(dataRepositoryMock.getPersons()).thenReturn(personsList);

    Collection<Persons> processTest = personService.getCommunityEmail(cityTest);
    //    THEN
    assertThat(processTest.size()).isEqualTo(2);
  }

  @Test
  void getCommunityEmailValidWithDuplicate_ShouldReturnAListWithTwoPersons() {

    //    GIVEN
    Persons person1 = new Persons();
    person1.setEmail("testEmail1");
    person1.setCity(cityTest);
    Persons person2 = new Persons();
    person2.setEmail("testEmail2");
    person2.setCity(cityTest);
    List<Persons> personsList = Arrays.asList(person1, person2, person1);

    //    WHEN
    when(dataRepositoryMock.getPersons()).thenReturn(personsList);
    Collection<Persons> processTest = personService.getCommunityEmail(cityTest);
    //    THEN
    assertThat(processTest.size()).isEqualTo(2);
  }

  @Test
  void getCommunityEmailWithEmptyOrNullArgs_ShouldReturnEmptyList() {

    //    GIVEN
    Persons person1 = new Persons();
    person1.setEmail("testEmail1");
    person1.setCity(cityTest);
    Persons person2 = new Persons();
    person2.setEmail("testEmail2");
    person2.setCity(cityTest);
    List<Persons> personsList = Arrays.asList(person1, person2, person1);
    //    WHEN
    when(dataRepositoryMock.getPersons()).thenReturn(personsList);
    Collection<Persons> processTest1 = personService.getCommunityEmail("");
    Collection<Persons> processTest2 = personService.getCommunityEmail(null);
    //    THEN
    assertThat(processTest1).isEmpty();
    assertThat(processTest2).isEmpty();
  }

  @Test
  void getCommunityEmailWithEmptyOrNullEmail_ShouldReturnAListWithEmptyOrNullValue() {

    //    GIVEN
    Persons person1 = new Persons();
    person1.setEmail(null);
    person1.setCity(cityTest);
    Persons person2 = new Persons();
    person2.setEmail(" ");
    person2.setCity(cityTest);
    List<Persons> personsList = Arrays.asList(person1, person2);
    //    WHEN
    when(dataRepositoryMock.getPersons()).thenReturn(personsList);
    Collection<Persons> processTest = personService.getCommunityEmail(cityTest);

    //    THEN
    assertThat(processTest.size()).isEqualTo(2);
  }

  @Test
  void getCommunityEmailWhenRepositoryReturnEmptyList_ShouldReturnEmptyList() {

    //    GIVEN

    //    WHEN
    when(dataRepositoryMock.getPersons()).thenReturn(new ArrayList<>());
    Collection<Persons> processTest = personService.getCommunityEmail(cityTest);

    //    THEN
    assertThat(processTest).isEmpty();
  }

}