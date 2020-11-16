package com.benoit.safetyAlert.repository;


import com.benoit.safetyAlert.model.DatabaseJson;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.InputStream;

@Repository
public class DataRepository {
    //cet obj va permettre de mapper du json en obj java
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    //c'est le fichier Json en memoire
    private static DatabaseJson databaseJson;

    public DataRepository() throws IOException {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("data.json");
        databaseJson = OBJECT_MAPPER.readerFor(DatabaseJson.class).readValue(inputStream);
    }

    public static void main(String[] args) throws IOException {
        DataRepository dataRepository = new DataRepository();
        System.out.println(dataRepository.databaseJson.getPersons().size());
    }
}
