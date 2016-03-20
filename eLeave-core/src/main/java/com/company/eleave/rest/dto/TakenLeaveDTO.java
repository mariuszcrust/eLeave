/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.eleave.rest.dto;

import java.util.Date;

/**
 *
 * @author aga
 */
public class TakenLeaveDTO {

    private long id;
    private int leaveDaysTaken;
    private Date leaveFrom;
    private Date leaveTo;
    
    private LeaveStatusDTO status;
    
    private long annualBalanceLeaveId;
    private String leaveType;
    
    private long approverId;
    private String approverName;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getLeaveDaysTaken() {
        return leaveDaysTaken;
    }

    public void setLeaveDaysTaken(int leaveDaysTaken) {
        this.leaveDaysTaken = leaveDaysTaken;
    }

    public Date getLeaveFrom() {
        return leaveFrom;
    }

    public void setLeaveFrom(Date leaveFrom) {
        this.leaveFrom = leaveFrom;
    }

    public Date getLeaveTo() {
        return leaveTo;
    }

    public void setLeaveTo(Date leaveTo) {
        this.leaveTo = leaveTo;
    }

    public long getApproverId() {
        return approverId;
    }

    public void setApproverId(long approverId) {
        this.approverId = approverId;
    }

    public String getApproverName() {
        return approverName;
    }

    public void setApproverName(String approverName) {
        this.approverName = approverName;
    }

    public String getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(String leaveType) {
        this.leaveType = leaveType;
    }
  
    public LeaveStatusDTO getStatus() {
        return status;
    }
    
    public void setStatus(LeaveStatusDTO status) {
        this.status = status;
    }
    
    public long annualBalanceLeaveId() {
        return annualBalanceLeaveId;
    }
    
    public void setAnnualBalanceLeaveId(long annualBalanceLeaveId) {
        this.annualBalanceLeaveId = annualBalanceLeaveId;
    }
    
}
