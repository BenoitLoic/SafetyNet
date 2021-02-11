package com.benoit.safetyAlert.dao;

import com.benoit.safetyAlert.model.Persons;
import com.benoit.safetyAlert.repository.DataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Implementation for PersonDao.
 * Contains method to create /update /delete person in DB
 */
@Service
public class PersonDaoImpl implements PersonDao {

  private final DataRepository dataRepository;

  @Autowired
  public PersonDaoImpl(DataRepository dataRepository) {
    this.dataRepository = dataRepository;
  }

  @Override
  public boolean createPerson(Persons person) {

    // ajout de la nouvelle personne en mémoire
    dataRepository.getPersons().add(person);
    // commit pour appliquer les changement sur le json
    dataRepository.commit();
    return true;
  }

  @Override
  public boolean deletePerson(Persons person) {
    dataRepository.getPersons().remove(person);
    dataRepository.commit();
    return true;
  }


  /**
   * This method will update the given person if present in DB.
   * if person exist in DB it will compare all value from the existing person
   * and change them if they are different.
   *
   * @param person the data to update in the existing Persons from DB
   */
  @Override
  public boolean updatePerson(Persons person) {


    for (Persons personToUpdate : dataRepository.getPersons()) {

      if (personToUpdate.getFirstName().equalsIgnoreCase(person.getFirstName())
          && personToUpdate.getLastName().equalsIgnoreCase(person.getLastName())) {
        //ajouter ou changer les donnée qui sont différentes entre les 2
        if (person.getAddress() == null) {
          person.setAddress(personToUpdate.getAddress());
        }
        if (person.getEmail() == null) {
          person.setEmail(personToUpdate.getEmail());
        }
        if (person.getCity() == null) {
          person.setCity(personToUpdate.getCity());
        }
        if (person.getZip() == null) {
          person.setZip(personToUpdate.getZip());
        }
        if (person.getPhone() == null) {
          person.setPhone(personToUpdate.getPhone());
        }
        dataRepository.getPersons().remove(personToUpdate);
        dataRepository.getPersons().add(person);
        dataRepository.commit();
        return true;
      }
    }


    return false;
  }
}
