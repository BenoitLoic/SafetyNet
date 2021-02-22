package com.safetynet.safetyalerts.services;

import com.safetynet.safetyalerts.dao.PersonDao;
import com.safetynet.safetyalerts.dto.PersonInfo;
import com.safetynet.safetyalerts.exceptions.DataAlreadyExistException;
import com.safetynet.safetyalerts.exceptions.DataNotFindException;
import com.safetynet.safetyalerts.model.Medicalrecords;
import com.safetynet.safetyalerts.model.Persons;
import com.safetynet.safetyalerts.repository.DataRepository;
import com.safetynet.safetyalerts.utility.CalculateAge;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@edu.umd.cs.findbugs.annotations.SuppressFBWarnings("SS_SHOULD_BE_STATIC")
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
      Medicalrecords medicalrecord = new Medicalrecords();
      medicalrecord.setFirstName(firstNameTest + i);
      medicalrecord.setLastName(lastNameTest + i);
      medicalrecord.setBirthdate(birthdateTest + i);
      person.setMedicalrecords(medicalrecord);

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
    Medicalrecords medicalrecordChild = new Medicalrecords();
    medicalrecordChild.setBirthdate("child");
    child.setMedicalrecords(medicalrecordChild);
    Persons adult = new Persons();
    adult.setFirstName("adultFirstName");
    adult.setLastName("adultLastName");
    adult.setAddress(addressTest);
    Medicalrecords medicalrecordAdult = new Medicalrecords();
    medicalrecordAdult.setBirthdate("adult");
    adult.setMedicalrecords(medicalrecordAdult);
    //    WHEN
    when(dataRepositoryMock.getPersons()).thenReturn(asList(child, adult));
    when(calculateAgeMock.calculateAge("child")).thenReturn(5);
    when(calculateAgeMock.calculateAge("adult")).thenReturn(30);

    //    THEN
    assertThat(personService.getChildAlert(addressTest).size()).isEqualTo(1);
    Assertions.assertThat(personService.getChildAlert(addressTest).iterator().next().getFirstName())
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
    Medicalrecords medicalrecordChild = new Medicalrecords();
    medicalrecordChild.setBirthdate("child");
    child.setMedicalrecords(medicalrecordChild);
    Persons adult = new Persons();
    adult.setFirstName("adultFirstName");
    adult.setLastName(lastNameTest);
    adult.setAddress(addressTest);
    Medicalrecords medicalrecordAdult = new Medicalrecords();
    medicalrecordAdult.setBirthdate("adult");
    adult.setMedicalrecords(medicalrecordAdult);
    //    WHEN
    when(dataRepositoryMock.getPersons()).thenReturn(asList(child, adult));
    when(calculateAgeMock.calculateAge("child")).thenReturn(5);
    when(calculateAgeMock.calculateAge("adult")).thenReturn(30);
    Collection<PersonInfo> processTest = personService.getChildAlert(addressTest);
    //    THEN
    assertThat(personService.getChildAlert(addressTest).size()).isEqualTo(1);
    Assertions.assertThat(personService.getChildAlert(addressTest).iterator().next().getFirstName())
        .isEqualTo(firstNameTest);
    Assertions.assertThat(personService.getChildAlert(addressTest).iterator().next().getFamily().size())
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
    Medicalrecords medicalrecordAdult = new Medicalrecords();
    medicalrecordAdult.setBirthdate("adult");
    child.setMedicalrecords(medicalrecordAdult);
    Persons adult = new Persons();
    adult.setFirstName("adultFirstName");
    adult.setLastName(lastNameTest);
    adult.setAddress(addressTest);
    adult.setMedicalrecords(medicalrecordAdult);
    //    WHEN
    when(dataRepositoryMock.getPersons()).thenReturn(asList(child, adult));

    when(calculateAgeMock.calculateAge("adult")).thenReturn(30);

    //    THEN
    Assertions.assertThat(personService.getChildAlert(addressTest)).isEmpty();
  }

  //  renvoi une liste vide si le repo renvoi une liste vide
  @Test
  void getChildAlertWhenRepositoryReturnAnEmptyList_ShouldReturnAnEmptyList() {
    //    GIVEN

    //    WHEN
    when(dataRepositoryMock.getPersons()).thenReturn(new ArrayList<>());

    //    THEN
    Assertions.assertThat(personService.getChildAlert(addressTest)).isEmpty();
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
    List<Persons> personsList = new ArrayList<>();
    personsList.add(person);
    //    WHEN
    when(dataRepositoryMock.getPersons()).thenReturn(personsList);
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
    List<Persons> personsList = new ArrayList<>();
    personsList.add(person);
    //    WHEN
    when(dataRepositoryMock.getPersons()).thenReturn(personsList);
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
    List<Persons> personsList = new ArrayList<>();

    //    WHEN
    when(dataRepositoryMock.getPersons()).thenReturn(personsList);
    //    THEN
    assertThrows(DataNotFindException.class, () -> personService.deletePerson(person));
  }
}
