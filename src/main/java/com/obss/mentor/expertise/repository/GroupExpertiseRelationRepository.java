package com.obss.mentor.expertise.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import com.obss.mentor.expertise.model.GroupExpertiseRelation;

/**
 * Elastic repository for {@code GroupExpertiseRelation}.
 * 
 * @author Goktug Selcuk
 *
 */
@Repository
public interface GroupExpertiseRelationRepository
    extends ElasticsearchRepository<GroupExpertiseRelation, String> {
  
  @Query("{\"bool\": {\"must\": [{\"match\": {\"expertiseAreas.expertiseName\": \"?0\"}}]}}")
  Page<GroupExpertiseRelation> findByExpertiseName(String expertiseName, Pageable pageable);

}
