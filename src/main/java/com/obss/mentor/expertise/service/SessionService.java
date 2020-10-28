package com.obss.mentor.expertise.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.obss.mentor.expertise.model.GroupSession;
import com.obss.mentor.expertise.repository.GroupSessionRepository;

/**
 * Session related operations can be handled here.
 * 
 * @author Goktug Selcuk
 *
 */
@Component
public class SessionService {
  @Autowired
  private GroupSessionRepository groupSessionRepository;
  /**
   * Set next ses
   * @param groupSession
   * @return
   */
  public GroupSession saveSession(GroupSession groupSession) {
    return groupSessionRepository.save(groupSession);
  }

}
