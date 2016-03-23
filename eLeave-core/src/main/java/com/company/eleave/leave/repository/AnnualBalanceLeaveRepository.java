package com.company.eleave.leave.repository;

import com.company.eleave.leave.entity.AnnualBalanceLeave;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Sebastian Szlachetka
 */
public interface AnnualBalanceLeaveRepository extends CrudRepository<AnnualBalanceLeave, Long> {

  @Query("SELECT abl FROM AnnualBalanceLeave abl WHERE abl.employee.id = :employeeId")
  List<AnnualBalanceLeave> findByEmployee(final @Param("employeeId") long employeeId);
  
  @Query("SELECT abl FROM AnnualBalanceLeave abl WHERE abl.leaveType.id = :leaveTypeId")
  List<AnnualBalanceLeave> findByLeaveTypeId(final @Param("leaveTypeId") long leaveTypeId);
}
