package com.company.eleave.leave.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.eleave.leave.entity.LeaveType;
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
}
