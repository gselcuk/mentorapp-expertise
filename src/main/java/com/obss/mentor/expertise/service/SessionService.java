package com.obss.mentor.expertise.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.google.common.collect.Lists;
import com.obss.mentor.expertise.constant.RelationPhase;
import com.obss.mentor.expertise.exception.MentorException;
import com.obss.mentor.expertise.model.GroupExpertiseRelation;
import com.obss.mentor.expertise.model.GroupSession;
import com.obss.mentor.expertise.model.Session;
import com.obss.mentor.expertise.model.SessionRating;
import com.obss.mentor.expertise.repository.GroupExpertiseRelationRepository;
import com.obss.mentor.expertise.repository.GroupSessionRepository;
import com.obss.mentor.expertise.serviceparam.GetSessionResponse;
import com.obss.mentor.expertise.serviceparam.RateSessionRequest;
import com.obss.mentor.expertise.serviceparam.SetSessionRequest;

/**
 * Session related operations can be handled here.
 * 
 * @author Goktug Selcuk
 *
 */
@Service
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

    Session currentSession = new Session();
    BeanUtils.copyProperties(setSessionRequest, currentSession);
    currentSession.setSessionId(setSessionRequest.getCurrentSessionId());

    if (currentSession.equals(groupSession.getCurrentSession()))
      return groupSession;
    else if (groupSession.getCurrentSession() != null
        && StringUtils.isNotEmpty(currentSession.getSessionId())
        && currentSession.getSessionId().equals(groupSession.getCurrentSession().getSessionId())) {
      currentSession.setSessionId(groupSession.getCurrentSession().getSessionId());
      groupSession.setCurrentSession(currentSession);
      return groupSessionRepository.save(groupSession);
    }

    if (StringUtils.isNotEmpty(groupSession.getMentorGroupId())) {
      if (CollectionUtils.isNotEmpty(groupSession.getSessionHistory()))
        groupSession.getSessionHistory().add(groupSession.getCurrentSession());
      else
        groupSession.setSessionHistory(Lists.newArrayList(groupSession.getCurrentSession()));
    } else {
      groupSession.setMentorGroupId(setSessionRequest.getMentorGroupId());
    }

    currentSession.setSessionId(UUID.randomUUID().toString());
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
  public GetSessionResponse getSessionInfo(String mentorGroupId, String userId) {
    GroupSession groupSession =
        groupSessionRepository.findById(mentorGroupId).orElse(new GroupSession());
    GetSessionResponse getSessionResponse = new GetSessionResponse();
    getSessionResponse.setMentorGroupId(groupSession.getMentorGroupId());
    getSessionResponse.setCurrentSession(groupSession.getCurrentSession());
    if (CollectionUtils.isNotEmpty(groupSession.getSessionHistory()))
      getSessionResponse.setSessionHistory(groupSession.getSessionHistory().stream()
          .map(session -> session.toSessionInfoUI(userId, mentorGroupId))
          .collect(Collectors.toList()));
    return getSessionResponse;
  }

  /**
   * Rate session.
   * 
   * @param rateSession
   */
  public void rateSession(RateSessionRequest rateSession) {
    if (StringUtils.isNotEmpty(rateSession.getSessionId())) {
      GroupSession groupSession =
          groupSessionRepository.findById(rateSession.getMentorGroupId()).orElse(null);

      if (groupSession == null)
        throw new MentorException("Session not found");
      // TODO:last minute implementation fix the design.

      List<Session> relatedSession = groupSession.getSessionHistory().stream().map(sessionHist -> {
        SessionRating sessionRating =
            SessionRating.builder().rating(rateSession.getSessionRating().getRating())
                .userId(rateSession.getSessionRating().getUserId()).build();
        if (StringUtils.isNotEmpty(sessionHist.getSessionId())
            && sessionHist.getSessionId().equals(rateSession.getSessionId())) {
          List<SessionRating> ratings = new ArrayList<>();

          if (CollectionUtils.isNotEmpty(sessionHist.getSessionRatings())) {
            if (CollectionUtils.isEmpty(sessionHist.getSessionRatings().stream()
                .filter(sessionRate -> sessionRate.getUserId()
                    .equals(rateSession.getSessionRating().getUserId()))
                .collect(Collectors.toList()))) {
              ratings = sessionHist.getSessionRatings();
              ratings.add(sessionRating);
            } else {
              ratings = sessionHist.getSessionRatings().stream().map(sessionRate -> {
                if (sessionRate.getUserId().equals(rateSession.getSessionRating().getUserId())) {
                  sessionRate.setRating(rateSession.getSessionRating().getRating());
                }
                return sessionRate;
              }).collect(Collectors.toList());
            }

          } else
            ratings = Arrays.asList(sessionRating);


          sessionHist.setSessionRatings(ratings);
        }
        return sessionHist;
      }).collect(Collectors.toList());

      groupSession.setSessionHistory(relatedSession);

      groupSessionRepository.save(groupSession);
    }

  }

}
