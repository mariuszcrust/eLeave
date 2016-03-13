package com.company.eleave.rest.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.company.eleave.leave.entity.LeaveType;
import com.company.eleave.rest.dto.LeaveTypeDTO;

@Component
public class LeaveTypeMapper implements ModelMapping<LeaveTypeDTO, LeaveType>{

  private ModelMapper mapper = new ModelMapper();

  public LeaveTypeDTO toDto(LeaveType leaveType) {
    return mapper.map(leaveType, LeaveTypeDTO.class);
  }

  public LeaveType toEntity(LeaveTypeDTO leaveType) {
    return mapper.map(leaveType, LeaveType.class);
  }

}
