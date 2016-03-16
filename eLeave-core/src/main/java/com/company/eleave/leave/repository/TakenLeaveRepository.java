/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.eleave.leave.repository;

import com.company.eleave.leave.entity.TakenLeave;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author mdaniel
 */
public interface TakenLeaveRepository extends CrudRepository<TakenLeave, Long> {
//WHERE abl.employee.Id = :employeeId
    @Query("SELECT tl FROM TakenLeave tl INNER JOIN tl.annualBalanceLeave abl")
    public List<TakenLeave> findAllTakenLeavesByEmployeeId(Long employeeId);
    
}
