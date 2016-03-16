package com.company.eleave.leave.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.eleave.leave.entity.AnnualBalanceLeave;
import com.company.eleave.leave.repository.AnnualBalanceLeaveRepository;
import com.company.eleave.rest.dto.AnnualBalanceLeaveDTO;
import com.google.common.collect.Lists;

@Service
@Transactional
public class AnnualBalanceService {

    @Autowired
    AnnualBalanceLeaveRepository annualBalanceLeaveRepository;

    public List<AnnualBalanceLeave> getAllLeaves() {
        return Lists.newArrayList(annualBalanceLeaveRepository.findAll());
    }
    
    public List<AnnualBalanceLeave> getLeavesForUser(long employeeId) {
        return annualBalanceLeaveRepository.findByEmployee(employeeId);
    }

    public long createLeave(AnnualBalanceLeave annualBalanceLeave) {
        final AnnualBalanceLeave created = annualBalanceLeaveRepository.save(annualBalanceLeave);
        return created.getId();

    }

}
