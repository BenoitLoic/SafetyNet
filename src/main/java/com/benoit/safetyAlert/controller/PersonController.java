package com.benoit.safetyAlert.controller;

import com.benoit.safetyAlert.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.Collection;
import java.util.List;

@RestController
public class PersonController {

    @Autowired
    private PersonService personService;

    @GetMapping("/communityEmail")
    public Collection<String> communityEmail(@RequestParam String city) {

        return personService.getCommunityEmail(city);
    }

    @GetMapping("/phoneAlert")
    public Collection<String> phoneAlert(@RequestParam String station) {

        return personService.getPhoneNumber(station);
    }

    @GetMapping("/firestation")
    public Collection<Object> fireStationCoverage(@RequestParam String stationNumber) {

        return personService.getPersonCoveredByFireStation(stationNumber);
    }

    @GetMapping("/personInfo")
    public Collection personInfo(@RequestParam String firstName, @RequestParam String lastName) {

        return personService.getPersonInfo(firstName, lastName);
    }

    @GetMapping("/fire")
    public Collection fire(@RequestParam String address) {

        return personService.getFireAddress(address);
    }

    @GetMapping("/flood/stations")
    public Collection<Object> floodStations(@RequestParam List<String> stations) {

        return personService.getFloodStations(stations);
    }

    @GetMapping("/childAlert")
    public Collection<Object> childAlert(@RequestParam String address) {

        return personService.getChildAlert(address);
    }

}




