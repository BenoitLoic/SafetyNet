package com.benoit.safetyAlert.controller;

import com.benoit.safetyAlert.services.FirestationService;
import com.benoit.safetyAlert.services.MedicalRecordsService;
import com.benoit.safetyAlert.services.PersonService;
import com.benoit.safetyAlert.utility.Counter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
public class PersonController {

    @Autowired
    private PersonService personService;

    @Autowired
    private FirestationService firestationService;

    @Autowired
    private MedicalRecordsService medicalRecordsService;


    @GetMapping("/communityEmail")
    public Collection<String> communityEmail(@RequestParam String city) {

        return personService.getCommunityEmail(city);
    }

    @GetMapping("/phoneAlert")
    public Collection<String> phoneAlert(@RequestParam String station) {

        //recuperation des adresses de la station
        List<String> firestationAddress = firestationService.getFirestationAddress(station);

        List<String> phoneNumber = new ArrayList<>();
        //boucle pour récupérer les tel des utilisateurs
        for (String address : firestationAddress) {
            phoneNumber.add(personService.getPhoneNumber(address).toString());
        }
        return phoneNumber;
    }

    @GetMapping("/firestation")
    public Collection firestationCoverage(@RequestParam String stationNumber) {

        //recupération des address de la station

        Counter counter = new Counter();
        Collection<Collection<List<String>>> personCovered = new ArrayList<>();

        //on déroule la liste d'address
        for (String address : firestationService.getFirestationAddress(stationNumber)) {
            personCovered.add(personService.getPersonCoveredByFirestation(address));

        }
        Collection adultAndChildCounterList = new ArrayList<>();
        adultAndChildCounterList.add("adult: " + counter.getAdult());
        adultAndChildCounterList.add("child: " + counter.getChild());
        personCovered.add(adultAndChildCounterList);
        counter.reset();
        return personCovered;
    }

    @GetMapping("/personInfo")
    public Collection personInfo(@RequestParam String firstName, @RequestParam String lastName) {

        return personService.getPersonInfo(firstName, lastName);

    }

    @GetMapping("/fire")
    public Collection fire(@RequestParam String address){

        return personService.getFireAddress(address);
    }

//    @GetMapping("/firestation")
//    public Collection<List<String>> firestationCoverage(String station) throws IOException {
//
//        //recupération des address de la station
//        List<String> firestationAddress = firestationService.getFirestationAddress(station);
//        List<Persons> personByAddress;
//        Collection<List<String>> returningData = new ArrayList<>();
//        int adult = 0;
//        int child = 0;
//        //on déroule la liste d'address
//        for (String address : firestationAddress) {
//
//            DataRepository dataRepository = new DataRepository();
//            personByAddress = dataRepository.getPersonByAddress(address);
////            on récupere les info des utilisateurs pour chaque adresses
//            for (Persons person : personByAddress) {
////                List<String> user = new ArrayList<>();
////                user.add(person.getFirstName());
////                user.add(person.getLastName());
////                user.add(person.getAddress());
////                user.add(person.getPhone());
////                returningData.add(user);
//
////                recupération de l'age
//                if (medicalRecordsService.getAge(person.getFirstName(), person.getLastName()) >= 18) {
//                    adult++;
//                } else {
//                    child++;
//                }
//            }
//        }
//
//        //comptage des adultes et enfants
//        List<String> count = new ArrayList<>();
//        count.add("adult: " + adult);
//        count.add("child: " + child);
//        returningData.add(count);
//
//        return returningData;
//    }


}




