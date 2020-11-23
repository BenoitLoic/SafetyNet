package com.benoit.safetyAlert.controller;

import com.benoit.safetyAlert.services.FirestationService;
import com.benoit.safetyAlert.services.PersonService;
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


    @GetMapping("/communityEmail")
    public Collection<String> communityEmail(@RequestParam String city) {

        Collection<String> communityEmail = personService.getCommunityEmail(city);

        return communityEmail;
    }

    @GetMapping("/phoneAlert")
    public Collection<String> phoneAlert(@RequestParam String station) {

        //recuperation des adresses de la station
        List<String> firestationAddress = firestationService.getFirestationAddress(station);

        List<String> phoneNumber =new ArrayList<>();
        //boucle pour récupérer les tel des utilisateurs
        for (int i=0; i<firestationAddress.size(); i++){
            phoneNumber.add(personService.getPhoneNumber(firestationAddress.get(i)).toString());
        }
    return phoneNumber;
    }

}




