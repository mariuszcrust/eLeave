package com.company.eleave.leave.entity;

import com.company.eleave.BaseEntity;
import com.company.eleave.employee.entity.Employee;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author Sebastian Szlachetka
 */
@Entity
@Table(name = "annual_balance_leave")
public class AnnualBalanceLeave extends BaseEntity {

  @ManyToOne
  @JoinColumn(name = "employee_id", nullable = false)
  private Employee employee;

  @OneToOne
  @JoinColumn(name = "leave_type_id", nullable = false)
  private LeaveType leaveType;

  @Column(name = "leave_days_remaining")
  private int leaveDaysRemaining;

  @Column(name = "leave_days_allowed")
  private int leaveDaysAllowed;

  private int year;

  @Column(name = "validity_date")
  private Date validityDate;

  public Employee getEmployee() {
    return employee;
  }

  public void setEmployee(Employee employee) {
    this.employee = employee;
  }

  public LeaveType getLeaveType() {
    return leaveType;
  }

  public void setLeaveType(LeaveType leaveType) {
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

  public int getYear() {
    return year;
  }

  public void setYear(int year) {
    this.year = year;
  }

  public Date getValidityDate() {
    return validityDate;
  }

  public void setValidityDate(Date validityDate) {
    this.validityDate = validityDate;
  }



}
