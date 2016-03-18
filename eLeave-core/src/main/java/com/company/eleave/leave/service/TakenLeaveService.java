/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.eleave.leave.service;

import com.company.eleave.leave.entity.LeaveStatus;
import com.company.eleave.leave.entity.TakenLeave;
import com.company.eleave.leave.repository.TakenLeaveRepository;
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
public class TakenLeaveService {
    
    @Autowired
    TakenLeaveRepository takenLeaveRepository;

    public TakenLeave getById(Long takenLeaveId) {
        return takenLeaveRepository.findOne(takenLeaveId);
    }

    public long create(final TakenLeave takenLeave) {
        TakenLeave newTakenLeave = takenLeaveRepository.save(takenLeave);
        return newTakenLeave.getId();
    }

    public void update(TakenLeave takenLeave) {
        takenLeaveRepository.save(takenLeave);
    }

    public void delete(TakenLeave takenLeave) {
        takenLeaveRepository.delete(takenLeave);
    }
    
    public List<TakenLeave> findTakenLeavesByEmployeeId(final long employeeId) {
        return takenLeaveRepository.findTakenLeavesForEmployeeId(employeeId);
    }
    
    public List<TakenLeave> findTakenLeavesByApproverId(final long approverId) {
        return takenLeaveRepository.findTakenLeavesForApproverId(approverId);
    }
    
}
