/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.eleave.leave.service;

import com.company.eleave.leave.entity.TakenLeave;
import com.company.eleave.leave.repository.TakenLeaveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author aga
 */

@Service
@Transactional
public class TakenLeaveService {
    
    @Autowired
    TakenLeaveRepository takenLeaveRepository;

    public TakenLeave getById(Long takenLeaveId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
