package com.obss.mentor.expertise.model;

import java.util.List;
import lombok.Data;

/**
 * Session model.
 * 
 * @author Goktug Selcuk
 *
 */
@Data
public class Session {

  private String sessionDescription;
  private String sessionDate;
  private List<SessionRating> sessionRatings;
}
