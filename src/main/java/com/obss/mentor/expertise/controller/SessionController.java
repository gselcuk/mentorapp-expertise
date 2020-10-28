package com.obss.mentor.expertise.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.obss.mentor.expertise.model.GroupSession;
import com.obss.mentor.expertise.service.SessionService;

/***
 * Session controller.
 * 
 * @author Goktug Selcuk
 *
 */
@RestController
@RequestMapping(value = "session")
public class SessionController {
  
  @Autowired
  private SessionService sessionService;

  /**
   * Set next group session.
   * 
   * @param groupSession
   * @return
   */
  @PostMapping("/save")
  public GroupSession setNextSession(@RequestBody GroupSession groupSession) {
    return sessionService.saveSession(groupSession);

  }
}
