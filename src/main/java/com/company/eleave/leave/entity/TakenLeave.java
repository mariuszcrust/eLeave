package com.company.eleave.leave.entity;

import com.company.eleave.BaseEntity;
import com.company.eleave.employee.entity.Employee;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author Sebastian Szlachetka
 */
@Entity
@Table(name = "taken_leave")
public class TakenLeave extends BaseEntity{
    
    @Embedded
    private LeaveStatus leaveStatus;
    
    @OneToOne
    @JoinColumn(name = "approver_id")
    private Employee approver;
    
    @OneToOne
    @JoinColumn(name = "annual_balance_leave_id", nullable = false)
    private AnnualBalanceLeave annualBalanceLeave;
    
    @Column(name = "leave_days_taken")
    private int leaveDaysTaken;
    
    @Column(name = "leave_from")
    private Date leaveFrom;
    
    @Column(name = "leave_to")
    private Date leaveTo;

    public LeaveStatus getLeaveStatus() {
        return leaveStatus;
    }

    public void setLeaveStatus(LeaveStatus leaveStatus) {
        this.leaveStatus = leaveStatus;
    }

    public Employee getApprover() {
        return approver;
    }

    public void setApprover(Employee approver) {
        this.approver = approver;
    }

    public AnnualBalanceLeave getAnnualBalanceLeave() {
        return annualBalanceLeave;
    }

    public void setAnnualBalanceLeave(AnnualBalanceLeave annualBalanceLeave) {
        this.annualBalanceLeave = annualBalanceLeave;
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
}
