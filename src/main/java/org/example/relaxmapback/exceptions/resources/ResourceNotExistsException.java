package org.example.relaxmapback.exceptions.resources;

public class ResourceNotExistsException extends RuntimeException {
  public ResourceNotExistsException(String message) {
    super(message);
  }
}
