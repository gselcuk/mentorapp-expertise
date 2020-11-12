package com.obss.mentor.expertise.serviceparam;

import com.obss.mentor.expertise.model.SessionRating;
import lombok.Data;

/**
 * Rate session.
 * 
 * @author Goktug Selcuk
 *
 */
@Data
public class RateSessionRequest {
  
  private String mentorGroupId;
  private String sessionDescription;
  private String sessionDate;
  private SessionRating sessionRating;
}
