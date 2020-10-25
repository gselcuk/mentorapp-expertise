package com.obss.mentor.expertise.constant;

public enum GroupName {

  MENTEE,
  MENTOR;

  /**
   * Check is mentee.
   * 
   * @param groupName
   * @return
   */
  public static boolean isMentee(GroupName groupName) {
    return MENTEE.equals(groupName);
  }

  /**
   * Check is mentor.
   * 
   * @param groupName
   * @return
   */
  public static boolean isMentor(GroupName groupName) {
    return MENTOR.equals(groupName);
  }
}
