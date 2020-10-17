package com.obss.mentor.expertise.service;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import com.obss.mentor.expertise.model.Approvable;

/**
 * For approval needed object flow need to be executed under this class.
 * 
 * @author Goktug Selcuk
 *
 */
@Service
public class ApprovalService<T extends Approvable> {
  
  /**
   * If object needed to send to approval;send to approval otherwise save it to database.
   * 
   * @param input
   * @param crudRepository
   * @return
   */
  public T doOperation(T input, CrudRepository<T, String> crudRepository) {

    if (input.isApprovalNeeded())
      return sendToApproval(input);

    return crudRepository.save(input);

  }

  private T sendToApproval(T input) {// TODO:implent approval process.
    return input;
  }

}
