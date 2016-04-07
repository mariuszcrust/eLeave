/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.eleave.leave.repository;

import com.company.eleave.leave.entity.Holiday;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author aga
 */
public interface HolidayRepository extends CrudRepository<Holiday, Long> {
    
}
