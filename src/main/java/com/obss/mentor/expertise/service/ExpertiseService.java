package com.obss.mentor.expertise.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import com.netflix.discovery.EurekaClient;
import com.obss.mentor.expertise.model.AppUser;
import com.obss.mentor.expertise.model.GroupExpertiseRelation;
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
  private EurekaClient discoveryClient;

  /**
   * Save model to database.
   * 
   * @param groupExpertiseRelation
   * @return
   */
  public GroupExpertiseRelation saveRelation(GroupExpertiseRelation groupExpertiseRelation) {
    approvalService.doOperation(groupExpertiseRelation, groupExpertiseRelationRepository);

    if (!groupExpertiseRelation.isApprovalNeeded())
      setUserRole(groupExpertiseRelation.getMenteeGroupId());

    return groupExpertiseRelation;
  }

  /**
   * Call related service.
   * 
   * @param menteeGroupId
   */
  private void setUserRole(String mentorGroupId) {
    String url = discoveryClient.getNextServerFromEureka("mentorapp-user",false).getHomePageUrl();
    restTemplate.postForEntity(url + "set/role/mentorgroupleader",
        AppUser.builder().id(mentorGroupId).build(), AppUser.class);
  }

  /**
   * Get model from database.
   * 
   * @param groupExpertiseRelation
   * @return
   */
  public GroupExpertiseRelation getRelationById(String id) {
    return groupExpertiseRelationRepository.findById(id).orElse(null);
  }


}
