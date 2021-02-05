package com.benoit.safetyAlert.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidArgumentException extends RuntimeException {

  public InvalidArgumentException(String message) {
    super(message);
  }

  public InvalidArgumentException(String message, Throwable cause) {
    super(message, cause);
  }
}
