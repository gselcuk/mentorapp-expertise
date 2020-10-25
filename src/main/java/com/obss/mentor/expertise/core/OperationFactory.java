package com.obss.mentor.expertise.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.obss.mentor.expertise.constant.GroupName;
import com.obss.mentor.expertise.exception.MentorException;

/**
 * Get related operation related to group name.
 * 
 * @author Goktug Selcuk
 *
 */
@Component
public class OperationFactory {

  @Autowired
  private MentorOperation mentorOperation;

  @Autowired
  private MenteeOperation menteeOperation;

  public Operation getOperation(GroupName groupName) {
    
    if (GroupName.isMentor(groupName))
      return mentorOperation;
    else if (GroupName.isMentee(groupName))
      return menteeOperation;

    throw new MentorException("Given group name not implemented yet!");
  }
}
