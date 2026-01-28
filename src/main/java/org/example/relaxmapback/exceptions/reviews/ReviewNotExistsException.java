package org.example.relaxmapback.exceptions.reviews;

public class ReviewNotExistsException extends RuntimeException {
  public ReviewNotExistsException(String message) {
    super(message);
  }
}
