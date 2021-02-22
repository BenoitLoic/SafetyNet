package com.safetynet.safetyalerts.utility;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * Contains method to Calculate age.
 */
@Component
public class CalculateAge {

  private static final Logger LOGGER = LogManager.getLogger(CalculateAge.class);

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
      LOGGER.error("invalid format for birthdate");
      throw parseDateEx;
    } catch (NullPointerException nullBirthdateEx) {
      LOGGER.error("error : birthdate is null.");
      throw nullBirthdateEx;
    } catch (DateTimeException dateTimeException) {
      LOGGER.error("error : invalid birthdate.");
      throw dateTimeException;
    }
  }
}
