package com.safetynet.safetyalerts.utility;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class CalculateAgeTest {

  CalculateAge calc = new CalculateAge();

  @Test
  void calculateAge() {
    String birthdate = "03/23/1987";
    assertEquals(
        Period.between(LocalDate.of(1987, 3, 23), LocalDate.now()).getYears(),
        calc.calculateAge(birthdate));
  }

  @Test
  void calculateAgeWithInvalidBirthdateString() {

    String invalidBirthdate = "invalidString";
    assertThrows(DateTimeParseException.class, () -> calc.calculateAge(invalidBirthdate));
  }

  @Test
  void calculateAgeWithEmptyBirthdate() {

    String emptyBirthdate = "";
    assertThrows(DateTimeParseException.class, () -> calc.calculateAge(emptyBirthdate));
  }

  @Test
  void calculateAgeWithNullBirthdate() {

    String nullBirthdate = null;
    assertThrows(NullPointerException.class, () -> calc.calculateAge(nullBirthdate));
  }

  @Test
  void calculateAgeLessThanOneYearShouldReturnOne() {

    String sixMonthFromNow =
        LocalDate.now().minusMonths(6).format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
    assertEquals(1, calc.calculateAge(sixMonthFromNow));
  }

  @Test
  void calculateAgeWithBirthdateAfterNow() {

    String oneYearAfter =
        LocalDate.now().plusYears(1).format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
    assertThrows(DateTimeException.class, () -> calc.calculateAge(oneYearAfter));
  }
}
