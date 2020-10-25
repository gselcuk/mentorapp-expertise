package com.obss.mentor.expertise.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import com.obss.mentor.expertise.constant.DateFormat;
import com.obss.mentor.expertise.constant.GroupName;
import com.obss.mentor.expertise.constant.RelationPhase;
import com.obss.mentor.expertise.core.OperationFactory;
import com.obss.mentor.expertise.exception.MentorException;
import com.obss.mentor.expertise.model.GroupExpertiseRelation;
import com.obss.mentor.expertise.model.GroupExpertiseRelationRequest;
import com.obss.mentor.expertise.model.RelationResponse;
import com.obss.mentor.expertise.repository.GroupExpertiseRelationRepository;
import com.obss.mentor.expertise.serviceparam.JoinRelationRequest;
import com.obss.mentor.expertise.serviceparam.ListRelationResponse;
import com.obss.mentor.expertise.serviceparam.SearchExpertiseRequest;
import com.obss.mentor.expertise.util.DateUtils;

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
  @Autowired
  private OperationFactory operationFactory;
  @Autowired
  private DateUtils dateUtils;

  /**
   * Save model to database.For be mentor method relation phase is not started.
   * 
   * @param groupExpertiseRelation
   * @return
   */
  public GroupExpertiseRelation beMentor(
      GroupExpertiseRelationRequest groupExpertiseRelationRequest) {
    GroupExpertiseRelation groupExpertiseRelation =
        groupExpertiseRelationRequest.getGroupExpertiseRelation();
    groupExpertiseRelation.setRelationPhase(RelationPhase.NOT_STARTED);

    saveGroupExpertiseRelation(groupExpertiseRelation);

    if (!groupExpertiseRelation.isApprovalNeeded())
      userService.setUserRole(groupExpertiseRelation.getMentorGroupId(),
          groupExpertiseRelationRequest.getAuthToken(), GroupName.MENTOR);

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
   * Search given keywords and expertise names at database.
   * 
   * @param searchExpertiseRequest
   * @return
   */
  public ListRelationResponse search(SearchExpertiseRequest searchExpertiseRequest,
      Pageable pageable, String authToken) {
    List<GroupExpertiseRelation> asd = groupExpertiseRelationRepository
    .findByExpertiseName("Java", pageable).getContent();
    List<List<GroupExpertiseRelation>> groupExpertiseRelations =
        searchExpertiseRequest.getExpertiseNames().stream()
            .map(expertise -> groupExpertiseRelationRepository
                .findByExpertiseName(expertise, pageable).getContent())
            .collect(Collectors.toList());

    List<RelationResponse> listRelations = new ArrayList<>();

    groupExpertiseRelations.forEach(relation -> listRelations.addAll(
        relation.stream().filter(rel -> !rel.getRelationPhase().equals(RelationPhase.ONGOING))
            .map(RelationResponse::fromGroupExpertiseRelation).collect(Collectors.toList())));

    return new ListRelationResponse(listRelations.stream()
        .map(relationResponse -> userService.getUserNames(relationResponse, authToken))
        .collect(Collectors.toList()));
  }


  /**
   * Save given methot to databse or send to approval.
   * 
   * @param groupExpertiseRelationRequest
   * @return
   */
  public GroupExpertiseRelation saveGroupExpertiseRelation(
      GroupExpertiseRelation groupExpertiseRelation) {

    approvalService.doOperation(groupExpertiseRelation, groupExpertiseRelationRepository);

    return groupExpertiseRelation;
  }


  /**
   * Set {@code RelationPhase} to ready then do database oeration or send to approval.
   * 
   * @param groupExpertiseRelationRequest
   * @return
   */
  public GroupExpertiseRelation joinRelation(JoinRelationRequest joinRelationRequest) {
    GroupExpertiseRelation groupExpertiseRelation = groupExpertiseRelationRepository
        .findById(joinRelationRequest.getMentorGroupId()).orElse(null);

    if (groupExpertiseRelation == null)
      throw new MentorException("Given id not found!");

    operationFactory.getOperation(joinRelationRequest.getGroupName())
        .joinRelation(groupExpertiseRelation, joinRelationRequest.getUserId());

    if (isFirstMenteeJoin(groupExpertiseRelation, joinRelationRequest.getGroupName())) {
      groupExpertiseRelation.setRelationPhase(RelationPhase.READY);
      groupExpertiseRelation.setStartDate(dateUtils.getCurrentDate(DateFormat.CALENDAR_DATE));
    }

    if (!groupExpertiseRelation.isApprovalNeeded())
      userService.setUserRole(joinRelationRequest.getUserId(), joinRelationRequest.getAuthToken(),GroupName.MENTEE);

    saveGroupExpertiseRelation(groupExpertiseRelation);
    return groupExpertiseRelation;
  }


  /**
   * Check first mentee join.
   * 
   * @param groupExpertiseRelation
   * @return
   */
  private boolean isFirstMenteeJoin(GroupExpertiseRelation groupExpertiseRelation,
      GroupName groupName) {
    return CollectionUtils.isNotEmpty(groupExpertiseRelation.getMentees())
        && groupExpertiseRelation.getMentees().size() == 1
        && RelationPhase.isNotStarted(groupExpertiseRelation.getRelationPhase())
        && GroupName.isMentee(groupName);
  }



}
