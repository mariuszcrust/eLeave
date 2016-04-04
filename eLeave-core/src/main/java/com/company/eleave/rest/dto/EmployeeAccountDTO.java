/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.eleave.rest.dto;

/**
 *
 * @author aga
 */
public class EmployeeAccountDTO {
    
    private EmployeeDTO employee;
    private UserDTO user;
    
    public EmployeeDTO getEmployee() {
        return employee;
    }
    
    public void setEmployee(EmployeeDTO employeeDTO) {
        this.employee = employeeDTO;
    }
    
    public UserDTO getUser() {
        return user;
    }
    
    public void setUserDTO(UserDTO userDTO) {
        this.user=  userDTO;
    }
    
    
}
