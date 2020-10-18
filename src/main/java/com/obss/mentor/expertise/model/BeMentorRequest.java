package com.obss.mentor.expertise.model;

import lombok.Data;

/**
 * UI model of e mentor.
 * 
 * @author Goktug Selcuk
 *
 */
@Data
public class BeMentorRequest {
  
  private GroupExpertiseRelation groupExpertiseRelation;
  private String authToken;

}
