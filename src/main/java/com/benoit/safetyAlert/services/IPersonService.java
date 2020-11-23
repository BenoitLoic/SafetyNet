package com.benoit.safetyAlert.services;

import java.util.Collection;


public interface IPersonService {

    Collection<String> getCommunityEmail(String city);

    Collection<String> getPhoneNumber(String address);


}
