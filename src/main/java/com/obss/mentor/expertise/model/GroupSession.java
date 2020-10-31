package com.obss.mentor.expertise.model;

import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Group session information.
 * 
 * @author Goktug Selcuk
 *
 */
@Data
@Document(indexName = "group_session")
@EqualsAndHashCode
public class GroupSession {
  @Id
  private String mentorGroupId;
  private Session currentSession;
  
  @EqualsAndHashCode.Exclude
  private List<Session> sessionHistory;
  
  

}
