package com.company.eleave.leave.entity;

import com.company.eleave.BaseEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author Sebastian Szlachetka
 */
@Entity
@Table(name = "leave_type")
public class LeaveType extends BaseEntity {

  private static final long serialVersionUID = 1L;

  @Column(name = "leave_type_name")
  private String leaveTypeName;

  @Column(name = "default_days_allowed")
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

  @Override
  public String toString() {
    return "LeaveType{" + "leaveTypeName=" + leaveTypeName + ", defaultDaysAllowed=" + defaultDaysAllowed + ", comment=" + comment + '}';
  }
}
