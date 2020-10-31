package com.obss.mentor.expertise.service;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.obss.mentor.expertise.constant.RelationPhase;
import com.obss.mentor.expertise.exception.MentorException;
import com.obss.mentor.expertise.model.GroupExpertiseRelation;
import com.obss.mentor.expertise.model.GroupSession;
import com.obss.mentor.expertise.model.Session;
import com.obss.mentor.expertise.repository.GroupExpertiseRelationRepository;
import com.obss.mentor.expertise.repository.GroupSessionRepository;
import com.obss.mentor.expertise.serviceparam.SetSessionRequest;

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
  @Autowired
  private GroupExpertiseRelationRepository groupExpertiseRelationRepository;

  /**
   * Set next ses
   * 
   * @param groupSession
   * @return
   */
  public GroupSession saveSession(GroupSession groupSession) {
    return groupSessionRepository.save(groupSession);
  }

  /**
   * 
   * @param setSessionRequest
   * @return
   */
  public GroupSession setNextSession(SetSessionRequest setSessionRequest) {
    GroupSession groupSession = groupSessionRepository
        .findById(setSessionRequest.getMentorGroupId()).orElse(new GroupSession());

    if (CollectionUtils.isNotEmpty(groupSession.getSessionHistory()))
      groupSession.getSessionHistory().add(groupSession.getCurrentSession());
    else
      groupSession.setMentorGroupId(setSessionRequest.getMentorGroupId());

    Session currentSession = new Session();
    BeanUtils.copyProperties(setSessionRequest, currentSession);
    groupSession.setCurrentSession(currentSession);
    GroupExpertiseRelation groupExpertiseRelation = groupExpertiseRelationRepository
        .findById(setSessionRequest.getMentorGroupId()).orElse(null);

    if (groupExpertiseRelation == null)
      throw new MentorException("Relation not found");

    groupExpertiseRelation.setRelationPhase(RelationPhase.ONGOING);
    groupExpertiseRelationRepository.save(groupExpertiseRelation);

    return groupSessionRepository.save(groupSession);
  }

  /**
   * Get {@code GroupSession} for given mentor group id.
   * 
   * @param mentorGroupId
   * @return
   */
  public GroupSession getSessionInfo(String mentorGroupId) {
    return groupSessionRepository.findById(mentorGroupId).orElse(new GroupSession());
  }

}
