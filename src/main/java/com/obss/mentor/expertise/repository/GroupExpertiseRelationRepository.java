package com.obss.mentor.expertise.repository;

import org.springframework.stereotype.Repository;
import com.obss.mentor.expertise.model.GroupExpertiseRelation;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Elastic repository for {@code GroupExpertiseRelation}.
 * 
 * @author Goktug Selcuk
 *
 */
@Repository
public interface GroupExpertiseRelationRepository extends ElasticsearchRepository<GroupExpertiseRelation, String>{
  


}
