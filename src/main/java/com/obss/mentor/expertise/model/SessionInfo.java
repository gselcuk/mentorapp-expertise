package com.obss.mentor.expertise.model;

import java.util.Date;
import com.obss.mentor.expertise.constant.SessionPhase;
import lombok.Data;

/**
 * Session information.
 * 
 * @author Goktug Selcuk
 *
 */
@Data
public class SessionInfo {

  private String explanation;
  private Date sessionDate;
  private SessionPhase sessionPhase;

}
