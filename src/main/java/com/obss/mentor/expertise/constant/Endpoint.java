package com.obss.mentor.expertise.constant;

/**
 * All endpoint can be found under this class.
 * 
 * @author Goktug Selcuk
 *
 */
public enum Endpoint {
  MENTOR_USER_SET_MENTOR("user/set/role/mentorgroupleader"),
  MENTOR_USER_SET_MENTEE("user/set/role/mentee"),
  MENTOR_USER_AUTHENTICATE("user/authenticate"),
  MENTOR_GET_USER_BY_ID("user/get/%s");

  String url;


  Endpoint(String url) {
    this.url = url;
  }

  /**
   * Return url of endpoint.
   * 
   * @return
   */
  public String url() {
    return url;
  }

}
