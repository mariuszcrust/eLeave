/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.eleave.rest.mapper;

import com.company.eleave.leave.entity.LeaveStatus;
import com.company.eleave.rest.dto.LeaveStatusDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class LeaveStatusMapper implements Mapper<LeaveStatusDTO, LeaveStatus> {

    private ModelMapper mapper = new ModelMapper();
    
    @Override
    public LeaveStatusDTO toDto(LeaveStatus leaveStatus) {
        LeaveStatusDTO leaveStatusDTO = mapper.map(leaveStatus, LeaveStatusDTO.class);
                
        return leaveStatusDTO;
    }

    @Override
    public LeaveStatus toEntity(LeaveStatusDTO leaveStatusDTO) {
        LeaveStatus leaveStatus = mapper.map(leaveStatusDTO, LeaveStatus.class);
        
        return leaveStatus;
    }
    
}
