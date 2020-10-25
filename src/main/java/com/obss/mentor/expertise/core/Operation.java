package com.obss.mentor.expertise.core;

import com.obss.mentor.expertise.model.AppUser;
import com.obss.mentor.expertise.model.GroupExpertiseRelation;

/**
 * 
 * Operation type can be differentiated form MENTEE to MENTOR. Add related methods here to avoid if
 * else.
 * 
 * @author Goktug Selcuk
 * 
 */
public interface Operation {

  GroupExpertiseRelation joinRelation(GroupExpertiseRelation input, String userId);

  void setRole(AppUser appUser, String authToken);

}
