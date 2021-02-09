package com.benoit.safetyAlert.services;

import com.benoit.safetyAlert.dao.PersonDao;
import com.benoit.safetyAlert.dto.PersonInfo;
import com.benoit.safetyAlert.exceptions.DataAlreadyExistException;
import com.benoit.safetyAlert.exceptions.DataNotFindException;
import com.benoit.safetyAlert.exceptions.InvalidArgumentException;
import com.benoit.safetyAlert.model.Firestation;
import com.benoit.safetyAlert.model.Medicalrecords;
import com.benoit.safetyAlert.model.Persons;
import com.benoit.safetyAlert.repository.DataRepository;
import com.benoit.safetyAlert.utility.CalculateAge;
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

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PersonServiceTest {


  @Mock
  DataRepository dataRepositoryMock;
  @Mock
  PersonDao personDaoMock;
  @Mock
  CalculateAge calculateAgeMock;
  @InjectMocks
  PersonServiceImpl personService;

  private final String firstNameTest = "testName";
  private final String lastNameTest = "test Last Name";
  private final String birthdateTest = "01/01/2001";
  private final String addressTest = "1 rue du test";
  private final String cityTest = "city";

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

  // valid
  @Test
  void getFireAddressValid_ShouldReturnAListOfTwo() {
    //    GIVEN
    List<Persons> personsList = new ArrayList<>();
    for (int i = 0; i < 2; i++) {
      Persons person = new Persons();
      person.setFirstName(firstNameTest + i);
      person.setLastName(lastNameTest + i);
      person.setAddress(addressTest);
      person.setMedicalrecords(new Medicalrecords(firstNameTest, lastNameTest, birthdateTest));

      personsList.add(person);
    }
    Persons person = new Persons();
    person.setFirstName("ab");
    person.setLastName("cdef");
    person.setAddress("anotherAddressTest");
    personsList.add(person);
    //    WHEN
    when(dataRepositoryMock.getPersons()).thenReturn(personsList);
    when(calculateAgeMock.calculateAge(any())).thenReturn(20);
    Collection<PersonInfo> processTest = personService.getFireAddress(addressTest);
    //    THEN
    assertThat(processTest.size()).isEqualTo(2);
    assertThat(processTest.iterator().next().getFirstName()).contains(firstNameTest);
  }

  // cas ou le repo renvoi une liste vide
  @Test
  void getFireAddressWhenRepositoryReturnAnEmptyList_ShouldReturnAnEmptyList() {
    //    GIVEN
    List<Persons> personsList = new ArrayList<>();

    //    WHEN
    when(dataRepositoryMock.getPersons()).thenReturn(personsList);

    Collection<PersonInfo> processTest = personService.getFireAddress(addressTest);
    //    THEN
    assertThat(processTest).isEmpty();
  }

  // cas ou address est nul ou vide
  @Test
  void getFireAddressWhenArgumentIsEmpty_ShouldReturnAnEmptyList() {
    //    GIVEN
    List<Persons> personsList = new ArrayList<>();
    for (int i = 0; i < 2; i++) {
      Persons person = new Persons();
      person.setFirstName(firstNameTest + i);
      person.setLastName(lastNameTest + i);
      person.setAddress(addressTest);

      personsList.add(person);
    }
    Persons person = new Persons();
    person.setFirstName("ab");
    person.setLastName("cdef");
    person.setAddress("addressTest");
    personsList.add(person);
    //    WHEN
    when(dataRepositoryMock.getPersons()).thenReturn(personsList);


    Collection<PersonInfo> processTest = personService.getFireAddress("");
    //    THEN

    assertThat(processTest).isEmpty();
  }


  // valid : ajoute un enfant mais pas un adulte
  @Test
  void getChildAlertValid_ShouldReturnOneChildAndZeroAdult() {
    //    GIVEN
    Persons child = new Persons();
    child.setFirstName(firstNameTest);
    child.setLastName(lastNameTest);
    child.setAddress(addressTest);
    child.setMedicalrecords(new Medicalrecords(null, null, "child"));
    Persons adult = new Persons();
    adult.setFirstName("adultFirstName");
    adult.setLastName("adultLastName");
    adult.setAddress(addressTest);
    adult.setMedicalrecords(new Medicalrecords(null, null, "adult"));
    //    WHEN
    when(dataRepositoryMock.getPersons()).thenReturn(asList(child, adult));
    when(calculateAgeMock.calculateAge("child")).thenReturn(5);
    when(calculateAgeMock.calculateAge("adult")).thenReturn(30);

    //    THEN
    assertThat(personService.getChildAlert(addressTest).size()).isEqualTo(1);
    assertThat(personService.getChildAlert(addressTest).iterator().next().getFirstName())
        .isEqualTo(firstNameTest);
  }

  // valid ajoute un enfant et un membre de sa famille
  @Test
  void getChildAlertValid_ShouldReturnOneChildAndOneFamilyMember() {
    //    GIVEN
    Persons child = new Persons();
    child.setFirstName(firstNameTest);
    child.setLastName(lastNameTest);
    child.setAddress(addressTest);
    child.setMedicalrecords(new Medicalrecords(null, null, "child"));
    Persons adult = new Persons();
    adult.setFirstName("adultFirstName");
    adult.setLastName(lastNameTest);
    adult.setAddress(addressTest);
    adult.setMedicalrecords(new Medicalrecords(null, null, "adult"));
    //    WHEN
    when(dataRepositoryMock.getPersons()).thenReturn(asList(child, adult));
    when(calculateAgeMock.calculateAge("child")).thenReturn(5);
    when(calculateAgeMock.calculateAge("adult")).thenReturn(30);
    Collection<PersonInfo> processTest = personService.getChildAlert(addressTest);
    //    THEN
    assertThat(personService.getChildAlert(addressTest).size()).isEqualTo(1);
    assertThat(personService.getChildAlert(addressTest).iterator().next().getFirstName())
        .isEqualTo(firstNameTest);
    assertThat(personService.getChildAlert(addressTest).iterator().next().getFamily().size())
        .isEqualTo(1);

    for (PersonInfo personInfo : processTest) {
      assertThat(personInfo.getFirstName()).isEqualTo(firstNameTest);
      for (PersonInfo personInf : personInfo.getFamily()) {
        assertThat(personInf.getFirstName()).isEqualTo("adultFirstName");
      }
    }
  }

  //  renvoi une liste vide si pas d'enfant
  @Test
  void getChildAlertValidWithNoChild_ShouldReturnAnEmptyList() {
    //    GIVEN
    Persons child = new Persons();
    child.setFirstName(firstNameTest);
    child.setLastName(lastNameTest);
    child.setAddress(addressTest);
    child.setMedicalrecords(new Medicalrecords(null, null, "adult"));
    Persons adult = new Persons();
    adult.setFirstName("adultFirstName");
    adult.setLastName(lastNameTest);
    adult.setAddress(addressTest);
    adult.setMedicalrecords(new Medicalrecords(null, null, "adult"));
    //    WHEN
    when(dataRepositoryMock.getPersons()).thenReturn(asList(child, adult));

    when(calculateAgeMock.calculateAge("adult")).thenReturn(30);

    //    THEN
    assertThat(personService.getChildAlert(addressTest)).isEmpty();
  }

  //  renvoi une liste vide si le repo renvoi une liste vide
  @Test
  void getChildAlertWhenRepositoryReturnAnEmptyList_ShouldReturnAnEmptyList() {
    //    GIVEN

    //    WHEN
    when(dataRepositoryMock.getPersons()).thenReturn(new ArrayList<>());

    //    THEN
    assertThat(personService.getChildAlert(addressTest)).isEmpty();
  }

  // Valid
  @Test
  void createPersonValid() {
    //    GIVEN
    Persons person = new Persons();
    person.setFirstName(firstNameTest);
    person.setLastName(lastNameTest);
    //    WHEN
    when(dataRepositoryMock.getPersons()).thenReturn(new ArrayList<>());
    when(personDaoMock.createPerson(any())).thenReturn(true);
    //    THEN
    assertThat(personService.createPerson(person)).isTrue();
  }


  // avec firestation qui existe déja
  @Test
  void createPersonInvalid_ShouldThrowDataAlreadyExistException() {
    //    GIVEN
    Persons person = new Persons();
    person.setFirstName(firstNameTest);
    person.setLastName(lastNameTest);
    //    WHEN
    when(dataRepositoryMock.getPersons()).thenReturn(asList(person));
    //    THEN
    assertThrows(DataAlreadyExistException.class, () -> personService.createPerson(person));
  }

  // Valid
  @Test
  void deletePersonValid() {
    //    GIVEN
    Persons person = new Persons();
    person.setFirstName(firstNameTest);
    person.setLastName(lastNameTest);
    //    WHEN
    when(dataRepositoryMock.getPersons()).thenReturn(asList(person));
    when(personDaoMock.deletePerson(any())).thenReturn(true);
    //    THEN
    assertThat(personService.deletePerson(person)).isTrue();
  }


  // avec firestation qui n'existe pas
  @Test
  void deletePersonInvalid_ShouldThrowDataNotFindExeption() {
    //    GIVEN
    Persons person = new Persons();
    person.setFirstName(firstNameTest);
    person.setLastName(lastNameTest);
    //    WHEN
    when(dataRepositoryMock.getPersons()).thenReturn(asList(new Persons()));
    //    THEN
    assertThrows(DataNotFindException.class, () -> personService.deletePerson(person));
  }
}
