package com.obss.mentor.expertise.constant;

/**
 * All endpoint can be found under this class.
 * 
 * @author Goktug Selcuk
 *
 */
public enum Endpoint {
  MENTOR_USER_UPDATE_ROLE("user/set/role/mentorgroupleader"),
  MENTOR_USER_AUTHENTICATE("user/authenticate");

  String url;


  Endpoint(String url) {
    this.url = url;
  }

  /**
   * Return app server and end point .
   * 
   * @return
   */
  public String url() {
    return url;
  }

}
