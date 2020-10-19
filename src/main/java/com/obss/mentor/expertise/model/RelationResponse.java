package com.obss.mentor.expertise.model;

import lombok.Builder;
import lombok.Data;

/**
 * 
 * @author Goktug Selcuk
 *
 */
@Data
@Builder
public class RelationResponse {
  private GroupExpertiseRelation groupExpertiseRelation;
  private String mentorName;
}
