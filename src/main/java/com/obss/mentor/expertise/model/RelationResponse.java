package com.obss.mentor.expertise.model;

import java.util.List;
import com.obss.mentor.expertise.constant.RelationPhase;
import lombok.Data;

/**
 * 
 * @author Goktug Selcuk
 *
 */
@Data
public class RelationResponse {

  private String mentorName;
  private List<String> otherMentors;
  private List<String> otherMentees;
  private List<Expertises> expertiseAreas;
  private RelationPhase relationPhase;

}
