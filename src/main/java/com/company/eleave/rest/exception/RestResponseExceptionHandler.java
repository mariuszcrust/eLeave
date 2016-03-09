package com.company.eleave.rest.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseExceptionHandler extends ResponseEntityExceptionHandler {

  private static final String MESSAGE_FORMAT_NOT_FOUND = "Element with id: %d of type: %s has not been found";

  private static final String MESSAGE_FORMAT_BAD_PARAMETER = "Element with value: %s of type: %s has invalid value. Root cause: %s";

  @ExceptionHandler(ElementNotFoundException.class)
  protected ResponseEntity<Object> notFoundException(ElementNotFoundException ex, WebRequest request) {
    return handleExceptionInternal(ex, String.format(MESSAGE_FORMAT_NOT_FOUND, ex.getElementId(), ex.getClazzType()), new HttpHeaders(), HttpStatus.CONFLICT, request);
  }

  @ExceptionHandler(BadParameterException.class)
  protected ResponseEntity<Object> badParameterException(BadParameterException ex, WebRequest request) {
    return handleExceptionInternal(ex, String.format(MESSAGE_FORMAT_BAD_PARAMETER, ex.getValue(), ex.getType(), ex.getElement(), ex.getMessage()), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
  }
}
