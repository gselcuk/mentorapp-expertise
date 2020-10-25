package com.obss.mentor.expertise.serviceparam;

import java.util.List;
import lombok.Data;

/**
 * Search expertise request.
 * 
 * @author Goktug Selcuk
 *
 */
@Data
public class SearchExpertiseRequest {
  private List<String> expertiseNames;
  private List<String> keywords;
}
 