/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.eleave.rest.mapper;

import com.company.eleave.leave.entity.Holiday;
import com.company.eleave.rest.dto.HolidayDTO;

/**
 *
 * @author mdaniel
 */
public class HolidayMapper implements Mapper<HolidayDTO, Holiday>{

    @Override
    public HolidayDTO toDto(Holiday e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Holiday toEntity(HolidayDTO d) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
