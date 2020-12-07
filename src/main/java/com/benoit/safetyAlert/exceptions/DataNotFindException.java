package com.benoit.safetyAlert.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class DataNotFindException extends RuntimeException {

  public DataNotFindException(String message) {
    super(message);
  }
}
