package com.obss.mentor.expertise.model;

import lombok.Builder;
import lombok.Data;

/**
 * Session ratings.
 * 
 * @author Goktug Selcuk
 *
 */
@Data
@Builder
public class SessionRating {

  private String userId;
  private Double rating;

}
