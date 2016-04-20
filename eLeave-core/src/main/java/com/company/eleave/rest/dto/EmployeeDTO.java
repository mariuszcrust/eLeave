package com.company.eleave.rest.dto;

import java.util.List;

public class EmployeeDTO {

    private long id;
    private String firstName;
    private String lastName;
    private String email;

    private long approverId;
    
    private List<UserRoleDTO> roles;
    
    private List<AnnualBalanceLeaveDTO> annualBalanceLeaves;
    
    public long getId() {
        return id;
    }
    
    public void setId(long id) {
        this.id = id;
    }
    
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<AnnualBalanceLeaveDTO> getAnnualBalanceLeaves() {
        return annualBalanceLeaves;
    }

    public void setAnnualBalanceLeaves(List<AnnualBalanceLeaveDTO> annualBalanceLeave) {
        this.annualBalanceLeaves = annualBalanceLeave;
    }
    
    public long getApproverId() {
        return approverId;
    }

    public void setApproverId(long approverId) {
        this.approverId = approverId;
    }
    
    public List<UserRoleDTO> getRoles() {
        return roles;
    }

    public void setRoles(List<UserRoleDTO> roles) {
        this.roles = roles;
    }
    
    
}
