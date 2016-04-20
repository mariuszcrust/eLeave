/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.eleave.rest.dto;

/**
 *
 * @author mdaniel
 */
public class UserRoleDTO {

    private String name;
    private String labelName;
    
    public UserRoleDTO(String name, String labelName) {
        this.name = name;
        this.labelName = labelName;
    }

    public String getName() {
        return name;
    }

    public String getLabelName() {
        return labelName;
    }

}
