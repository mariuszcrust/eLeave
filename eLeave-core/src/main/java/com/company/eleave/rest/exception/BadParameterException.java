package com.company.eleave.rest.exception;

public class BadParameterException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  private String value;

  private String type;

  private String element;
  
  private ExceptionType exceptionType;

  public BadParameterException(final String value, String type, String element, ExceptionType exceptionType) {
    this.value = value;
    this.type = type;
    this.element = element;
    this.exceptionType = exceptionType;
  }

  public String getValue() {
    return value;
  }

  public String getType() {
    return type;
  }

  public String getElement() {
    return element;
  }
  
  public ExceptionType getExceptionType() {
      return exceptionType;
  }
  
  public enum ExceptionType {
        BAD_VALUE, NOT_POSSIBLE_TO_REMOVE;
  }

}
