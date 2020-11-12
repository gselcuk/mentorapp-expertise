package com.obss.mentor.expertise.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.obss.mentor.expertise.model.GroupSession;
import com.obss.mentor.expertise.service.SessionService;
import com.obss.mentor.expertise.serviceparam.RateSessionRequest;
import com.obss.mentor.expertise.serviceparam.SetSessionRequest;

/***
 * Session controller.
 * 
 * @author Goktug Selcuk
 *
 */
@RestController
@RequestMapping(value = "expertise/session")
public class SessionController {

  @Autowired
  private SessionService sessionService;

  /**
   * Set next group session.
   * 
   * @param groupSession
   * @return
   */
  @PostMapping("/set")
  public GroupSession setNextSession(@RequestBody SetSessionRequest setSessionRequest) {
    return sessionService.setNextSession(setSessionRequest);
  }

  /**
   * Get {@code GroupSession} from given mentor group id.
   * 
   * @return
   */
  @GetMapping("/get/{mentorGroupId}")
  public GroupSession getSessionInfo(@PathVariable("mentorGroupId") String mentorGroupId) {
    return sessionService.getSessionInfo(mentorGroupId);
  }

  /**
   * Rate session request.
   * 
   * @param rateSession
   */
  @PostMapping("/rate")
  public void rateSession(@RequestBody RateSessionRequest rateSession) {
    sessionService.rateSession(rateSession);
  }
}
