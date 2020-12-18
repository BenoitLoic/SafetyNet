//package com.benoit.safetyAlert.services;
//
//import com.benoit.safetyAlert.model.Firestation;
//import com.benoit.safetyAlert.repository.DataRepository;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//class FirestationServiceTest {
//
//    @BeforeEach
//    void setUp() {
//        Firestation firestationTest1 = new Firestation();
//        Firestation firestationTest2 = new Firestation();
//        Firestation firestationTest3 = new Firestation();
//        firestationTest1.setStation("1");
//        firestationTest1.setAddress("Test Address 1");
//        firestationTest2.setStation("2");
//        firestationTest2.setAddress("TEST address 2");
//        firestationTest3.setStation("1");
//        firestationTest3.setAddress("Test Address 3");
//        List<Firestation> testList = new ArrayList<>();
//        Collections.addAll(testList, firestationTest1, firestationTest2, firestationTest3);
//
//    }
//
//
//
//    //on test le traitement des methodes => on mock le repository
//
//    @Mock
//    private static DataRepository dataRepository;
//
//    @Test
//    void getFireStationAddress() {
//        Firestation firestationTest1 = new Firestation();
//        firestationTest1.setStation("1");
//        firestationTest1.setAddress("Test Address 1");
//        List<Firestation> testList = new ArrayList<>();
//        testList.add(firestationTest1);
//
//        when(dataRepository.getFirestationByStationNumber(anyString())).thenReturn(testList);
//
//        FirestationServiceImpl fireStationService = new FirestationServiceImpl();
//        fireStationService.getFirestationAddress(anyString());
//        assertEquals("Test Address 1", testList.get(0).getAddress());
//    }
//}