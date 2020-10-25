package com.obss.mentor.expertise.constant;

public enum DateFormat {

  CALENDAR_DATE("yyyy-MM-dd");

  private String format;

  DateFormat(String format) {
    this.format = format;
  }

  public String format() {
    return format;
  }

}
