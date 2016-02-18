package com.company.eleave.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.company.eleave.employee.entity.Employee;

@Repository("employeeDao")
public class EmployeeDao extends AbstractDao<Long, Employee> {

    @SuppressWarnings("unchecked")
	public List<Employee> findAll() {
        Criteria criteria = getSession().createCriteria(Employee.class);
        return (List<Employee>) criteria.list();
    }

	public Employee getById(Long employeeId) {
		return getById(employeeId);
	}

	public void update(Employee currentEmployee) {
		update(currentEmployee);
	}
	
	public void delete(final Long employeeId) {
		delete(employeeId);
	}
}
