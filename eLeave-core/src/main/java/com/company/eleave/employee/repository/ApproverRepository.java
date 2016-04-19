package com.company.eleave.employee.repository;

import org.springframework.data.repository.CrudRepository;

import com.company.eleave.employee.entity.Approver;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ApproverRepository extends CrudRepository<Approver, Long> {
    
    @Query("SELECT a FROM Approver a WHERE a.approver.id = :approverId WHERE a.startDate < current_timestamp() AND a.endDate > current_timestamp()")
    List<Approver> getEmployeesAssignedToApprover(final @Param("approverId") long approverId);
    
    @Query("SELECT a FROM Approver a WHERE a.employee.id = :employeeId WHERE a.startDate < current_timestamp() AND a.endDate > current_timestamp()")
    Approver getApproverForEmployee(final @Param("employeeId") long employeeId);
}
