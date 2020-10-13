package com.obss.mentor.expertise.model;

import java.util.List;
import lombok.Data;

/**
 * 
 * @author Goktug Selcuk
 *
 */
@Data
public class GroupExpertiseRelation {

  private String mentorGroupId;
  private List<Expertises> expertiseAreas;
  private String menteeGroupId;

}
