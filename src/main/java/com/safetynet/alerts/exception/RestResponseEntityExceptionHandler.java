package com.safetynet.alerts.exception;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

  private static final Logger log = LoggerFactory.getLogger(RestResponseEntityExceptionHandler.class);

  /**
   *
   */
  @ExceptionHandler(FirestationNotFoundException.class)
  protected ResponseEntity<Object> handleFirestationNotFoundException(
      FirestationNotFoundException firestationNotFoundException, WebRequest request) {
    logError(firestationNotFoundException);
    return handleExceptionInternal(firestationNotFoundException, getErrorDetails(firestationNotFoundException, request),
        new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
  }

  /**
   *
   */
  @ExceptionHandler(PersonNotFoundException.class)
  protected ResponseEntity<Object> handlePersonNotFoundException(
      PersonNotFoundException personNotFoundException, WebRequest request) {
    logError(personNotFoundException);
    return handleExceptionInternal(personNotFoundException, getErrorDetails(personNotFoundException, request),
        new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
  }

  @ExceptionHandler(ChildrenNotFoundException.class)
  protected ResponseEntity<Object> handleChildrenNotFoundException(
      ChildrenNotFoundException childrenNotFoundException, WebRequest request) {
    logError(childrenNotFoundException);
    return handleExceptionInternal(childrenNotFoundException, getErrorDetails(childrenNotFoundException, request),
        new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException methodArgumentNotValidException,
      HttpHeaders headers, HttpStatus status, WebRequest request) {
    return handleExceptionInternal(methodArgumentNotValidException, getErrorDetailsForMethodArgumentNotValid(
        methodArgumentNotValidException.getBindingResult().getFieldErrors().stream()
            .map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList()), request),
        new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
  }

  @NotNull
  @Override
  protected ResponseEntity<Object> handleMissingServletRequestParameter(
      @NotNull MissingServletRequestParameterException ex, @NotNull HttpHeaders headers, @NotNull HttpStatus status,
      @NotNull WebRequest request) {
    logError(ex);
    return handleExceptionInternal(ex, getErrorDetails(ex, request),
        new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
  }

  @NotNull
  @ExceptionHandler(NumberFormatException.class)
  protected ResponseEntity<Object> handleNumberFormatException(@NotNull NumberFormatException ex,
      @NotNull WebRequest request) {
    logError(ex);
    return handleExceptionInternal(ex, getErrorDetails(ex, request),
        new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
  }

  @Override
  protected ResponseEntity<Object> handleTypeMismatch(
      TypeMismatchException methodArgumentNotValidException,
      HttpHeaders headers, HttpStatus status, WebRequest request) {
    logError(methodArgumentNotValidException);
    return handleExceptionInternal(methodArgumentNotValidException,
        getErrorDetails(methodArgumentNotValidException, request),
        new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
  }

  @Override
  protected ResponseEntity<Object> handleHttpMessageNotReadable(
      HttpMessageNotReadableException httpMessageNotReadableException,
      HttpHeaders headers, HttpStatus status, WebRequest request) {
    logError(httpMessageNotReadableException);
    return handleExceptionInternal(httpMessageNotReadableException,
        getErrorDetails(httpMessageNotReadableException, request),
        new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
  }

  private ErrorDetails getErrorDetails(Exception exception, WebRequest request) {
    return new ErrorDetails(LocalDateTime.now(), exception.getMessage(), request.getDescription(false));
  }

  private ErrorDetails getErrorDetailsForMethodArgumentNotValid(List<String> message, WebRequest request) {
    return new ErrorDetails(LocalDateTime.now(), message.get(0), request.getDescription(false));
  }

  private void logError(Exception exception) {
    log.error(exception.getMessage(), exception);
  }

}
