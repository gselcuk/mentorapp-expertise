package com.obss.mentor.expertise.util;

import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.time.format.DateTimeFormatter;
import org.springframework.stereotype.Component;
import com.obss.mentor.expertise.constant.DateFormat;

/**
 * Date related utility methods.
 * 
 * @author Goktug Selcuk
 *
 */
@Component
public class DateUtils {

  /**
   * Get current date with given date format.
   * 
   * @return
   */
  public String getCurrentDate(DateFormat dateFormat) {
    LocalDate localDate = LocalDate.now();
    return getDateGivenFormat(localDate, dateFormat);
  }

  /**
   * Get date as String with given {@code DateFormat}.
   * 
   * @param localDateTime
   * @param dateFormat
   * @return
   */
  public String getDateGivenFormat(ChronoLocalDate localDateTime, DateFormat dateFormat) {
    DateTimeFormatter datePattern = DateTimeFormatter.ofPattern(dateFormat.format());
    return localDateTime.format(datePattern);
  }

}
