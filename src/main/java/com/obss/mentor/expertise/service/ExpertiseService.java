package com.obss.mentor.expertise.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import com.obss.mentor.expertise.constant.RelationPhase;
import com.obss.mentor.expertise.model.BeMentorRequest;
import com.obss.mentor.expertise.model.GroupExpertiseRelation;
import com.obss.mentor.expertise.model.RelationResponse;
import com.obss.mentor.expertise.repository.GroupExpertiseRelationRepository;
import com.obss.mentor.expertise.serviceparam.ListRelationResponse;
import com.obss.mentor.expertise.serviceparam.SearchExpertiseRequest;

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
  private UserService userService;


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
      userService.setUserRole(groupExpertiseRelation.getMentorGroupId(),
          beMentorRequest.getAuthToken());

    return groupExpertiseRelation;
  }



  /**
   * Get model from database.
   * 
   * @param groupExpertiseRelation
   * @return
   */
  public ListRelationResponse getRelationById(String id, String authToken) {
    RelationResponse relationResponse = RelationResponse
        .fromGroupExpertiseRelation(groupExpertiseRelationRepository.findById(id).orElse(null));

    return new ListRelationResponse(
        Arrays.asList(userService.getUserNames(relationResponse, authToken)));
  }


  /**
   * 
   * @param searchExpertiseRequest
   * @return
   */
  public ListRelationResponse search(SearchExpertiseRequest searchExpertiseRequest,
      Pageable pageable, String authToken) {
    List<List<GroupExpertiseRelation>> groupExpertiseRelations =
        searchExpertiseRequest.getExpertiseNames().stream()
            .map(expertise -> groupExpertiseRelationRepository
                .findByExpertiseName(expertise, pageable).getContent())
            .collect(Collectors.toList());

    List<RelationResponse> listRelations = new ArrayList<>();

    groupExpertiseRelations.forEach(relation -> listRelations.addAll(relation.stream()
        .map(RelationResponse::fromGroupExpertiseRelation).collect(Collectors.toList())));
    
    return new ListRelationResponse(listRelations.stream()
        .map(relationResponse -> userService.getUserNames(relationResponse, authToken))
        .collect(Collectors.toList()));
  }



}
