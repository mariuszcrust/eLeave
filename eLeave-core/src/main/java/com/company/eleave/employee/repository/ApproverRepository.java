package com.company.eleave.employee.repository;

import org.springframework.data.repository.CrudRepository;

import com.company.eleave.employee.entity.Approver;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ApproverRepository extends CrudRepository<Approver, Long> {
    
    @Query("SELECT a FROM Approver a WHERE a.approver.id = :approverId AND a.startDate < :currentDate AND a.endDate > :currentDate")
    List<Approver> getEmployeesAssignedToApprover(final @Param("approverId") long approverId, final @Param("currentDate") Date currentDate);
    
    @Query("SELECT a FROM Approver a WHERE a.employee.id = :employeeId AND a.startDate < :currentDate AND a.endDate > :currentDate")
    Approver getApproverForEmployee(final @Param("employeeId") long employeeId, final @Param("currentDate") Date currentDate);
}
