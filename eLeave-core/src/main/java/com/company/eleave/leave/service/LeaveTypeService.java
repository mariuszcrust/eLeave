package com.company.eleave.leave.service;

import com.company.eleave.leave.entity.AnnualBalanceLeave;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.eleave.leave.entity.LeaveType;
import com.company.eleave.leave.repository.AnnualBalanceLeaveRepository;
import com.company.eleave.leave.repository.LeaveTypeRepository;
import com.google.common.collect.Lists;

/**
 *
 * @author Sebastian Szlachetka
 */
@Service
@Transactional
public class LeaveTypeService {

    @Autowired
    private LeaveTypeRepository leaveTypeRepo;
    
    @Autowired
    private AnnualBalanceLeaveRepository annualBalanceLeaveTypeRepository;

    public Long createNewLeaveType(final LeaveType aLeaveType) {
        LeaveType leaveType = leaveTypeRepo.save(aLeaveType);
        return leaveType.getId();
    }

    public List<LeaveType> getAll() {
        return Lists.newArrayList(leaveTypeRepo.findAll());
    }

    public LeaveType getById(Long leaveTypeId) {
        return leaveTypeRepo.findOne(leaveTypeId);
    }

    public void delete(Long leaveTypeId) {
        leaveTypeRepo.delete(getById(leaveTypeId));
    }

    public void update(LeaveType leaveType) {
        leaveTypeRepo.save(leaveType);
    }

    public boolean isLeaveTypeAssignedToAnyLeave(Long leaveTypeId) {
        return annualBalanceLeaveTypeRepository.findByLeaveType(leaveTypeId).size() > 0;
    }
}
