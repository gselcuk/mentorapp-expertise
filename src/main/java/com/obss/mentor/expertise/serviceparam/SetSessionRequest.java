package com.obss.mentor.expertise.serviceparam;

import lombok.Data;

@Data
public class SetSessionRequest {

  private String mentorGroupId;
  private String currentSessionId;
  private String sessionDescription;
  private String sessionDate;

}
