package com.benoit.safetyAlert.controller;

import java.util.Collection;

public interface MedicalRecordsController {

    Collection<Object> personInfo(String firstName, String lastName);

}
