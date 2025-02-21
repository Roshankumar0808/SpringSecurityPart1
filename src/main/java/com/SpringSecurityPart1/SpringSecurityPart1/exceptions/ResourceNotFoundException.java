package com.SpringSecurityPart1.SpringSecurityPart1.exceptions;

public class ResourceNotFoundException extends RuntimeException {
  public ResourceNotFoundException(String message) {
    super(message);
  }
}
