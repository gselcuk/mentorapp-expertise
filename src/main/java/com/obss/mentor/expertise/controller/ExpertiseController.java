package com.obss.mentor.expertise.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.obss.mentor.expertise.model.GroupExpertiseRelation;
import com.obss.mentor.expertise.service.ExpertiseService;

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
   * Get {@code GroupExpertiseRelation} from database.
   * 
   * @param id
   * @return
   */
  @GetMapping(value = "/get/{id}")
  public GroupExpertiseRelation getExpertiseRelation(String id) {
    return expertiseService.getRelationById(id);
  }


  /**
   * Save expertise.
   * 
   * @param groupExpertiseRelation
   * @return
   */
  @PostMapping(value = "/save")
  public GroupExpertiseRelation saveExpertise(@RequestBody GroupExpertiseRelation groupExpertiseRelation) {
    return expertiseService.saveRelation(groupExpertiseRelation);
  }

}
