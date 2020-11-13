package com.obss.mentor.expertise.serviceparam;

import java.util.List;
import com.obss.mentor.expertise.model.Session;
import lombok.Data;

@Data
public class GetSessionResponse {
  
  private String mentorGroupId;
  private Session currentSession;
  private List<SessionInfoUI> sessionHistory;
}
