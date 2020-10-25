package com.obss.mentor.expertise.serviceparam;

import com.obss.mentor.expertise.constant.GroupName;
import lombok.Data;

/**
 * Join relation request.
 * 
 * @author Goktug Selcuk
 *
 */
@Data
public class JoinRelationRequest {

  private String mentorGroupId;
  private String userId;
  private GroupName groupName;
  private String authToken;

}
