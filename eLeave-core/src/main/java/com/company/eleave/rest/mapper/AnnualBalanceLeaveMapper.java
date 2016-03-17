package com.company.eleave.rest.mapper;

import com.company.eleave.leave.entity.AnnualBalanceLeave;
import com.company.eleave.leave.repository.LeaveTypeRepository;
import com.company.eleave.rest.dto.AnnualBalanceLeaveDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AnnualBalanceLeaveMapper implements ModelMapping<AnnualBalanceLeaveDTO, AnnualBalanceLeave> {

    private final ModelMapper mapper = new ModelMapper();
    
    @Autowired
    LeaveTypeRepository leaveTypeRepository;

    @Override
    public AnnualBalanceLeaveDTO toDto(AnnualBalanceLeave annualBalance) {
        AnnualBalanceLeaveDTO annualBalanceLeaveDTO = mapper.map(annualBalance, AnnualBalanceLeaveDTO.class);
        annualBalanceLeaveDTO.setLeaveTypeId(annualBalance.getLeaveType().getId());
        annualBalanceLeaveDTO.setLeaveTypeName(annualBalance.getLeaveType().getLeaveTypeName());
        return annualBalanceLeaveDTO;
    }

    @Override
    public AnnualBalanceLeave toEntity(AnnualBalanceLeaveDTO annualBalanceLeaveDTO) {
        AnnualBalanceLeave annualBalanceLeave = mapper.map(annualBalanceLeaveDTO, AnnualBalanceLeave.class);
        annualBalanceLeave.setLeaveType(leaveTypeRepository.findOne(annualBalanceLeaveDTO.getLeaveTypeId()));
        
        return annualBalanceLeave;
    }

}
