package com.obss.mentor.expertise.model;

import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import com.obss.mentor.expertise.constant.RelationPhase;
import lombok.Data;

/**
 * 
 * @author Goktug Selcuk
 *
 */
@Data
@Document(indexName = "expertise_relations")
public class GroupExpertiseRelation implements Approvable {
  
  @Id
  private String mentorGroupId;
  private List<String> otherMentors;
  private List<Expertises> expertiseAreas;
  private List<String> mentees;
  private RelationPhase relationPhase;


  @Override
  public boolean isApprovalNeeded() {
    // TODO: for now not send to approval.
    return false;
  }



}
