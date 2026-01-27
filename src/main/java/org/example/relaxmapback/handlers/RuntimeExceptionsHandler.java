package org.example.relaxmapback.handlers;

import org.example.relaxmapback.common.ErrorResponse;
import org.example.relaxmapback.exceptions.files.EmptyFileException;
import org.example.relaxmapback.exceptions.files.TooLargeFileException;
import org.example.relaxmapback.exceptions.files.UnsupportedContentTypeException;
import org.example.relaxmapback.exceptions.resources.ResourceNotExistsException;
import org.example.relaxmapback.exceptions.tokens.InvalidRefreshTokenException;
import org.example.relaxmapback.exceptions.tokens.NotRefreshTokenException;
import org.example.relaxmapback.exceptions.users.InvalidCredentialsException;
import org.example.relaxmapback.exceptions.users.UserAlreadyExistsException;
import org.example.relaxmapback.exceptions.users.UserNotExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RuntimeExceptionsHandler {
  @ExceptionHandler(UserAlreadyExistsException.class)
  public ResponseEntity<ErrorResponse> handleUserAlreadyExists(UserAlreadyExistsException ex) {
    return ResponseEntity.status(HttpStatus.CONFLICT)
            .body(new ErrorResponse(ex.getMessage(), System.currentTimeMillis(), 409));
  }

  @ExceptionHandler(UserNotExistsException.class)
  public ResponseEntity<ErrorResponse> handleUserAlreadyExists(UserNotExistsException ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(new ErrorResponse(ex.getMessage(), System.currentTimeMillis(), 404));
  }

  @ExceptionHandler(InvalidCredentialsException.class)
  public ResponseEntity<ErrorResponse> handleInvalidCredentials(InvalidCredentialsException ex) {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(new ErrorResponse(ex.getMessage(), System.currentTimeMillis(), 401));
  }

  @ExceptionHandler(InvalidRefreshTokenException.class)
  public ResponseEntity<ErrorResponse> handleInvalidRefreshToken(InvalidRefreshTokenException ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(new ErrorResponse(ex.getMessage(), System.currentTimeMillis(), 400));
  }

  @ExceptionHandler(NotRefreshTokenException.class)
  public ResponseEntity<ErrorResponse> handleInvalidRefreshToken(NotRefreshTokenException ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(new ErrorResponse(ex.getMessage(), System.currentTimeMillis(), 400));
  }

  @ExceptionHandler(ResourceNotExistsException.class)
  public ResponseEntity<ErrorResponse> handleUserAlreadyExists(ResourceNotExistsException ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(new ErrorResponse(ex.getMessage(), System.currentTimeMillis(), 404));
  }

  @ExceptionHandler(EmptyFileException.class)
  public ResponseEntity<ErrorResponse> handleEmptyFile(EmptyFileException ex) {
    return ResponseEntity.badRequest()
            .body(new ErrorResponse(ex.getMessage(), System.currentTimeMillis(), 400));
  }

  @ExceptionHandler(TooLargeFileException.class)
  public ResponseEntity<ErrorResponse> handleTooLargeFile(TooLargeFileException ex) {
    return ResponseEntity.badRequest()
            .body(new ErrorResponse(ex.getMessage(), System.currentTimeMillis(), 400));
  }

  @ExceptionHandler(UnsupportedContentTypeException.class)
  public ResponseEntity<ErrorResponse> handleUnsupportedContentType(UnsupportedContentTypeException ex) {
    return ResponseEntity.badRequest()
            .body(new ErrorResponse(ex.getMessage(), System.currentTimeMillis(), 400));
  }

  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<ErrorResponse> handleRuntime(RuntimeException ex) {
    return ResponseEntity.badRequest()
            .body(new ErrorResponse(ex.getMessage(), System.currentTimeMillis(), 400));
  }
}
