package com.company.eleave.employee.rest.dto;


public class LeaveTypeDTO {

  private String leaveTypeName;

  private int defaultDaysAllowed;

  private String comment;

  public String getLeaveTypeName() {
    return leaveTypeName;
  }

  public void setLeaveTypeName(String leaveTypeName) {
    this.leaveTypeName = leaveTypeName;
  }

  public int getDefaultDaysAllowed() {
    return defaultDaysAllowed;
  }

  public void setDefaultDaysAllowed(int defaultDaysAllowed) {
    this.defaultDaysAllowed = defaultDaysAllowed;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }
}
