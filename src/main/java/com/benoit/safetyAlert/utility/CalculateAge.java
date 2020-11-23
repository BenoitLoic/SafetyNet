package com.benoit.safetyAlert.utility;


import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

/**
 * Contains method to Calculate age.
 */
public class CalculateAge {

    /**
     * Calculate age from a birthdate (String).
     *
     * @param birthdate the birthdate
     * @return the age
     */
    public static int calculateAge(String birthdate) {

//        format birthdate in LocalDate
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate formattedDate = LocalDate.parse(birthdate, formatter);
//        calculate current age
        return Period.between(formattedDate, LocalDate.now()).getYears();
    }
}
