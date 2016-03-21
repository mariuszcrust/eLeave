/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.eleave.rest.mapper;

import com.company.eleave.leave.entity.TakenLeave;
import com.company.eleave.rest.dto.LeaveStatusDTO;
import com.company.eleave.rest.dto.TakenLeaveDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

@Component
public class TakenLeaveMapper implements Mapper<TakenLeaveDTO, TakenLeave>{

    private final ModelMapper mapper = new ModelMapper();
    
    @Override
    public TakenLeaveDTO toDto(TakenLeave takenLeave) {
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        TakenLeaveDTO takenLeaveDTO = mapper.map(takenLeave, TakenLeaveDTO.class);
        
        takenLeaveDTO.setApproverId(takenLeave.getApprover().getId());
        takenLeaveDTO.setApproverName(takenLeave.getApprover().getFirstName() + " " + takenLeave.getApprover().getLastName());
        
        takenLeaveDTO.setAnnualBalanceLeaveId(takenLeave.getAnnualBalanceLeave().getId());
        takenLeaveDTO.setLeaveType(takenLeave.getAnnualBalanceLeave().getLeaveType().getLeaveTypeName());
        
        LeaveStatusDTO leaveStatusDTO = new LeaveStatusDTO();
        leaveStatusDTO.setComment(takenLeave.getLeaveStatus().getComment());
        leaveStatusDTO.setStatus(takenLeave.getLeaveStatus().getStatusName().toString());
        
        takenLeaveDTO.setStatus(leaveStatusDTO);
        
        return takenLeaveDTO;
    }

    @Override
    public TakenLeave toEntity(TakenLeaveDTO takenLeaveDTO) {
        TakenLeave takenLeave = mapper.map(takenLeaveDTO, TakenLeave.class);
        
        return takenLeave;
    }
    
}
