/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.eleave.security.rest;

import com.company.eleave.rest.dto.UserRoleDTO;
import com.company.eleave.security.entity.UserRole;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author mdaniel
 */
@RestController
@RequestMapping(value = "/users")
public class UserController {

    @RequestMapping(value = "/roles", method = RequestMethod.GET)
    public ResponseEntity<List<UserRoleDTO>> getAll() {   
        List<UserRoleDTO> roles = Arrays.asList(UserRole.RoleName.values()).stream().map(role -> userRoleToDto(role)).collect(Collectors.toList());

        return new ResponseEntity<>(roles, HttpStatus.OK);
    }
    
    private UserRoleDTO userRoleToDto(UserRole.RoleName role) {
        return new UserRoleDTO(role.name(), role.getLabelName());
    }
}
