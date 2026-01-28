package org.example.relaxmapback.exceptions.users;

public class IdOrEmailRequiredException extends RuntimeException {
  public IdOrEmailRequiredException(String message) {
    super(message);
  }
}
