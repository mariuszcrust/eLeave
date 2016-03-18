/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.eleave.rest.mapper;

import com.company.eleave.leave.entity.TakenLeave;
import com.company.eleave.rest.dto.TakenLeaveDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class TakenLeaveMapper implements ModelMapping<TakenLeaveDTO, TakenLeave>{

    private final ModelMapper mapper = new ModelMapper();
    
    @Override
    public TakenLeaveDTO toDto(TakenLeave takenLeave) {
        TakenLeaveDTO takenLeaveDTO = mapper.map(takenLeave, TakenLeaveDTO.class);
        
        return takenLeaveDTO;
    }

    @Override
    public TakenLeave toEntity(TakenLeaveDTO takenLeaveDTO) {
        TakenLeave takenLeave = mapper.map(takenLeaveDTO, TakenLeave.class);
        
        return takenLeave;
    }
    
}
