package org.example.relaxmapback.exceptions.files;

public class TooLargeFileException extends RuntimeException {
  public TooLargeFileException(String message) {
    super(message);
  }
}
