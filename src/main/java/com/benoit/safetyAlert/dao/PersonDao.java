package com.benoit.safetyAlert.dao;

import com.benoit.safetyAlert.model.Persons;

/**
 * Interface for personDao.
 * Contains method to create /update /delete person in DB
 */
public interface PersonDao {

  boolean createPerson(Persons person);

  boolean deletePerson(Persons person);

  boolean updatePerson(Persons person);
}
