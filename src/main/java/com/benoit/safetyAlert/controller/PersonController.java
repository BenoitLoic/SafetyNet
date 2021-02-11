package com.benoit.safetyAlert.controller;

import com.benoit.safetyAlert.dto.PersonInfo;
import com.benoit.safetyAlert.model.Persons;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.Collection;

/**
 * The interface PersonController.
 */
public interface PersonController {

  /**
   * Method for GET /communityEmail.
   * this method will take one city as parameter
   * and return all email that match this city.
   *
   * @param city the city to process @NotBlank
   * @return the collection of email from this city
   */
  Collection<Persons> communityEmail(@NotBlank String city);

  /**
   * Method for GET /fire.
   * this method will take an address
   * and return a list of user's information that live at this address.
   * information includes : name, phone, age, medication, allergies and station number.
   *
   * @param address the address to process @NotBlank
   * @return the collection of user information.
   */
  Collection<PersonInfo> fire(@NotBlank String address);

  /**
   * Method for GET /childAlert.
   * this method will take an address as parameter
   * and return a list of children (age <= 18years) living there with their family members.
   * children information includes : firstName, lastName, age, list of family member living here.
   * This method will return an empty list if there is no child.
   *
   * @param address the address @NotBlank
   * @return the collection of child information.
   */
  Collection<PersonInfo> childAlert(@NotBlank String address);

  /**
   * Method for POST /person.
   * this method will add the given person to DB.
   *
   * @param person the person to add. firstName and lastName are mandatory
   */
  void createPerson(@Valid Persons person);

  /**
   * Method for DELETE /person.
   * this method will delete the given person from DB.
   *
   * @param person the person to delete. firstName and lastName are mandatory
   */
  void deletePerson(@Valid Persons person);

  /**
   * Method for PUT /person.
   * this method will update the given person in DB.
   *
   * @param person the person to update. firstName and lastName are mandatory
   */
  void updatePerson(@Valid Persons person);
}
