package com.company.eleave.rest.exception;

public class ElementNotFoundException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  private Long elementId;

  private String clazzType;

  public ElementNotFoundException(final Long id, final ExceptionElementType type) {
    this.elementId = id;
    this.clazzType = type.getName();
  }

  public Long getElementId() {
    return elementId;
  }

  public String getClazzType() {
    return clazzType;
  }


}
