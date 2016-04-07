/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.eleave.rest.mapper;

import com.company.eleave.leave.entity.Holiday;
import com.company.eleave.rest.dto.HolidayDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 *
 * @author mdaniel
 */
@Component
public class HolidayMapper implements Mapper<HolidayDTO, Holiday>{

    private ModelMapper mapper = new ModelMapper();
    
    @Override
    public HolidayDTO toDto(Holiday holiday) {
        return mapper.map(holiday, HolidayDTO.class);
    }

    @Override
    public Holiday toEntity(HolidayDTO holidayDTO) {
        return mapper.map(holidayDTO, Holiday.class);
    }
    
}
