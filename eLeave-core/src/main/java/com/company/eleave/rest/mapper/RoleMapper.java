/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.eleave.rest.mapper;

import com.company.eleave.rest.dto.UserRoleDTO;
import com.company.eleave.security.entity.UserRole;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 *
 * @author aga
 */

@Component
public class RoleMapper implements Mapper<UserRoleDTO, UserRole>{

    private ModelMapper mapper = new ModelMapper();
    
    @Override
    public UserRoleDTO toDto(UserRole userRole) {
        return mapper.map(userRole, UserRoleDTO.class);
    }

    @Override
    public UserRole toEntity(UserRoleDTO userRoleDTO) {
        return mapper.map(userRoleDTO, UserRole.class);
    }
    
}
