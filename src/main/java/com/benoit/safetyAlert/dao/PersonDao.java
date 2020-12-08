package com.benoit.safetyAlert.dao;

import com.benoit.safetyAlert.model.Persons;

public interface PersonDao {

    boolean createPerson(Persons person);

    boolean deletePerson(Persons person);

    boolean updatePerson(Persons person);
}
