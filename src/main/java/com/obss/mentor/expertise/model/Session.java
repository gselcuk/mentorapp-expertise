package com.obss.mentor.expertise.model;

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
   private boolean isCompleted;
}
