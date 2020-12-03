package com.benoit.safetyAlert.utility;


import org.junit.jupiter.api.Test;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CalculateAgeTest {


    @Test
    void calculateAge() {
        String birthdate = "03/23/1987";
        assertEquals(Period.between(LocalDate.of(1987, 3, 23), LocalDate.now()).getYears(), CalculateAge.calculateAge(birthdate));
    }

    @Test
    void calculateAgeWithInvalidBirthdateString() {

        String invalidBirthdate = "invalidString";
        assertThrows(DateTimeParseException.class, () -> CalculateAge.calculateAge(invalidBirthdate));
    }

    @Test
    void calculateAgeWithEmptyBirthdate() {

        String emptyBirthdate = "";
        assertThrows(DateTimeParseException.class, () -> CalculateAge.calculateAge(emptyBirthdate));

    }

    @Test
    void calculateAgeWithNullBirthdate() {

        String nullBirthdate = null;
        assertThrows(NullPointerException.class, () -> CalculateAge.calculateAge(nullBirthdate));

    }

    @Test
    void calculateAgeLessThanOneYearShouldReturnOne() {

        String sixMonthFromNow = LocalDate.now().minusMonths(6).format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        assertEquals(1, CalculateAge.calculateAge(sixMonthFromNow));

    }

    @Test
    void calculateAgeWithBirthdateAfterNow() {

        String oneYearAfter = LocalDate.now().plusYears(1).format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        assertThrows(DateTimeException.class, () -> CalculateAge.calculateAge(oneYearAfter));
    }


}