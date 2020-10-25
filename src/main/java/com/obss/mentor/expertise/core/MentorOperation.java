package com.obss.mentor.expertise.core;

import java.util.Arrays;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import com.obss.mentor.expertise.constant.AppServer;
import com.obss.mentor.expertise.constant.Endpoint;
import com.obss.mentor.expertise.model.AppUser;
import com.obss.mentor.expertise.model.GroupExpertiseRelation;
import com.obss.mentor.expertise.util.SecurityUtils;

/**
 * Mentor related operation.
 * 
 * @author Goktug Selcuk
 *
 */
@Component
public class MentorOperation implements Operation {

  @Autowired
  private RestTemplate restTemplate;
  @Autowired
  private AppServer appServer;
  @Autowired
  private SecurityUtils<AppUser> securityUtils;
  
  
  @Override
  public GroupExpertiseRelation joinRelation(GroupExpertiseRelation input, String userId) {
    
    if (CollectionUtils.isNotEmpty(input.getOtherMentors()))
      input.getOtherMentors().add(userId);
    else
      input.setOtherMentors(Arrays.asList(userId));
    
    return input;
  }

  @Override
  public void setRole(AppUser appUser, String authToken) {
    restTemplate.exchange(appServer.getUrlMentorUser(Endpoint.MENTOR_USER_SET_MENTOR),
        HttpMethod.POST, securityUtils.createRequestWithAuth(authToken, appUser), AppUser.class);
    
  }

}
