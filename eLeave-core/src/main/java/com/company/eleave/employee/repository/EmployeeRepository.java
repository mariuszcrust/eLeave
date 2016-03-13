package com.company.eleave.employee.repository;

import com.company.eleave.employee.entity.Employee;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Sebastian Szlachetka
 */
public interface EmployeeRepository extends CrudRepository<Employee, Long>{
    
  @Query("SELECT e FROM Employee e")
  List<Employee> findAllEmployeesWithoutUser();
}
