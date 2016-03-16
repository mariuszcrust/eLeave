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
import org.springframework.data.repository.query.Param;

/**
 *
 * @author mdaniel
 */
public interface TakenLeaveRepository extends CrudRepository<TakenLeave, Long> {

    @Query("FROM TakenLeave tl WHERE tl.annualBalanceLeave.employee.id = :employeeId")
    public List<TakenLeave> findAllTakenLeavesByEmployeeId(@Param("employeeId") Long employeeId);
    
}
