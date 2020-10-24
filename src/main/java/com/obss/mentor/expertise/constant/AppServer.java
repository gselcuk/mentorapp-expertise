package com.obss.mentor.expertise.constant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.netflix.discovery.EurekaClient;

/**
 * App server names can be found under this class.
 * 
 * @author Goktug Selcuk
 *
 */
@Component
public class AppServer {


  @Autowired
  private EurekaClient discoveryClient;

  public static final String MENTOR_USER = "mentorapp-user";


  /**
   * Create full endpoint for mentor user micro service.
   * 
   * @param endpoint {@code Endpoint} ENUM.
   * @return
   */
  public String getUrlMentorUser(Endpoint endpoint) {
    return discoveryClient.getNextServerFromEureka(AppServer.MENTOR_USER, false).getHomePageUrl()
        + endpoint.url;
  }

}
