package com.benoit.safetyAlert.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Custom RuntimeException.
 * add response status 404 NOT_FOUND.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class EndPointNotFindException extends RuntimeException {

  public EndPointNotFindException(String message) {
    super(message);
  }
}
