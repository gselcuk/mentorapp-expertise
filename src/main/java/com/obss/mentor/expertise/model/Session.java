package com.obss.mentor.expertise.model;

import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
import com.obss.mentor.expertise.serviceparam.SessionInfoUI;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Session model.
 * 
 * @author Goktug Selcuk
 *
 */
@Data
@EqualsAndHashCode
public class Session {

  @EqualsAndHashCode.Exclude
  private String sessionId;

  private String sessionDescription;
  private String sessionDate;

  @EqualsAndHashCode.Exclude
  private List<SessionRating> sessionRatings;

  public SessionInfoUI toSessionInfoUI(String userId, String mentorGroupId) {
    SessionInfoUI sessionInfoUI = new SessionInfoUI();
    sessionInfoUI.setSessionId(this.sessionId);
    sessionInfoUI.setSessionDescription(this.sessionDescription);
    sessionInfoUI.setSessionDate(this.sessionDate);
    Double currentUserRating = (double) 0;
    
    if (CollectionUtils.isNotEmpty(this.sessionRatings)) {
      currentUserRating = userId.equals(mentorGroupId)
          ? this.sessionRatings.stream().mapToDouble(SessionRating::getRating).average().orElse(0)
          : this.sessionRatings.stream().filter(rating -> rating.getUserId().equals(userId))
              .mapToDouble(SessionRating::getRating).findFirst().orElse(0);
      sessionInfoUI.setCurrentUserRating(currentUserRating);
    }


    return sessionInfoUI;
  }
}
