package com.company.eleave.employee.repository;

import com.company.eleave.employee.entity.Employee;
import com.company.eleave.security.entity.UserRole;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Sebastian Szlachetka
 */

public interface EmployeeRepository extends CrudRepository<Employee, Long>{
    
  @Query("SELECT e FROM Employee e INNER JOIN e.user u")
  List<Employee> findAllActive();

  @Query("SELECT e FROM Employee e JOIN FETCH e.user u WHERE e.id = :employeeId")
  public Employee findByIdWithAccount(final @Param("employeeId") Long employeeId);
  
  @Query("SELECT e FROM Employee e JOIN FETCH e.user u JOIN FETCH u.userRoles ur WHERE ur.roleName = :role")
  List<Employee> getByRole(final @Param("role") UserRole.RoleName role);
  
}


