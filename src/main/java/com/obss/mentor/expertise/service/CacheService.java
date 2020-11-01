package com.obss.mentor.expertise.service;

import java.util.List;
import java.util.stream.StreamSupport;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import com.google.common.collect.Lists;
import com.obss.mentor.expertise.model.GroupExpertiseRelation;
import com.obss.mentor.expertise.repository.GroupExpertiseRelationRepository;
import lombok.extern.slf4j.Slf4j;

/**
 * Cache related operations.
 * 
 * @author Goktug Selcuk
 *
 */
@Service
public class CacheService {

  @Autowired
  private GroupExpertiseRelationRepository groupExpertiseRelationRepository;
  @Value("${admin.authtoken}")
  private String adminAuthToken;
  @Autowired
  private UserService userService;


  /**
   * Load user id cache on startup.
   */
  @EventListener(ApplicationReadyEvent.class)
  public void loadCacheOnStartup() {
    StreamSupport.stream(groupExpertiseRelationRepository.findAll().spliterator(), false).parallel()
        .forEach(record -> callFindUserIdForAllusers(record));
  }

  /**
   * Call find user name from USER microservice.
   * 
   * @param record
   */
  private void callFindUserIdForAllusers(GroupExpertiseRelation record) {
    List<String> allUsers = Lists.newArrayList();
    allUsers.add(record.getMentorGroupId());

    if (CollectionUtils.isNotEmpty(record.getOtherMentors()))
      allUsers.addAll(record.getOtherMentors());
    if (CollectionUtils.isNotEmpty(record.getMentees()))
      allUsers.addAll(record.getMentees());

    allUsers.parallelStream().forEach(user -> userService.findUserNameFromId(user, adminAuthToken));
  }
}
