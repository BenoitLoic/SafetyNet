package com.benoit.safetyAlert.services;

import com.benoit.safetyAlert.dto.PersonInfo;

import java.util.Collection;
import java.util.List;

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
    Collection<String> getCommunityEmail(String city);







    /**
     * Gets fire address.
     *
     * @param address the address
     * @return the fire address
     */
    Collection <Object>getFireAddress(String address);



    /**
     * Gets child alert.
     *
     * @param address the address
     * @return the child alert
     */
    Collection<Object> getChildAlert(String address);


}
