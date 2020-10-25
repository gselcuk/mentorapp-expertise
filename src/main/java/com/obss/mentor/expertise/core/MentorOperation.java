package com.obss.mentor.expertise.core;

import java.util.Arrays;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;
import com.obss.mentor.expertise.model.GroupExpertiseRelation;

/**
 * Mentor related operation.
 * 
 * @author Goktug Selcuk
 *
 */
@Component
public class MentorOperation implements Operation {

  @Override
  public GroupExpertiseRelation joinRelation(GroupExpertiseRelation input, String userId) {
    
    if (CollectionUtils.isNotEmpty(input.getOtherMentors()))
      input.getOtherMentors().add(userId);
    else
      input.setOtherMentors(Arrays.asList(userId));
    
    return input;
  }

}
