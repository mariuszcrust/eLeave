package com.company.eleave.leave.service;

import com.company.eleave.leave.entity.LeaveType;
import com.company.eleave.leave.repository.LeaveTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Sebastian Szlachetka
 */
@Service
@Transactional
public class LeaveTypeService {
    
    @Autowired
    private LeaveTypeRepository leaveTypeRepo;
    
    public Long createNewLeaveType(final LeaveType aLeaveType) {
        LeaveType leaveType = leaveTypeRepo.save(aLeaveType);
        return leaveType.getId();
    }
}
