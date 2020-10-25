package com.obss.mentor.expertise.service;

import java.util.stream.Collectors;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import com.obss.mentor.expertise.constant.AppServer;
import com.obss.mentor.expertise.constant.Endpoint;
import com.obss.mentor.expertise.constant.GroupName;
import com.obss.mentor.expertise.core.OperationFactory;
import com.obss.mentor.expertise.model.AppUser;
import com.obss.mentor.expertise.model.RelationResponse;
import com.obss.mentor.expertise.util.SecurityUtils;

@Component
public class UserService {

  @Autowired
  private RestTemplate restTemplate;
  @Autowired
  private AppServer appServer;
  @Autowired
  private SecurityUtils<AppUser> securityUtils;
  @Autowired
  private OperationFactory operationFactory;
  /**
   * 
   * @param id
   * @return
   */
  public String findUserNameFromId(String id, String authToken) {
    if (StringUtils.isEmpty(id))
      return StringUtils.EMPTY;
    ResponseEntity<AppUser> responseEntity = restTemplate.exchange(
        setUrlId(appServer.getUrlMentorUser(Endpoint.MENTOR_GET_USER_BY_ID), id), HttpMethod.GET,
        securityUtils.createRequestWithAuth(authToken, null), AppUser.class);

    return responseEntity.getBody() != null ? responseEntity.getBody().getUserName() : id;
  }

  /**
   * Set user for url.
   * 
   * @param urlMentorUser
   * @param id
   * @return
   */
  private String setUrlId(String urlMentorUser, String id) {
    return String.format(urlMentorUser, id);
  }

  /**
   * Call related service.
   * 
   * @param menteeGroupId
   */
  public void setUserRole(String mentorGroupId, String authToken,GroupName groupName) {
    AppUser appUser = new AppUser();
    appUser.setId(mentorGroupId);
    
    operationFactory.getOperation(groupName).setRole(appUser, authToken);

  }

  /**
   * Get and set user names for {@code RelationResponse}.
   * 
   * @param relationResponse
   * @param authToken
   * @return
   */
  public RelationResponse getUserNames(RelationResponse relationResponse, String authToken) {
    relationResponse.setMentorName(findUserNameFromId(relationResponse.getMentorName(), authToken));

    if (CollectionUtils.isNotEmpty(relationResponse.getOtherMentors()))
      relationResponse.setOtherMentors(relationResponse.getOtherMentors().stream()
          .map(mentor -> findUserNameFromId(mentor, authToken)).collect(Collectors.toList()));
    if (CollectionUtils.isNotEmpty(relationResponse.getOtherMentees()))
      relationResponse.setOtherMentees(relationResponse.getOtherMentees().stream()
          .map(mentee -> findUserNameFromId(mentee, authToken)).collect(Collectors.toList()));

    return relationResponse;
  }
}
