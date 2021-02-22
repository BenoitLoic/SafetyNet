package com.safetynet.safetyalerts.controller;

import com.safetynet.safetyalerts.dto.PersonInfo;
import com.safetynet.safetyalerts.model.Persons;
import com.safetynet.safetyalerts.services.PersonServiceImpl;
import java.util.Collection;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * The implementation for PersonController.
 */
@Validated
@RestController
public class PersonControllerImpl implements PersonController {

  private final PersonServiceImpl personService;

  @Autowired
  public PersonControllerImpl(PersonServiceImpl personService) {
    this.personService = personService;
  }

  /**
   * Method for GET /communityEmail.
   * this method will take one city as parameter
   * and return all email that match this city.
   *
   * @param city the city to process
   * @return the collection of email from this city
   */
  @Override
  @GetMapping("/communityEmail")
  @ResponseStatus(HttpStatus.OK)
  public Collection<Persons> communityEmail(@NotBlank @RequestParam String city) {

    return personService.getCommunityEmail(city);
  }

  /**
   * Method for GET /fire.
   * this method will take an address
   * and return a list of user's information that live at this address.
   * information includes : name, phone, age, medication, allergies and station number.
   *
   * @param address the address to process
   * @return the collection of user information.
   */
  @Override
  @GetMapping("/fire")
  @ResponseStatus(HttpStatus.OK)
  public Collection<PersonInfo> fire(@NotBlank @RequestParam String address) {

    return personService.getFireAddress(address);
  }

  /**
   * Method for GET /childAlert.
   * this method will take an address as parameter
   * and return a list of children (age <= 18years) living there with their family members.
   * children information includes : firstName, lastName, age, list of family member living here.
   * This method will return an empty list if there is no child.
   *
   * @param address the address
   * @return the collection of child information.
   */
  @Override
  @GetMapping("/childAlert")
  @ResponseStatus(HttpStatus.OK)
  public Collection<PersonInfo> childAlert(@NotBlank @RequestParam String address) {

    return personService.getChildAlert(address);
  }

  /**
   * Method for POST /person.
   * this method will add the given person to DB.
   *
   * @param person the person to add. firstName and lastName are mandatory
   */
  @Override
  @PostMapping("/person")
  @ResponseStatus(HttpStatus.CREATED)
  public void createPerson(@RequestBody @Valid Persons person) {
    personService.createPerson(person);
  }

  /**
   * Method for DELETE /person.
   * this method will delete the given person from DB.
   *
   * @param person the person to delete. firstName and lastName are mandatory
   */
  @Override
  @DeleteMapping("/person")
  @ResponseStatus(HttpStatus.OK)
  public void deletePerson(@RequestBody @Valid Persons person) {

    personService.deletePerson(person);
  }

  /**
   * Method for PUT /person.
   * this method will update the given person in DB.
   *
   * @param person the person to update. firstName and lastName are mandatory
   */
  @Override
  @PutMapping("/person")
  @ResponseStatus(HttpStatus.CREATED)
  public void updatePerson(@RequestBody @Valid Persons person) {

    personService.updatePerson(person);
  }
}
