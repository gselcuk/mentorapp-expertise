package com.obss.mentor.expertise.constant;

/**
 * Relation phases.
 * 
 * @author Goktug Selcuk
 *
 */
public enum RelationPhase {

  NOT_STARTED,
  READY,
  ONGOING,
  FINISHED;

  /**
   * Relation phase is not started control.
   * 
   * @param relationPhase
   * @return
   */
  public static boolean isNotStarted(RelationPhase relationPhase) {
    return NOT_STARTED.equals(relationPhase);
  }

}
