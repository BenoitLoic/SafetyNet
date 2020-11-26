package com.benoit.safetyAlert.services;

import java.util.Collection;
import java.util.List;


public interface IPersonService {

    Collection<String> getCommunityEmail(String city);

    Collection<String> getPhoneNumber(String address);

    Collection<List<String>> getPersonCoveredByFirestation(String address);

    Collection getFireAddress(String address);
}
