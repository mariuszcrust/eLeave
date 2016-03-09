package com.company.eleave.rest.dto;

import java.util.Date;

public class AnnualBalanceLeaveDTO {

  private LeaveTypeDTO leaveType;
  private int leaveDaysRemaining;
  private int leaveDaysAllowed;
  private Date validityDate;

  public LeaveTypeDTO getLeaveType() {
    return leaveType;
  }

  public void setLeaveType(LeaveTypeDTO leaveType) {
    this.leaveType = leaveType;
  }

  public int getLeaveDaysRemaining() {
    return leaveDaysRemaining;
  }

  public void setLeaveDaysRemaining(int leaveDaysRemaining) {
    this.leaveDaysRemaining = leaveDaysRemaining;
  }

  public int getLeaveDaysAllowed() {
    return leaveDaysAllowed;
  }

  public void setLeaveDaysAllowed(int leaveDaysAllowed) {
    this.leaveDaysAllowed = leaveDaysAllowed;
  }

  public Date getValidityDate() {
    return validityDate;
  }

  public void setValidityDate(Date validityDate) {
    this.validityDate = validityDate;
  }
}
