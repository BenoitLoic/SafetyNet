package com.benoit.safetyAlert.services;

import com.benoit.safetyAlert.dto.PersonInfo;
import com.benoit.safetyAlert.model.Persons;

import java.util.Collection;

/**
 * The interface PersonService.
 */
public interface PersonService {

    /**
     * Gets community email.
     *
     * @param city the city
     * @return the community email
     */
    Collection<Persons> getCommunityEmail(String city);

    /**
     * Gets fire address.
     *
     * @param address the address
     * @return the fire address
     */
    Collection<PersonInfo> getFireAddress(String address);

    /**
     * Gets child alert.
     *
     * @param address the address
     * @return the child alert
     */
    Collection<PersonInfo> getChildAlert(String address);

    boolean createPerson(Persons person);

    boolean deletePerson(Persons person);

    boolean updatePerson(Persons person);
}
