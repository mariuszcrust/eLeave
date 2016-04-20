/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.eleave.security.repository;

import com.company.eleave.security.entity.UserRole;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author aga
 */
public interface RolesRepository  extends CrudRepository<UserRole, Long> {
    
}
