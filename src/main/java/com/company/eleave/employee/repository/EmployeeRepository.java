package com.company.eleave.employee.repository;

import com.company.eleave.employee.entity.Employee;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Sebastian Szlachetka
 */
public interface EmployeeRepository extends CrudRepository<Employee, Long>{
    
}
