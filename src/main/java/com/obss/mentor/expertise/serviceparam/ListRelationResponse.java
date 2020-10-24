package com.obss.mentor.expertise.serviceparam;

import java.util.List;
import com.obss.mentor.expertise.model.RelationResponse;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 
 * @author Goktug Selcuk
 *
 */
@Data
@AllArgsConstructor
public class ListRelationResponse {

  private List<RelationResponse> listRelation;
}
