package com.safetynet.safetyalerts.services;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import com.safetynet.safetyalerts.dao.FirestationDao;
import com.safetynet.safetyalerts.dto.FirestationDto;
import com.safetynet.safetyalerts.dto.PersonInfo;
import com.safetynet.safetyalerts.exceptions.DataAlreadyExistException;
import com.safetynet.safetyalerts.exceptions.DataNotFindException;
import com.safetynet.safetyalerts.model.Firestation;
import com.safetynet.safetyalerts.model.Medicalrecords;
import com.safetynet.safetyalerts.model.Persons;
import com.safetynet.safetyalerts.repository.DataRepository;
import com.safetynet.safetyalerts.utility.CalculateAge;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@SuppressFBWarnings("SS_SHOULD_BE_STATIC")
@ExtendWith(MockitoExtension.class)
class FirestationServiceTest {

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
  private final String firstNameTest = "testName";
  private final String lastNameTest = "testLastName";
  private final String stationTest = "5";
  private final String addressTest = "1 rue du Test";

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
    Firestation firestationTest = new Firestation();
    firestationTest.setAddress(addressTest);
    firestationTest.setStation(stationTest);
    firestationTest.setPersons(personsList);
    List<Firestation> firestationList = new ArrayList<>();
    firestationList.add(firestationTest);
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
    List<Persons> personsList2 = new ArrayList<>();
    personsList2.add(person3);
    Firestation firestationTest1 = new Firestation();
    firestationTest1.setStation(stationTest);
    firestationTest1.setAddress(addressTest);
    firestationTest1.setPersons(personsList1);
    Firestation firestationTest2 = new Firestation();
    firestationTest2.setStation(stationTest);
    firestationTest2.setAddress(addressTest);
    firestationTest2.setPersons(personsList2);
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

    Firestation firestationTest1 = new Firestation();
    firestationTest1.setStation(stationTest);
    firestationTest1.setAddress(addressTest);
    firestationTest1.setPersons(personsList);
    Firestation firestationTest2 = new Firestation();
    firestationTest2.setStation(stationTest + 1);
    firestationTest2.setAddress(addressTest);
    firestationTest2.setPersons(personsList);
    Firestation firestationTest3 = new Firestation();
    firestationTest3.setStation(stationTest + 2);
    firestationTest3.setAddress(addressTest);
    firestationTest3.setPersons(personsList);

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
    Firestation firestationTest = new Firestation();
    firestationTest.setAddress(addressTest);
    firestationTest.setStation(stationTest);
    firestationTest.setPersons(personsList);
    List<Firestation> firestationList = new ArrayList<>();
    firestationList.add(firestationTest);
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
    Persons person = new Persons();
    person.setFirstName(firstNameTest);
    person.setLastName(lastNameTest);
    Medicalrecords medicalrecords = new Medicalrecords();
    medicalrecords.setBirthdate(birthdateChildTest);
    person.setMedicalrecords(medicalrecords);
    List<Persons> personsList = new ArrayList<>();
    personsList.add(person);

    Firestation firestation = new Firestation();
    firestation.setStation(stationTest);
    firestation.setAddress(addressTest);
    firestation.setPersons(personsList);
    List<Firestation> firestationList = new ArrayList<>();
    firestationList.add(firestation);
    //    WHEN
    when(dataRepository.getFirestations()).thenReturn(firestationList);
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
      Persons person = new Persons();
      person.setFirstName(firstNameTest + i);
      person.setLastName(lastNameTest + i);
      Medicalrecords medicalrecords = new Medicalrecords();
      medicalrecords.setBirthdate(birthdateAdultTest);
      person.setMedicalrecords(medicalrecords);
      adultList1.add(person);
    }
    for (int i = 0; i < 2; i++) {
      Persons person = new Persons();
      person.setFirstName(firstNameTest + i);
      person.setLastName(lastNameTest + i);
      Medicalrecords medicalrecords = new Medicalrecords();
      medicalrecords.setBirthdate(birthdateChildTest);
      person.setMedicalrecords(medicalrecords);
      childList2.add(person);
    }
    Firestation firestation1 = new Firestation();
    firestation1.setAddress(addressTest);
    firestation1.setStation(stationTest);
    firestation1.setPersons(adultList1);
    Firestation firestation2 = new Firestation();
    firestation2.setAddress(addressTest);
    firestation2.setStation(stationTest);
    firestation2.setPersons(childList2);
    List<Firestation> firestationList =
        asList(firestation1, firestation2);
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
    Persons person = new Persons();
    person.setFirstName(firstNameTest);
    person.setLastName(lastNameTest);
    Medicalrecords medicalrecords = new Medicalrecords();
    medicalrecords.setBirthdate(birthdateChildTest);
    person.setMedicalrecords(medicalrecords);
    List<Persons> personsList = new ArrayList<>();
    personsList.add(person);
    Firestation firestation = new Firestation();
    firestation.setStation(stationTest);
    firestation.setAddress(addressTest);
    firestation.setPersons(personsList);
    List<Firestation> firestationList = new ArrayList<>();
    firestationList.add(firestation);
    //    WHEN
    when(dataRepository.getFirestations()).thenReturn(firestationList);
    Collection<FirestationDto> processTest = firestationService.getFloodStations(asList(stationTest));
    //    THEN
    assertThat(processTest.size()).isEqualTo(1);

  }

  // Valid avec 2 stations  de 1 personne
  @Test
  void getFloodStations_ShouldReturnTwoPersonInfo() {
    //    GIVEN
    Persons person1 = new Persons();
    person1.setFirstName(firstNameTest);
    person1.setLastName(lastNameTest);
    Medicalrecords medicalrecords = new Medicalrecords();
    medicalrecords.setBirthdate(birthdateChildTest);
    person1.setMedicalrecords(medicalrecords);
    List<Persons> personsList1 = new ArrayList<>();
    personsList1.add(person1);
    Persons person2 = new Persons();
    person2.setFirstName(firstNameTest);
    person2.setLastName(lastNameTest);
    person2.setMedicalrecords(medicalrecords);
    List<Persons> personsList2 = new ArrayList<>();
    personsList2.add(person2);
    Firestation firestation1 = new Firestation();
    firestation1.setStation(stationTest);
    firestation1.setAddress(addressTest);
    firestation1.setPersons(personsList1);
    Firestation firestation2 = new Firestation();
    firestation2.setStation(stationTest);
    firestation2.setAddress(addressTest + 1);
    firestation2.setPersons(personsList2);
    List<Firestation> firestationList = asList(firestation1, firestation2);
    //    WHEN
    when(dataRepository.getFirestations()).thenReturn(firestationList);
    Collection<FirestationDto> processTest = firestationService.getFloodStations(asList(stationTest));
    //    THEN

    assertThat(processTest.size()).isEqualTo(2); //on verifie que 2 firestation sont ajoutés
    for (FirestationDto fire : processTest) {
      assertThat(fire.getPersonInfos().size()).isEqualTo(1); //on vérifie que chaque firestation ajouté a bien 1 utilisateur
    }
  }

  // Valid avec 2 stations de 2 personnes
  @Test
  void getFloodStations_ShouldReturnFourPersonInfo() {
    //    GIVEN
    Medicalrecords medicalrecords = new Medicalrecords();
    medicalrecords.setBirthdate(birthdateChildTest);
    List<Persons> personsList1 = new ArrayList<>();
    for (int i = 0; i < 2; i++) {
      //
      Persons person1 = new Persons();
      person1.setFirstName(firstNameTest + i);
      person1.setLastName(lastNameTest + i);
      person1.setMedicalrecords(medicalrecords);
      personsList1.add(person1);
    }
    List<Persons> personsList2 = new ArrayList<>();
    for (int i = 2; i < 4; i++) {
      //
      Persons person2 = new Persons();
      person2.setFirstName(firstNameTest + i);
      person2.setLastName(lastNameTest + i);
      person2.setMedicalrecords(medicalrecords);
      personsList2.add(person2);
    }
    Firestation firestation1 = new Firestation();
    firestation1.setAddress(addressTest);
    firestation1.setStation(stationTest);
    firestation1.setPersons(personsList1);
    Firestation firestation2 = new Firestation();
    firestation2.setAddress(addressTest + 1);
    firestation2.setStation(stationTest + 1);
    firestation2.setPersons(personsList2);
    List<Firestation> firestationList = asList(firestation1, firestation2);
    //    WHEN
    when(dataRepository.getFirestations()).thenReturn(firestationList);
    Collection<FirestationDto> processTest = firestationService.getFloodStations(asList(stationTest, stationTest + 1));
    //    THEN
    assertThat(processTest.size()).isEqualTo(2);
    for (FirestationDto firestationDto : processTest) {
      assertThat(firestationDto.getPersonInfos().size()).isEqualTo(2);
    }
  }

  // Valid avec 2 stations de 2 personnes identiques
  @Test
  void getFloodStationsWithDuplicate_ShouldReturnTwoPersonInfo() {
    //    GIVEN
    List<Persons> personsList1 = new ArrayList<>();
    for (int i = 0; i < 2; i++) {
      //
      Persons person1 = new Persons();
      person1.setFirstName(firstNameTest + i);
      person1.setLastName(lastNameTest + i);
      Medicalrecords medicalrecords = new Medicalrecords();
      medicalrecords.setBirthdate(birthdateChildTest);
      person1.setMedicalrecords(medicalrecords);
      personsList1.add(person1);

    }
    Firestation firestation1 = new Firestation();
    firestation1.setStation(stationTest);
    firestation1.setAddress(addressTest);
    firestation1.setPersons(personsList1);

    List<Firestation> firestationList = asList(firestation1, firestation1);
    //    WHEN
    when(dataRepository.getFirestations()).thenReturn(firestationList);
    Collection<FirestationDto> processTest = firestationService.getFloodStations(asList(stationTest));
    //    THEN
    assertThat(processTest.iterator().next().getPersonInfos().size()).isEqualTo(2);
  }

  // avec une liste de station vide
  @Test
  void getFloodStationsWithEmptyArgs_ShouldReturnEmptyList() {
    //    GIVEN
    Persons persons = new Persons();
    persons.setFirstName(firstNameTest);
    persons.setLastName(lastNameTest);
    List<Persons> personsList = new ArrayList<>();
    personsList.add(persons);
    Firestation firestation = new Firestation();
    firestation.setStation(stationTest);
    firestation.setAddress(addressTest);
    firestation.setPersons(personsList);
    List<Firestation> firestationList = new ArrayList<>();
    firestationList.add(firestation);

    //    WHEN
    when(dataRepository.getFirestations()).thenReturn(firestationList);
    Collection<FirestationDto> processTest = firestationService.getFloodStations(new ArrayList<>());
    //    THEN
    assertThat(processTest).isEmpty();
  }

  @Test
  void getFloodStationsWhenRepositoryReturnEmptyList_ShouldReturnEmptyList() {
    //    GIVEN

    //    WHEN
    when(dataRepository.getFirestations()).thenReturn(new ArrayList<>());
    Collection<FirestationDto> processTest = firestationService.getFloodStations(asList(stationTest));
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

  // avec firestation qui n'existe pas
  @Test
  void deleteFirestationInvalid_ShouldThrowDataNotFindExeption() {
    //    GIVEN
    Firestation firestation = new Firestation();
    firestation.setStation(stationTest);
    firestation.setAddress(addressTest);
    //    WHEN
    when(dataRepository.getFirestations()).thenReturn(asList(new Firestation()));
    //    THEN
    assertThrows(
        DataNotFindException.class, () -> firestationService.deleteFirestation(firestation));
  }
}
