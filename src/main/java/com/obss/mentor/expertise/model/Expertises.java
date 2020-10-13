package com.obss.mentor.expertise.model;

import java.util.List;
import lombok.Data;

/**
 * 
 * @author Goktug Selcuk
 *
 */
@Data
public class Expertises {
  private String category;
  private String expertiseName;
  private List<String> keywords;

}
