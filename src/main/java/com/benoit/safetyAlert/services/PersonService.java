package com.benoit.safetyAlert.services;

import com.benoit.safetyAlert.model.Persons;
import com.benoit.safetyAlert.repository.DataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class PersonService implements IPersonService {

    @Autowired
    DataRepository dataRepository;

    public Collection<String> getCommunityEmail(String city) {

        List<Persons> personByCity = dataRepository.getPersonByCity(city);
        Collection<String> collectionEmail = new ArrayList<String>();
        for (Persons person : personByCity) {

            collectionEmail.add(person.getEmail());
        }
        return collectionEmail;
    }

    public Collection<String> getPhoneNumber(String address) {

        List<Persons> personByAddress = dataRepository.getPersonByAddress(address);
        Collection<String> collectionPhoneNumber = new ArrayList<>();
        for (Persons person : personByAddress) {
            collectionPhoneNumber.add(person.getPhone());
        }
        return collectionPhoneNumber;
    }

}
