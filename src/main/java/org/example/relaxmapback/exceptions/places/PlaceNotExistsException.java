package org.example.relaxmapback.exceptions.places;

public class PlaceNotExistsException extends RuntimeException {
  public PlaceNotExistsException(String message) {
    super(message);
  }
}
