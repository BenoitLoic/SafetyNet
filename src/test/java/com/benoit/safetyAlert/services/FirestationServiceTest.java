package com.benoit.safetyAlert.services;

import com.benoit.safetyAlert.dao.FirestationDao;
import com.benoit.safetyAlert.dto.PersonInfo;
import com.benoit.safetyAlert.exceptions.DataAlreadyExistException;
import com.benoit.safetyAlert.exceptions.DataNotFindException;
import com.benoit.safetyAlert.exceptions.InvalidArgumentException;
import com.benoit.safetyAlert.model.Firestation;
import com.benoit.safetyAlert.model.Medicalrecords;
import com.benoit.safetyAlert.model.Persons;
import com.benoit.safetyAlert.repository.DataRepository;
import com.benoit.safetyAlert.utility.CalculateAge;
import com.google.inject.internal.util.Iterables;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FirestationServiceTest {
  private final String firstNameTest = "testName";
  private final String lastNameTest = "testLastName";
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
  private String birthdateChildTest;
  private String birthdateAdultTest;

  @BeforeEach
  void setUp() {
    DateTimeFormatter formattedDate = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    birthdateChildTest = LocalDate.now().minusYears(2).format(formattedDate);
    birthdateAdultTest = LocalDate.now().minusYears(20).format(formattedDate);
  }

  @Test
  void getPhoneNumberValidWithOneFirestation_ShouldReturnAListWithTwoPerson() {

    //    GIVEN
    Persons person1 = new Persons();
    Persons person2 = new Persons();
    person1.setPhone("0000");
    person2.setPhone("1111");
    List<Persons> personsList = asList(person1, person2);
    Firestation firestationTest = new Firestation(stationTest, addressTest, personsList);
    List<Firestation> firestationList = asList(firestationTest);
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
    List<Persons> personsList1 = asList(person1, person2);
    List<Persons> personsList2 = asList(person3);
    Firestation firestationTest1 = new Firestation(stationTest, addressTest, personsList1);
    Firestation firestationTest2 = new Firestation(stationTest, addressTest, personsList2);

    List<Firestation> firestationList = asList(firestationTest1, firestationTest2);
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
    List<Persons> personsList = asList(person1, person2);

    Firestation firestationTest1 = new Firestation(stationTest, addressTest, personsList);
    Firestation firestationTest2 = new Firestation("2", addressTest, personsList);
    Firestation firestationTest3 = new Firestation("3", addressTest, personsList);
    List<Firestation> firestationList =
        asList(firestationTest1, firestationTest2, firestationTest3);
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
  void getPhoneNumberWithPhoneNull_ShouldReturnAListOfTwoPersonInfo() {

    //    GIVEN
    Persons person1 = new Persons();
    Persons person2 = new Persons();
    person1.setPhone("0000");
    person2.setPhone(null);
    List<Persons> personsList = asList(person1, person2);
    Firestation firestationTest = new Firestation(stationTest, addressTest, personsList);
    List<Firestation> firestationList = asList(firestationTest);
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

    List<Persons> personsList =
        asList(
            new Persons(
                firstNameTest,
                lastNameTest,
                null,
                null,
                null,
                null,
                null,
                null,
                new Medicalrecords(null, null, birthdateChildTest)));

    Firestation firestation = new Firestation(stationTest, addressTest, personsList);
    //    WHEN
    when(dataRepository.getFirestations()).thenReturn(asList(firestation));
    Collection<PersonInfo> processTest =
        firestationService.getPersonCoveredByFireStation(stationTest);
    //    THEN

    for (PersonInfo personInfo : processTest) {
      if (personInfo.getFirstName() == null) {
        assertThat(personInfo.getNumberOfChild()).isEqualTo(1);
        assertThat(personInfo.getNumberOfAdult()).isEqualTo(0);
      } else {
        assertThat(personInfo.getFirstName()).isEqualTo(firstNameTest);
      }
    }
  }

  @Test
  void
  getPersonCoveredByFireStationWithMultipleFirestation_ShouldReturnAListWithThreeAdultAndTwoChild() {

    //  GIVEN

    List<Persons> adultList1 = new ArrayList<>();
    List<Persons> childList2 = new ArrayList<>();

    for (int i = 0; i < 3; i++) {
      adultList1.add(
          new Persons(
              firstNameTest + i,
              lastNameTest + i,
              null,
              null,
              null,
              null,
              null,
              null,
              new Medicalrecords(null, null, birthdateAdultTest)));
    }
    for (int i = 0; i < 2; i++) {
      childList2.add(
          new Persons(
              firstNameTest + i,
              lastNameTest + i,
              null,
              null,
              null,
              null,
              null,
              null,
              new Medicalrecords(null, null, birthdateChildTest)));
    }
    List<Firestation> firestationList =
        asList(
            new Firestation(stationTest, addressTest, adultList1),
            new Firestation(stationTest, addressTest, childList2));
    //  WHEN
    when(dataRepository.getFirestations()).thenReturn(firestationList);
    Collection<PersonInfo> processTest =
        firestationService.getPersonCoveredByFireStation(stationTest);
    //  THEN
    assertThat(processTest.size()).isEqualTo(6); // 5 person + 1 for adult/child counter
    for (PersonInfo personInfo : processTest) {
      if (personInfo.getFirstName() == null) {
        assertThat(personInfo.getNumberOfAdult()).isEqualTo(3);
        assertThat(personInfo.getNumberOfChild()).isEqualTo(2);
      }
    }
  }

  // Valid avec 1 seule station qui a 1 personne
  @Test
  void getFloodStations_ShouldReturnOnePersonInfo() {
    //    GIVEN
    List<Persons> personsList =
        asList(
            new Persons(
                firstNameTest,
                lastNameTest,
                null,
                null,
                null,
                null,
                null,
                null,
                new Medicalrecords(null, null, birthdateChildTest)));
    List<Firestation> firestationList =
        asList(new Firestation(stationTest, addressTest, personsList));
    //    WHEN
    when(dataRepository.getFirestations()).thenReturn(firestationList);
    Collection<PersonInfo> processTest = firestationService.getFloodStations(asList(stationTest));
    //    THEN
    assertThat(processTest.size()).isEqualTo(1);
    assertThat(processTest.iterator().next().getFirstName()).isEqualTo(firstNameTest);
  }

  // Valid avec 2 stations  de 1 personne
  @Test
  void getFloodStations_ShouldReturnTwoPersonInfo() {
    //    GIVEN
    List<Persons> personsList1 =
        asList(
            new Persons(
                firstNameTest,
                lastNameTest,
                null,
                null,
                null,
                null,
                null,
                null,
                new Medicalrecords(null, null, birthdateChildTest)));
    List<Persons> personsList2 =
        asList(
            new Persons(
                firstNameTest + 1,
                lastNameTest + 1,
                null,
                null,
                null,
                null,
                null,
                null,
                new Medicalrecords(null, null, birthdateChildTest)));
    List<Firestation> firestationList =
        asList(
            new Firestation(stationTest, addressTest, personsList1),
            new Firestation(stationTest, addressTest, personsList2));
    //    WHEN
    when(dataRepository.getFirestations()).thenReturn(firestationList);
    Collection<PersonInfo> processTest = firestationService.getFloodStations(asList(stationTest));
    //    THEN
    assertThat(processTest.size()).isEqualTo(2);
  }

  // Valid avec 2 stations de 2 personnes
  @Test
  void getFloodStations_ShouldReturnFourPersonInfo() {
    //    GIVEN
    List<Persons> personsList1 = new ArrayList<>();
    for (int i = 0; i < 2; i++) {
      //
      personsList1.add(
          new Persons(
              firstNameTest + i,
              lastNameTest + i,
              null,
              null,
              null,
              null,
              null,
              null,
              new Medicalrecords(null, null, birthdateChildTest)));
    }
    List<Persons> personsList2 = new ArrayList<>();
    for (int i = 2; i < 4; i++) {
      //
      personsList1.add(
          new Persons(
              firstNameTest + i,
              lastNameTest + i,
              null,
              null,
              null,
              null,
              null,
              null,
              new Medicalrecords(null, null, birthdateChildTest)));
    }

    List<Firestation> firestationList =
        asList(
            new Firestation(stationTest, addressTest, personsList1),
            new Firestation(stationTest, addressTest, personsList2));
    //    WHEN
    when(dataRepository.getFirestations()).thenReturn(firestationList);
    Collection<PersonInfo> processTest = firestationService.getFloodStations(asList(stationTest));
    //    THEN
    assertThat(processTest.size()).isEqualTo(4);
  }

  // Valid avec 2 stations de 2 personnes identiques
  @Test
  void getFloodStationsWithDuplicate_ShouldReturnTwoPersonInfo() {
    //    GIVEN
    List<Persons> personsList1 = new ArrayList<>();
    for (int i = 0; i < 2; i++) {
      //
      personsList1.add(
          new Persons(
              firstNameTest + i,
              lastNameTest + i,
              null,
              null,
              null,
              null,
              null,
              null,
              new Medicalrecords(null, null, birthdateChildTest)));
    }
    List<Firestation> firestationList =
        asList(
            new Firestation(stationTest, addressTest, personsList1),
            new Firestation(stationTest, addressTest, personsList1));
    //    WHEN
    when(dataRepository.getFirestations()).thenReturn(firestationList);
    Collection<PersonInfo> processTest = firestationService.getFloodStations(asList(stationTest));
    //    THEN
    assertThat(processTest.size()).isEqualTo(2);
  }

  // avec une liste de station vide
  @Test
  void getFloodStationsWithEmptyArgs_ShouldReturnEmptyList() {
    //    GIVEN
    List<Persons> personsList =
        asList(
            new Persons(
                firstNameTest,
                lastNameTest,
                null,
                null,
                null,
                null,
                null,
                null,
                new Medicalrecords(null, null, birthdateChildTest)));
    List<Firestation> firestationList =
        asList(new Firestation(stationTest, addressTest, personsList));
    //    WHEN
    when(dataRepository.getFirestations()).thenReturn(firestationList);
    Collection<PersonInfo> processTest = firestationService.getFloodStations(new ArrayList<>());
    //    THEN
    assertThat(processTest).isEmpty();
  }

  @Test
  void getFloodStationsWhenRepositoryReturnEmptyList_ShouldReturnEmptyList() {
    //    GIVEN

    //    WHEN
    when(dataRepository.getFirestations()).thenReturn(new ArrayList<>());
    Collection<PersonInfo> processTest = firestationService.getFloodStations(asList(stationTest));
    //    THEN
    assertThat(processTest).isEmpty();
  }

  // Valid
  @Test
  void createFirestationValid() {
    //    GIVEN
    Firestation firestation = new Firestation();
    firestation.setAddress(addressTest);
    firestation.setStation(stationTest);
    //    WHEN
    when(dataRepository.getFirestations()).thenReturn(asList(new Firestation()));
    when(firestationDao.createFirestation(any(Firestation.class))).thenReturn(true);
    //    THEN
    assertThat(firestationService.createFirestation(firestation)).isTrue();
  }

  //   avec un arg = null
  @Test
  void createFirestationWithNullArg_ShouldThrowInvalidArgumentException() {
    //    GIVEN

    //    WHEN
    when(dataRepository.getFirestations()).thenReturn(Arrays.asList(new Firestation()));
    //    THEN

    assertThrows(InvalidArgumentException.class, () -> firestationService.createFirestation(null));
  }

  // avec arg = new
  @Test
  void createFirestationWithNullValue_ShouldThrowInvalidArgumentException() {
    //    GIVEN

    //    WHEN
    when(dataRepository.getFirestations()).thenReturn(Arrays.asList(new Firestation()));
    //    THEN
    assertThrows(
        InvalidArgumentException.class,
        () -> firestationService.createFirestation(new Firestation()));
  }

  // avec firestation qui existe déja
  @Test
  void createFirestationInvalid_ShouldThrowDataAlreadyExistException() {
    //    GIVEN
    Firestation firestation = new Firestation();
    firestation.setAddress(addressTest);
    firestation.setStation(stationTest);
    //    WHEN
    when(dataRepository.getFirestations()).thenReturn(asList(firestation));
    //    THEN
    assertThrows(
        DataAlreadyExistException.class, () -> firestationService.createFirestation(firestation));
  }

  // Valid
  @Test
  void deleteFirestationValid() {
    //    GIVEN
    Firestation firestation = new Firestation();
    firestation.setAddress(addressTest);
    firestation.setStation(stationTest);
    //    WHEN
    when(dataRepository.getFirestations()).thenReturn(asList(firestation));
    when(firestationDao.deleteFirestation(any(Firestation.class))).thenReturn(true);
    //    THEN
    assertThat(firestationService.deleteFirestation(firestation)).isTrue();
  }

  // arg = null
  @Test
  void deleteFirestationWithNullArg_ShouldThrow() {
    //    GIVEN

    //    WHEN
    when(dataRepository.getFirestations()).thenReturn(asList(new Firestation()));
    //    THEN
    assertThrows(InvalidArgumentException.class, () -> firestationService.deleteFirestation(null));
  }

  // arg = new
  @Test
  void deleteFirestationWithNullValue_ShouldThrowDataNotFindException() {
    //    GIVEN
    Firestation firestation = new Firestation();

    //    WHEN
    when(dataRepository.getFirestations()).thenReturn(asList(firestation));

    //    THEN
    assertThrows(
        InvalidArgumentException.class, () -> firestationService.deleteFirestation(firestation));
  }

  // avec firestation qui n'existe pas
  @Test
  void deleteFirestationInvalid_ShouldThrowDataNotFindExeption() {
    //    GIVEN
    Firestation firestation = new Firestation(addressTest, stationTest, new ArrayList<>());
    //    WHEN
    when(dataRepository.getFirestations()).thenReturn(asList(new Firestation()));
    //    THEN
    assertThrows(
        DataNotFindException.class, () -> firestationService.deleteFirestation(firestation));
  }
}
