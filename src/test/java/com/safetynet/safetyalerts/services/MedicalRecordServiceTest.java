package com.safetynet.safetyalerts.services;

import com.safetynet.safetyalerts.dao.MedicalRecordDao;
import com.safetynet.safetyalerts.dto.PersonInfo;
import com.safetynet.safetyalerts.exceptions.DataAlreadyExistException;
import com.safetynet.safetyalerts.exceptions.DataNotFindException;
import com.safetynet.safetyalerts.model.Firestation;
import com.safetynet.safetyalerts.model.Medicalrecords;
import com.safetynet.safetyalerts.model.Persons;
import com.safetynet.safetyalerts.repository.DataRepository;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MedicalRecordServiceTest {

  @Mock
  DataRepository dataRepositoryMock;
  @Mock
  MedicalRecordDao medicalRecordDaoMock;

  @InjectMocks
  MedicalRecordsServiceImpl medicalRecordsService;

  String firstNameTest = "Homer Jay";
  String lastNameTest = "Simpson";
  String phoneTest;
  String emailTest;
  String addressTest;
  String zipTest;
  String cityTest;
  Firestation firestationTest = new Firestation();
  Medicalrecords medicalrecordsTest = new Medicalrecords();


  // quand tout se passe bien
  @Test
  void getPersonInfoReturnPersonInfoWithFirstNameAndLastName() {

    //      GIVEN
    List<Persons> personsList = new ArrayList<>();
    Persons person = new Persons();
    person.setFirstName(firstNameTest);
    person.setLastName(lastNameTest);
    person.setPhone(phoneTest);
    person.setAddress(addressTest);
    person.setCity(cityTest);
    person.setZip(zipTest);
    person.setEmail(emailTest);
    person.setFirestation(firestationTest);
    person.setMedicalrecords(medicalrecordsTest);
    personsList.add(person);
    //      WHEN
    when(dataRepositoryMock.getPersons()).thenReturn(personsList);
    PersonInfo personInfoTest = medicalRecordsService.getPersonInfo(firstNameTest, lastNameTest);
    //      THEN
    assertThat(personInfoTest.getFirstName()).isEqualTo(firstNameTest);
    assertThat(personInfoTest.getLastName()).isEqualTo(lastNameTest);
  }

  // quand on ajoute un args null ou vide
  @Test
  void getPersonInfoWithEmptyOrNullArgs_ShouldReturnDataNotFindException() {

    //      GIVEN
    List<Persons> personsList = new ArrayList<>();
    Persons person = new Persons();
    person.setFirstName(firstNameTest);
    person.setLastName(lastNameTest);
    person.setPhone(phoneTest);
    person.setAddress(addressTest);
    person.setCity(cityTest);
    person.setZip(zipTest);
    person.setEmail(emailTest);
    person.setFirestation(firestationTest);
    person.setMedicalrecords(medicalrecordsTest);
    personsList.add(person);
    //      WHEN
    when(dataRepositoryMock.getPersons()).thenReturn(personsList);
    //      THEN
    assertThrows(
        DataNotFindException.class, () -> medicalRecordsService.getPersonInfo(firstNameTest, ""));
    assertThrows(
        DataNotFindException.class, () -> medicalRecordsService.getPersonInfo(null, lastNameTest));
  }

  // Valid
  @Test
  void createMedicalRecordValid() {
    //    GIVEN
    Persons person = new Persons();
    person.setFirstName(firstNameTest);
    person.setLastName(lastNameTest);
    List<Persons> personsList = new ArrayList<>();
    personsList.add(person);
    Medicalrecords medicalrecord = new Medicalrecords();
    medicalrecord.setFirstName(firstNameTest);
    medicalrecord.setLastName(lastNameTest);
    //    WHEN
    when(dataRepositoryMock.getPersons()).thenReturn(personsList);
    when(medicalRecordDaoMock.createMedicalRecords(any())).thenReturn(true);
    //    THEN
    assertThat(medicalRecordsService.createMedicalRecord(medicalrecord)).isTrue();
  }

  // arg = new
  @Test
  void createMedicalRecordWithNullValue_ShouldThrowDataNotFindException() {
    //    GIVEN


    //    WHEN

    //    THEN
    assertThrows(
        DataNotFindException.class,
        () -> medicalRecordsService.createMedicalRecord(new Medicalrecords()));
  }

  // avec medicalRecord qui existe déja
  @Test
  void createMedicalRecordInvalid_ShouldThrowDataAlreadyExistException() {
    //    GIVEN
    Persons person = new Persons();
    person.setFirstName(firstNameTest);
    person.setLastName(lastNameTest);
    List<Persons> personsList = new ArrayList<>();
    personsList.add(person);
    List<Medicalrecords> medicalrecordsList = new ArrayList<>();
    Medicalrecords medicalrecord = new Medicalrecords();
    medicalrecord.setFirstName(firstNameTest);
    medicalrecord.setLastName(lastNameTest);
    medicalrecordsList.add(medicalrecord);

    //    WHEN
    when(dataRepositoryMock.getPersons()).thenReturn(personsList);
    when(dataRepositoryMock.getMedicalrecords()).thenReturn(medicalrecordsList);

    //    THEN
    assertThrows(
        DataAlreadyExistException.class,
        () -> medicalRecordsService.createMedicalRecord(medicalrecord));
  }

  @Test
  void createMedicalRecordInvalid_WhenPersonDoNotExist_ShouldThrowDataNotFindException() {
    //    GIVEN
    Medicalrecords medicalrecords = new Medicalrecords();
    medicalrecords.setFirstName(firstNameTest);
    medicalrecords.setLastName(lastNameTest);
    Persons person = new Persons();
    person.setFirstName("a");
    person.setLastName("b");
    List<Persons> personsList = new ArrayList<>();
    personsList.add(person);
    //    WHEN
    when(dataRepositoryMock.getPersons()).thenReturn(personsList);
    //    THEN
    assertThrows(
        DataNotFindException.class,
        () -> medicalRecordsService.createMedicalRecord(medicalrecords));
  }

  // Valid
  @Test
  void deleteMedicalRecordValid() {
    //    GIVEN
    Medicalrecords medicalrecords = new Medicalrecords();
    medicalrecords.setFirstName(firstNameTest);
    medicalrecords.setLastName(lastNameTest);
    List<Medicalrecords> medicalrecordsList = new ArrayList<>();
    medicalrecordsList.add(medicalrecords);
    //    WHEN
    when(dataRepositoryMock.getMedicalrecords()).thenReturn(medicalrecordsList);
    when(medicalRecordDaoMock.deleteMedicalRecords(any())).thenReturn(true);
    //    THEN
    assertThat(medicalRecordsService.deleteMedicalRecord(medicalrecords)).isTrue();
  }

  // arg = new
  @Test
  void deleteMedicalRecordWithNullValue_ShouldThrowDataNotFindException() {
    //    GIVEN

    //    WHEN

    //    THEN
    assertThrows(
        DataNotFindException.class,
        () -> medicalRecordsService.deleteMedicalRecord(new Medicalrecords()));
  }

  // avec medical record qui n'existe pas
  @Test
  void deleteMedicalRecordInvalid_ShouldThrowDataNotFindExeption() {
    //    GIVEN
    Medicalrecords medicalrecords = new Medicalrecords();
    medicalrecords.setFirstName(firstNameTest);
    medicalrecords.setLastName(lastNameTest);
    List<Medicalrecords> emptyMedicalrecordsList = new ArrayList<>();
    //    WHEN
    when(dataRepositoryMock.getMedicalrecords()).thenReturn(emptyMedicalrecordsList);
    //    THEN
    assertThrows(
        DataNotFindException.class,
        () -> medicalRecordsService.deleteMedicalRecord(medicalrecords));
  }

  // Valid
  @Test
  void updateMedicalRecordValid() {
    //    GIVEN
    Medicalrecords medicalrecords = new Medicalrecords();
    medicalrecords.setFirstName(firstNameTest);
    medicalrecords.setLastName(lastNameTest);
    List<Medicalrecords> medicalrecordsList = new ArrayList<>();
    medicalrecordsList.add(medicalrecords);
    //    WHEN
    when(dataRepositoryMock.getMedicalrecords()).thenReturn(medicalrecordsList);
    when(medicalRecordDaoMock.updateMedicalRecords(any())).thenReturn(true);
    //    THEN
    assertThat(medicalRecordsService.updateMedicalRecord(medicalrecords)).isTrue();
  }

  // arg = new
  @Test
  void updateMedicalRecordWithNullValue_ShouldThrowDataNotFindException() {
    //    GIVEN

    //    WHEN

    //    THEN
    assertThrows(
        DataNotFindException.class,
        () -> medicalRecordsService.updateMedicalRecord(new Medicalrecords()));
  }

  // avec medical record qui n'existe pas
  @Test
  void updateMedicalRecordInvalid_ShouldThrowDataNotFindExeption() {
    //    GIVEN
    Medicalrecords medicalrecords = new Medicalrecords();
    medicalrecords.setFirstName(firstNameTest);
    medicalrecords.setLastName(lastNameTest);
    List<Medicalrecords> medicalrecordsList = new ArrayList<>();

    //    WHEN
    when(dataRepositoryMock.getMedicalrecords())
        .thenReturn(medicalrecordsList);
    //    THEN
    assertThrows(
        DataNotFindException.class,
        () -> medicalRecordsService.updateMedicalRecord(medicalrecords));
  }

}
