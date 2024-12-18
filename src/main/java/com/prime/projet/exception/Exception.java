package com.prime.projet.exception;

import com.prime.projet.repository.entity.Destination;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class Exception extends RuntimeException {
  public Exception(String message) {
    super(message);
  }
  public Exception(String message, Throwable cause) {
    super(message, cause);
  }
  public UsernameNotFoundException getUsernameNotFoundException() {
    return (UsernameNotFoundException) getCause();
  }
  public Destination getDestination() {
    return null; 
  }
}
