package com.benoit.safetyAlert.services;

import java.util.Collection;
import java.util.List;


/**
 * The interface PersonService.
 */
public interface IPersonService {

    /**
     * Gets community email.
     *
     * @param city the city
     * @return the community email
     */
    Collection<String> getCommunityEmail(String city);

    /**
     * Gets phone number.
     *
     * @param address the address
     * @return the phone number
     */
    Collection<String> getPhoneNumber(String address);

    /**
     * Gets person covered by firestation.
     *
     * @param address the address
     * @return the person covered by firestation
     */
    Collection<Object> getPersonCoveredByFirestation(String address);

    /**
     * Gets fire address.
     *
     * @param address the address
     * @return the fire address
     */
    Collection<Object> getFireAddress(String address);

    /**
     * Gets flood stations.
     *
     * @param station the station
     * @return the flood stations
     */
    Collection<Object> getFloodStations(List<String> station);

    /**
     * Gets child alert.
     *
     * @param address the address
     * @return the child alert
     */
    Collection<Object> getChildAlert(String address);

}
