package com.obss.mentor.expertise.model;

import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import lombok.Data;

/**
 * Group session information.
 * 
 * @author Goktug Selcuk
 *
 */
@Data
@Document(indexName = "group_session")
public class GroupSession {
  @Id
  private String mentorGroupId;
  private Session currentSession;
  private List<Session> sessionHistory;

}
