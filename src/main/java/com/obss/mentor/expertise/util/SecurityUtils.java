package com.obss.mentor.expertise.util;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

/**
 * 
 * @author Goktug Selcuk
 *
 * @param <T>
 */
@Component
public class SecurityUtils<T> {


  /**
   * 
   * @param authToken
   * @param object
   * @return
   */
  public HttpEntity<T> createRequestWithAuth(String authToken, T object) {
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.set("Authorization", "Basic " + authToken);
    return new HttpEntity<>(object, httpHeaders);
  }

}
