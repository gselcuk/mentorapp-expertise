package com.obss.mentor.expertise.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.obss.mentor.expertise.model.GroupExpertiseRelationRequest;
import com.obss.mentor.expertise.model.GroupExpertiseRelation;
import com.obss.mentor.expertise.service.ExpertiseService;
import com.obss.mentor.expertise.serviceparam.JoinRelationRequest;
import com.obss.mentor.expertise.serviceparam.ListRelationResponse;
import com.obss.mentor.expertise.serviceparam.SearchExpertiseRequest;

/**
 * Controller for expertise.
 * 
 * @author Goktug Selcuk
 *
 */
@RestController
@RequestMapping(value = "expertise")
public class ExpertiseController {

  @Autowired
  private ExpertiseService expertiseService;

  /**
   * Create {@code ListRelationResponse}.Id is user id for getting related relations.
   * 
   * @param id
   * @return
   */
  @GetMapping(value = "/get/{authToken}/{id}")
  public ListRelationResponse getExpertiseRelation(@PathVariable("id") String id,
      @RequestParam(value = "page", defaultValue = "0") int page,
      @RequestParam(value = "size", defaultValue = "10") int size,
      @PathVariable("authToken") String authToken) {
    return expertiseService.getRelationById(id, PageRequest.of(page, size), authToken);
  }

  /**
   * Search expertise with keyword and expertise name.
   * 
   * @param searchExpertiseRequest Expertise name and keywords.
   * @param page Page for pagination.
   * @param size Size of page.
   * @param authToken Auth token for authorization.
   * @return
   */
  @PostMapping(value = "search/{authToken}")
  public ListRelationResponse search(@RequestBody SearchExpertiseRequest searchExpertiseRequest,
      @RequestParam(value = "page", defaultValue = "0") int page,
      @RequestParam(value = "size", defaultValue = "10") int size,
      @PathVariable("authToken") String authToken) {
    return expertiseService.search(searchExpertiseRequest, PageRequest.of(page, size), authToken);
  }


  /**
   * Save expertise.
   * 
   * @param groupExpertiseRelation
   * @return
   */
  @PostMapping(value = "/be-mentor")
  public GroupExpertiseRelation beMentor(
      @RequestBody GroupExpertiseRelationRequest groupExpertiseRelationRequest) {
    return expertiseService.beMentor(groupExpertiseRelationRequest);
  }

  /**
   * Join mentor group.
   * 
   * @param groupExpertiseRelationRequest
   * @return
   */
  @PostMapping(value = "/join-relation")
  public GroupExpertiseRelation joinRelation(@RequestBody JoinRelationRequest joinRelationRequest) {
    return expertiseService.joinRelation(joinRelationRequest);
  }

}
