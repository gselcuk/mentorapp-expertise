package com.obss.mentor.expertise.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import com.obss.mentor.expertise.model.GroupSession;

/**
 * 
 * @author Goktug Selcuk
 *
 */
@Repository
public interface GroupSessionRepository extends ElasticsearchRepository<GroupSession, String> {

}
