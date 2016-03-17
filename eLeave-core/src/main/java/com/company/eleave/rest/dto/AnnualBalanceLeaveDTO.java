package com.company.eleave.rest.dto;

import java.util.Date;

public class AnnualBalanceLeaveDTO {

    private long id;
    private int leaveDaysRemaining;
    private int leaveDaysAllowed;
    private Date validityDate;
    private String leaveTypeName;
    private long leaveTypeId;
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
    
    public String getLeaveTypeName() {
        return leaveTypeName;
    }
    
    public void setLeaveTypeName(String leaveTypeName) {
        this.leaveTypeName = leaveTypeName;
    }
    
    public void setLeaveTypeId(long leaveTypeId) {
        this.leaveTypeId = leaveTypeId;
    }
    
    public long getLeaveTypeId() {
        return leaveTypeId;
    }
}
