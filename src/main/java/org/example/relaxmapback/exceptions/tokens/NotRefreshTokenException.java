package org.example.relaxmapback.exceptions.tokens;

public class NotRefreshTokenException extends RuntimeException {
  public NotRefreshTokenException(String message) {
    super(message);
  }
}
