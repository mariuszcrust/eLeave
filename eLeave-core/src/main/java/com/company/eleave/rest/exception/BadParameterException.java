package com.company.eleave.rest.exception;

public class BadParameterException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  private String value;

  private String type;

  private String element;

  public BadParameterException(final String value, String type, String element) {
    this.value = value;
    this.type = type;
    this.element = element;
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


}
