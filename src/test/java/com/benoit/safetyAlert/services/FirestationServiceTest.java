package com.benoit.safetyAlert.services;

import com.benoit.safetyAlert.dao.FirestationDao;
import com.benoit.safetyAlert.dto.PersonInfo;
import com.benoit.safetyAlert.model.Firestation;
import com.benoit.safetyAlert.model.Medicalrecords;
import com.benoit.safetyAlert.model.Persons;
import com.benoit.safetyAlert.repository.DataRepository;
import com.benoit.safetyAlert.utility.CalculateAge;
import com.google.inject.internal.util.Iterables;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FirestationServiceTest {

  private final String stationTest = "5";
  private final String addressTest = "1 rue du Test";
  @Mock
  DataRepository dataRepository;
  @Mock
  FirestationDao firestationDao;
  @Mock
  CalculateAge calculateAgeMock;
  @InjectMocks
  FirestationServiceImpl firestationService;

  @Test
  void getPhoneNumberValidWithOneFirestation_ShouldReturnAListWithTwoPerson() {

    //    GIVEN
    Persons person1 = new Persons();
    Persons person2 = new Persons();
    person1.setPhone("0000");
    person2.setPhone("1111");
    List<Persons> personsList = Arrays.asList(person1, person2);
    Firestation firestationTest = new Firestation(stationTest, addressTest, personsList);
    List<Firestation> firestationList = Arrays.asList(firestationTest);
    //    WHEN
    when(dataRepository.getFirestations()).thenReturn(firestationList);
    //    THEN
    Collection<Persons> processTest = firestationService.getPhoneNumber(stationTest);
    assertThat(processTest.size()).isEqualTo(2);
    assertThat(processTest).contains(person1, person2);
  }

  @Test
  void getPhoneNumberValidWithTwoFirestation_ShouldReturnAListWithThreePerson() {

    //    GIVEN
    Persons person1 = new Persons();
    Persons person2 = new Persons();
    Persons person3 = new Persons();
    person1.setPhone("0000");
    person2.setPhone("1111");
    person3.setPhone("2222");
    List<Persons> personsList1 = Arrays.asList(person1, person2);
    List<Persons> personsList2 = Arrays.asList(person3);
    Firestation firestationTest1 = new Firestation(stationTest, addressTest, personsList1);
    Firestation firestationTest2 = new Firestation(stationTest, addressTest, personsList2);

    List<Firestation> firestationList = Arrays.asList(firestationTest1, firestationTest2);
    //    WHEN
    when(dataRepository.getFirestations()).thenReturn(firestationList);
    //    THEN
    Collection<Persons> processTest = firestationService.getPhoneNumber(stationTest);
    assertThat(processTest.size()).isEqualTo(3);
  }

  @Test
  void getPhoneNumberValidWithThreeDifferentFirestation_ShouldReturnAListWithTwoPerson() {

    //    GIVEN
    Persons person1 = new Persons();
    Persons person2 = new Persons();
    person1.setPhone("0000");
    person2.setPhone("1111");
    List<Persons> personsList = Arrays.asList(person1, person2);

    Firestation firestationTest1 = new Firestation(stationTest, addressTest, personsList);
    Firestation firestationTest2 = new Firestation("2", addressTest, personsList);
    Firestation firestationTest3 = new Firestation("3", addressTest, personsList);
    List<Firestation> firestationList =
        Arrays.asList(firestationTest1, firestationTest2, firestationTest3);
    //    WHEN
    when(dataRepository.getFirestations()).thenReturn(firestationList);
    //    THEN
    Collection<Persons> processTest = firestationService.getPhoneNumber(stationTest);
    assertThat(processTest.size()).isEqualTo(2);
  }

  @Test
  void getPhoneNumberValid_WhenRepositoryReturnEmpty_ShouldReturnEmptyList() {

    //    GIVEN
    List<Firestation> firestationList = new ArrayList<>();
    //    WHEN
    when(dataRepository.getFirestations()).thenReturn(firestationList);
    //    THEN
    Collection<Persons> processTest = firestationService.getPhoneNumber(stationTest);
    assertThat(processTest).isEmpty();
  }

  @Test
  void getPhoneNumber() {

    //    GIVEN
    Persons person1 = new Persons();
    Persons person2 = new Persons();
    person1.setPhone("0000");
    person2.setPhone(null);
    List<Persons> personsList = Arrays.asList(person1, person2);
    Firestation firestationTest = new Firestation(stationTest, addressTest, personsList);
    List<Firestation> firestationList = Arrays.asList(firestationTest);
    //    WHEN
    when(dataRepository.getFirestations()).thenReturn(firestationList);
    //    THEN
    Collection<Persons> processTest = firestationService.getPhoneNumber(stationTest);
    assertThat(processTest.size()).isEqualTo(2);
    assertThat(processTest).contains(person1, person2);
  }

  @Test
  void getPersonCoveredByFireStationValid_ShouldReturnAListWithOnePersonInfo() {
    //    GIVEN
    String firstName = "testName";
    String lastName = "test last Name";
    String birthdate = "01/01/2000";
    List<Persons> personsList = new ArrayList<>();
    Persons person = new Persons();
    person.setFirstName(firstName);
    person.setLastName(lastName);
    Medicalrecords medicalrecord = new Medicalrecords();
    medicalrecord.setBirthdate(birthdate);
    person.setMedicalrecords(medicalrecord);
    personsList.add(person);
    Firestation firestation = new Firestation(stationTest, addressTest, personsList);
    //    WHEN
    when(dataRepository.getFirestations()).thenReturn(Arrays.asList(firestation));

    //    THEN
    Collection<PersonInfo> processTest =
        firestationService.getPersonCoveredByFireStation(stationTest);
    assertThat(processTest.iterator().next().getFirstName()).contains(firstName);
  }
}
