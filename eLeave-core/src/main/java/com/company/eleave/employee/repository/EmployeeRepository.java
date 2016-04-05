package com.company.eleave.employee.repository;

import com.company.eleave.employee.entity.Employee;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Sebastian Szlachetka
 */

public interface EmployeeRepository extends CrudRepository<Employee, Long>{
    
  @Query("SELECT e FROM Employee e JOIN FETCH e.user u")
  List<Employee> findAllEmployeesWithAccount();

  @Query("SELECT e FROM Employee e JOIN FETCH e.user u WHERE e.id = :employeeId")
  public Employee findByIdWithAccount(final @Param("employeeId") Long employeeId);
  
}
