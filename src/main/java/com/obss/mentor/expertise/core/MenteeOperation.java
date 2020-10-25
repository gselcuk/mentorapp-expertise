package com.obss.mentor.expertise.core;

import java.util.Arrays;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;
import com.obss.mentor.expertise.model.GroupExpertiseRelation;

/**
 * Mentee related operations.
 * 
 * @author Goktug Selcuk
 *
 */
@Component
public class MenteeOperation implements Operation {

  @Override
  public GroupExpertiseRelation joinRelation(GroupExpertiseRelation input, String userId) {
    
    if (CollectionUtils.isNotEmpty(input.getMentees()))
      input.getMentees().add(userId);
    else
      input.setMentees(Arrays.asList(userId));
    
    return input;
  }

}
