/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.eleave.rest.exception;

/**
 *
 * @author aga
 */
public enum ErrorCode {
    
    // Error Code for not found elements
    EMPLOYEE_NOT_FOUND("404_001"),
    APPROVER_NOT_FOUND("404_002"),
    ANNUAL_BALANCE_LEAVE_NOT_FOUND("404_003"),
    LEAVE_STATUS_NOT_FOUND("404_004"),
    LEAVE_TYPE_NOT_FOUND("404_005"),
    TAKEN_LEAVE_NOT_FOUND("404_006"),
    PRIVILEGE_NOT_FOUND("404_007"),
    USER_NOT_FOUND("404_008"),
    USER_ROLE_NOT_FOUND("404_009"),

    //Error Code for bad parameters
    START_END_DATE_FOR_APPROVER_INVALID("400_001"),
    ANNUAL_BALANCE_LEAVE_TYPE_STATUS("400_002"),
    LEAVE_TYPE_CANNOT_BE_REMOVED_DB_CONSTRAINTS("400_003");
    
    private final String code;
    
    ErrorCode(final String code) {
        this.code = code;
    }
    
    public String getCode() {
        return code;
    }
}
