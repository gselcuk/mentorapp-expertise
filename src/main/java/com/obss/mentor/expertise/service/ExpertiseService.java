package com.obss.mentor.expertise.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
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

  /**
   * Save model to database.
   * 
   * @param groupExpertiseRelation
   * @return
   */
  public GroupExpertiseRelation saveRelation(GroupExpertiseRelation groupExpertiseRelation) {
    return approvalService.doOperation(groupExpertiseRelation, groupExpertiseRelationRepository);
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
