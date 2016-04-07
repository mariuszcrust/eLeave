/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.eleave.leave.service;

import com.company.eleave.leave.entity.Holiday;
import com.company.eleave.leave.repository.HolidayRepository;
import com.google.common.collect.Lists;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author mdaniel
 */
@Service
@Transactional
public class HolidayService {

    @Autowired
    private HolidayRepository holidayRepository;
    
    public Holiday getById(long holidayId) {
        return holidayRepository.findOne(holidayId);
    }

    public List<Holiday> getAll() {
        return Lists.newArrayList(holidayRepository.findAll());
    }

    public long createNew(Holiday holiday) {
        final Holiday newHoliday = holidayRepository.save(holiday);
        
        return newHoliday.getId();
    }

    public void update(Holiday holiday) {
        holidayRepository.save(holiday);
    }

    public void delete(Long holidayId) {
        holidayRepository.delete(holidayId);
    }

}
