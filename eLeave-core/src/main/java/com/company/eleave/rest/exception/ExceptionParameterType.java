package com.company.eleave.rest.exception;

import java.util.Date;

public enum ExceptionParameterType {
  START_END_DATE_FOR_APPROVER(Date.class),
  ANNUAL_BALANCE_LEAVE_TYPE_STATUS(String.class);

  private final Class clazz;

  ExceptionParameterType(Class clazz) {
    this.clazz = clazz;
  }

  public String getName() {
    return this.clazz.getSimpleName();
  }

}
