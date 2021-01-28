package com.benoit.safetyAlert.utility;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

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
    public int calculateAge(String birthdate) {

        //        format birthdate in LocalDate
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        try {
            LocalDate formattedDate = LocalDate.parse(birthdate, formatter);
            if (formattedDate.isAfter(LocalDate.now())) {
                throw new DateTimeException("invalid birthdate");
            }
            //        calculate age, if 0<age<1 return 1
            int age = Period.between(formattedDate, LocalDate.now()).getYears();
            if (age == 0) {
                age = 1;
            }
            return age;
        } catch (DateTimeParseException parseDateEx) {
            System.out.println("invalid format for birthdate");
            throw parseDateEx;
        } catch (NullPointerException nullBirthdateEx) {
            System.out.println("birthdate is null");
            throw nullBirthdateEx;
        } catch (DateTimeException dateTimeException) {
            System.out.println("invalid birthdate");
            throw dateTimeException;
        }
    }
}
