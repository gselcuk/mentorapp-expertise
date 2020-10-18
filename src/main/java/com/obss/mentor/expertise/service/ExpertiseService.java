package com.obss.mentor.expertise.service;

import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import com.obss.mentor.expertise.constant.AppServer;
import com.obss.mentor.expertise.constant.Endpoint;
import com.obss.mentor.expertise.constant.RelationPhase;
import com.obss.mentor.expertise.model.AppUser;
import com.obss.mentor.expertise.model.BeMentorRequest;
import com.obss.mentor.expertise.model.GroupExpertiseRelation;
import com.obss.mentor.expertise.model.ListRelationResponse;
import com.obss.mentor.expertise.repository.GroupExpertiseRelationRepository;

/**
 * Expertise service .
 * 
 * @author Goktug Selcuk
 *
 */
@Component
public class ExpertiseService {

  @Autowired
  private GroupExpertiseRelationRepository groupExpertiseRelationRepository;
  @Autowired
  private ApprovalService<GroupExpertiseRelation> approvalService;
  @Autowired
  private RestTemplate restTemplate;
  @Autowired
  private AppServer appServer;

  /**
   * Save model to database.
   * 
   * @param groupExpertiseRelation
   * @return
   */
  public GroupExpertiseRelation saveRelation(BeMentorRequest beMentorRequest) {
    GroupExpertiseRelation groupExpertiseRelation = beMentorRequest.getGroupExpertiseRelation();
    groupExpertiseRelation.setRelationPhase(RelationPhase.NOT_STARTED);

    approvalService.doOperation(groupExpertiseRelation, groupExpertiseRelationRepository);

    if (!groupExpertiseRelation.isApprovalNeeded())
      setUserRole(groupExpertiseRelation.getMentorGroupId(), beMentorRequest.getAuthToken());

    return groupExpertiseRelation;
  }

  /**
   * Call related service.
   * 
   * @param menteeGroupId
   */
  private void setUserRole(String mentorGroupId, String authToken) {
    HttpHeaders headers = new HttpHeaders();
    headers.set("Authorization", "Basic " + authToken);
    restTemplate.exchange(appServer.getUrlMentorUser(Endpoint.MENTOR_USER_UPDATE_ROLE),
        HttpMethod.POST, new HttpEntity<>(AppUser.builder().id(mentorGroupId).build(), headers),
        AppUser.class);
  }

  /**
   * Get model from database.
   * 
   * @param groupExpertiseRelation
   * @return
   */
  public List<ListRelationResponse> getRelationById(String id) {

    return Arrays.asList(ListRelationResponse.builder()
        .groupExpertiseRelation(groupExpertiseRelationRepository.findById(id).orElse(null))
        .mentorName(findUserNameFromId(id)).build());
  }

  /**
   * 
   * @param id
   * @return
   */
  private String findUserNameFromId(String id) {
    // TODO:find user name from given id.
    return StringUtils.EMPTY;
  }


}
