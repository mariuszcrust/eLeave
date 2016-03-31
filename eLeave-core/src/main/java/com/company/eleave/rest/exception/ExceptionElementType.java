package com.company.eleave.rest.exception;

import com.company.eleave.employee.entity.Approver;
import com.company.eleave.employee.entity.Employee;
import com.company.eleave.leave.entity.AnnualBalanceLeave;
import com.company.eleave.leave.entity.LeaveStatus;
import com.company.eleave.leave.entity.LeaveType;
import com.company.eleave.leave.entity.TakenLeave;
import com.company.eleave.security.entity.Privilege;
import com.company.eleave.security.entity.User;
import com.company.eleave.security.entity.UserRole;

public enum ExceptionElementType {

    EMPLOYEE(Employee.class),
    APPROVER(Approver.class),
    ANNUAL_BALANCE_LEAVE(AnnualBalanceLeave.class),
    LEAVE_STATUS(LeaveStatus.class),
    LEAVE_TYPE(LeaveType.class),
    TAKEN_LEAVE(TakenLeave.class),
    PRIVILEGE(Privilege.class),
    USER(User.class),
    USER_ROLE(UserRole.class);

    private final Class clazz;

    ExceptionElementType(Class clazz) {
        this.clazz = clazz;
    }

    public String getName() {
        return this.clazz.getSimpleName();
    }
}
