/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.eleave.security.service;

import com.company.eleave.security.entity.UserRole;
import com.company.eleave.security.repository.RolesRepository;
import com.google.common.collect.Lists;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author aga
 */

@Service
@Transactional
public class RolesService {
    
    @Autowired
    RolesRepository roleRepository;
    
    public List<UserRole> getAll() {
        return Lists.newArrayList(roleRepository.findAll());
    }
    
    
}
