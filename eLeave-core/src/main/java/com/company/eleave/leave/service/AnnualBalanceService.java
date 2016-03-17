package com.company.eleave.leave.service;

import com.company.eleave.leave.entity.AnnualBalanceLeave;
import com.company.eleave.leave.entity.LeaveType;
import com.company.eleave.leave.entity.TakenLeave;
import com.company.eleave.leave.repository.AnnualBalanceLeaveRepository;
import com.company.eleave.leave.repository.LeaveTypeRepository;
import com.company.eleave.leave.repository.TakenLeaveRepository;
import com.google.common.collect.Lists;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AnnualBalanceService {

    @Autowired
    AnnualBalanceLeaveRepository annualBalanceLeaveRepository;

    @Autowired
    LeaveTypeRepository leaveTypeRepository;
    
    @Autowired
    TakenLeaveRepository takenLeaveRepository;

    public List<AnnualBalanceLeave> getAllLeaves() {
        return Lists.newArrayList(annualBalanceLeaveRepository.findAll());
    }

    public List<AnnualBalanceLeave> getLeavesForUser(long employeeId) {
        return annualBalanceLeaveRepository.findByEmployee(employeeId);
    }

    public long createLeave(AnnualBalanceLeave annualBalanceLeave) {
        LeaveType leaveType = leaveTypeRepository.findOne(annualBalanceLeave.getLeaveType().getId());
        annualBalanceLeave.setLeaveType(leaveType);

        final AnnualBalanceLeave created = annualBalanceLeaveRepository.save(annualBalanceLeave);
        return created.getId();
    }

    public void deleteLeave(long leaveId) {
        AnnualBalanceLeave annualBalanceLeave = annualBalanceLeaveRepository.findOne(leaveId);
        List<TakenLeave> takenLeavesForAnnualBalance = takenLeaveRepository.findTakenLeavesByAnnualLeave(leaveId);
        takenLeaveRepository.delete(takenLeavesForAnnualBalance);
        annualBalanceLeaveRepository.delete(annualBalanceLeave);
    }

}
