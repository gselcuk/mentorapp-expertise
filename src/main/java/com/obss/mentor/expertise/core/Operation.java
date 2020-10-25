package com.obss.mentor.expertise.core;

/**
 * Operation type can be differentiated form MENTEE to MENTOR. Add related methods here to avoid if
 * else.
 */
import com.obss.mentor.expertise.model.GroupExpertiseRelation;

public interface Operation {

  public GroupExpertiseRelation joinRelation(GroupExpertiseRelation input, String userId);

}
