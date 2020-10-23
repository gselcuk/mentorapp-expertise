package com.obss.mentor.expertise.service;

import java.util.Arrays;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;
import com.obss.mentor.expertise.constant.AppServer;
import com.obss.mentor.expertise.constant.Endpoint;
import com.obss.mentor.expertise.constant.RelationPhase;
import com.obss.mentor.expertise.model.AppUser;
import com.obss.mentor.expertise.model.BeMentorRequest;
import com.obss.mentor.expertise.model.GroupExpertiseRelation;
import com.obss.mentor.expertise.model.ListRelationResponse;
import com.obss.mentor.expertise.model.RelationResponse;
import com.obss.mentor.expertise.repository.GroupExpertiseRelationRepository;
import com.obss.mentor.expertise.util.SecurityUtils;

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
  @Autowired
  private SecurityUtils<AppUser> securityUtils;

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
    AppUser appUser = new AppUser();
    appUser.setId(mentorGroupId);
    restTemplate.exchange(appServer.getUrlMentorUser(Endpoint.MENTOR_USER_UPDATE_ROLE),
        HttpMethod.POST, securityUtils.createRequestWithAuth(authToken, appUser), AppUser.class);
  }

  /**
   * Get model from database.
   * 
   * @param groupExpertiseRelation
   * @return
   */
  public ListRelationResponse getRelationById(String id, String authToken) {
    GroupExpertiseRelation groupExpertiseRelation =
        groupExpertiseRelationRepository.findById(id).orElse(null);

    RelationResponse relationResponse = new RelationResponse();

    relationResponse
        .setMentorName(findUserNameFromId(groupExpertiseRelation.getMentorGroupId(), authToken));
    relationResponse.setExpertiseAreas(groupExpertiseRelation.getExpertiseAreas());
    relationResponse.setRelationPhase(groupExpertiseRelation.getRelationPhase());

    if (!CollectionUtils.isEmpty(groupExpertiseRelation.getOtherMentors()))
      relationResponse.setOtherMentors(groupExpertiseRelation.getOtherMentors().stream()
          .map(mentor -> findUserNameFromId(mentor, authToken)).collect(Collectors.toList()));
    if (!CollectionUtils.isEmpty(groupExpertiseRelation.getMentees()))
      relationResponse.setOtherMentees(groupExpertiseRelation.getMentees().stream()
          .map(mentee -> findUserNameFromId(mentee, authToken)).collect(Collectors.toList()));
    return ListRelationResponse.builder().listRelation(Arrays.asList(relationResponse)).build();
  }

  /**
   * 
   * @param id
   * @return
   */
  private String findUserNameFromId(String id, String authToken) {
    return restTemplate
        .exchange(setUrlId(appServer.getUrlMentorUser(Endpoint.MENTOR_GET_USER_BY_ID), id),
            HttpMethod.GET, securityUtils.createRequestWithAuth(authToken, null), AppUser.class)
        .getBody().getUserName();
  }

  /**
   * Set user for url.
   * 
   * @param urlMentorUser
   * @param id
   * @return
   */
  private String setUrlId(String urlMentorUser, String id) {
    return String.format(urlMentorUser, id);
  }


}
