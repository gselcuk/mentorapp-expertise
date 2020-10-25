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

  private String mentorGroupId;
  private String mentorName;
  private List<String> otherMentors;
  private List<String> otherMentees;
  private List<Expertises> expertiseAreas;
  private String startDate;
  private RelationPhase relationPhase;

  public static RelationResponse fromGroupExpertiseRelation(
      GroupExpertiseRelation groupExpertiseRelation) {
    RelationResponse relationResponse = new RelationResponse();

    if (groupExpertiseRelation == null)
      return relationResponse;
    relationResponse.setMentorGroupId(groupExpertiseRelation.getMentorGroupId());
    relationResponse.setExpertiseAreas(groupExpertiseRelation.getExpertiseAreas());
    relationResponse.setRelationPhase(groupExpertiseRelation.getRelationPhase());
    relationResponse.setOtherMentees(groupExpertiseRelation.getMentees());
    relationResponse.setOtherMentors(groupExpertiseRelation.getOtherMentors());
    relationResponse.setMentorName(groupExpertiseRelation.getMentorGroupId());
    relationResponse.setStartDate(groupExpertiseRelation.getStartDate());
    return relationResponse;

  }

}
