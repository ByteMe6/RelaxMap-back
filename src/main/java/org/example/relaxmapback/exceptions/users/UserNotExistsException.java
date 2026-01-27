package org.example.relaxmapback.exceptions.users;

public class UserNotExistsException extends RuntimeException {
  public UserNotExistsException(String message) {
    super(message);
  }
}
