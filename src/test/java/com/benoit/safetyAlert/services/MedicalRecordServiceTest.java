package com.benoit.safetyAlert.services;

import com.benoit.safetyAlert.dao.MedicalRecordDao;
import com.benoit.safetyAlert.dto.PersonInfo;
import com.benoit.safetyAlert.exceptions.DataAlreadyExistException;
import com.benoit.safetyAlert.exceptions.DataNotFindException;
import com.benoit.safetyAlert.exceptions.InvalidArgumentException;
import com.benoit.safetyAlert.model.Firestation;
import com.benoit.safetyAlert.model.Medicalrecords;
import com.benoit.safetyAlert.model.Persons;
import com.benoit.safetyAlert.repository.DataRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
  String birthdateTest;
  String phoneTest;
  String emailTest;
  String addressTest;
  String zipTest;
  String cityTest;
  Firestation firestationTest = new Firestation();
  Medicalrecords medicalrecordsTest = new Medicalrecords();
  List<String> medicationsTest = new ArrayList<>();
  List<String> allergiesTest = new ArrayList<>();

  // quand tout se passe bien
  @Test
  void getPersonInfoReturnPersonInfoWithFirstNameAndLastName() {

    //      GIVEN
    List<Persons> personsList = new ArrayList<>();
    personsList.add(
        new Persons(
            firstNameTest,
            lastNameTest,
            addressTest,
            zipTest,
            cityTest,
            phoneTest,
            emailTest,
            firestationTest,
            medicalrecordsTest));
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
    personsList.add(
        new Persons(
            firstNameTest,
            lastNameTest,
            addressTest,
            zipTest,
            cityTest,
            phoneTest,
            emailTest,
            firestationTest,
            medicalrecordsTest));
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
    Medicalrecords medicalrecord = new Medicalrecords(firstNameTest, lastNameTest, birthdateTest);
    //    WHEN
    when(dataRepositoryMock.getPersons()).thenReturn(asList(person));
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
    List<Medicalrecords> medicalrecordsList = new ArrayList<>();
    Medicalrecords medicalrecord = new Medicalrecords(firstNameTest, lastNameTest, birthdateTest);
    medicalrecordsList.add(medicalrecord);

    //    WHEN
    when(dataRepositoryMock.getPersons()).thenReturn(asList(person));
    when(dataRepositoryMock.getMedicalrecords()).thenReturn(medicalrecordsList);

    //    THEN
    assertThrows(
        DataAlreadyExistException.class,
        () -> medicalRecordsService.createMedicalRecord(medicalrecord));
  }

  @Test
  void createMedicalRecordInvalid_WhenPersonDoNotExist_ShouldThrowDataNotFindException() {
    //    GIVEN
    Medicalrecords medicalrecords = new Medicalrecords(firstNameTest, lastNameTest, birthdateTest);
    Persons person = new Persons();
    person.setFirstName("a");
    person.setLastName("b");
    //    WHEN
    when(dataRepositoryMock.getPersons()).thenReturn(asList(person));
    //    THEN
    assertThrows(
        DataNotFindException.class,
        () -> medicalRecordsService.createMedicalRecord(medicalrecords));
  }

  // Valid
  @Test
  void deleteMedicalRecordValid() {
    //    GIVEN
    Medicalrecords medicalrecords = new Medicalrecords(firstNameTest, lastNameTest, birthdateTest);
    //    WHEN
    when(dataRepositoryMock.getMedicalrecords()).thenReturn(asList(medicalrecords));
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
    Medicalrecords medicalrecords = new Medicalrecords(firstNameTest, lastNameTest, birthdateTest);
    //    WHEN
    when(dataRepositoryMock.getMedicalrecords()).thenReturn(asList(new Medicalrecords()));
    //    THEN
    assertThrows(
        DataNotFindException.class,
        () -> medicalRecordsService.deleteMedicalRecord(medicalrecords));
  }

  // Valid
  @Test
  void updateMedicalRecordValid() {
    //    GIVEN
    Medicalrecords medicalrecords = new Medicalrecords(firstNameTest, lastNameTest, birthdateTest);
    //    WHEN
    when(dataRepositoryMock.getMedicalrecords()).thenReturn(asList(medicalrecords));
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
    Medicalrecords medicalrecords = new Medicalrecords(firstNameTest, lastNameTest, birthdateTest);
    //    WHEN
    when(dataRepositoryMock.getMedicalrecords())
        .thenReturn(asList(new Medicalrecords(firstNameTest + 1, lastNameTest + 1, birthdateTest)));
    //    THEN
    assertThrows(
        DataNotFindException.class,
        () -> medicalRecordsService.updateMedicalRecord(medicalrecords));
  }

}
