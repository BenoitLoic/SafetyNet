package com.safetynet.safetyalerts.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Custom RuntimeException.
 * add response status 500 INTERNAL_SERVER_ERROR.
 */
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class DataRepositoryException extends RuntimeException {

  public DataRepositoryException(String message, Throwable cause) {
    super(message, cause);
  }
}
