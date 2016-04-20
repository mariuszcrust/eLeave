/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.eleave.security.rest;

import com.company.eleave.rest.dto.UserRoleDTO;
import com.company.eleave.rest.mapper.RoleMapper;
import com.company.eleave.security.entity.UserRole;
import com.company.eleave.security.service.RolesService;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
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
    
    @Autowired
    RolesService rolesService;
    
    @Autowired
    RoleMapper mapper;

    @RequestMapping(value = "/roles", method = RequestMethod.GET)
    public ResponseEntity<List<UserRoleDTO>> getAll() {   
        List<UserRoleDTO> roles = rolesService.getAll().stream().map(role -> mapper.toDto(role)).collect(Collectors.toList());

        return new ResponseEntity<>(roles, HttpStatus.OK);
    }
}
