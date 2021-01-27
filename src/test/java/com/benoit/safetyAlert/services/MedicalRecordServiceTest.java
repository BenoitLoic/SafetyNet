package com.benoit.safetyAlert.services;

import com.benoit.safetyAlert.dao.MedicalRecordDao;
import com.benoit.safetyAlert.dto.PersonInfo;
import com.benoit.safetyAlert.exceptions.DataNotFindException;
import com.benoit.safetyAlert.model.Firestation;
import com.benoit.safetyAlert.model.Medicalrecords;
import com.benoit.safetyAlert.model.Persons;
import com.benoit.safetyAlert.repository.DataRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
    assertThrows(DataNotFindException.class, () -> medicalRecordsService.getPersonInfo(firstNameTest, ""));
    assertThrows(DataNotFindException.class, () -> medicalRecordsService.getPersonInfo(null, lastNameTest));
  }

  // quand medical record est null
  @Test
  void getPersonInfoWhenMedicalRecordsIsNull_GetMedicationShouldBeEmpty() {

    //      GIVEN
    List<Persons> personsList = new ArrayList<>();
    List<String> emptyList = new ArrayList<>();
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
            null));
    //      WHEN
    when(dataRepositoryMock.getPersons()).thenReturn(personsList);
    PersonInfo process = medicalRecordsService.getPersonInfo(firstNameTest, lastNameTest);
    //      THEN
    assertEquals(new ArrayList<>(), process.getMedication());

  }

}
