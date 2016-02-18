package com.company.eleave.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.eleave.dao.EmployeeDao;
import com.company.eleave.employee.entity.Employee;

@Service("employeeService")
@Transactional
public class EmployeeService {

	@Autowired
	EmployeeDao employeeDao;
	
	public List<Employee> getAll() {
		return employeeDao.findAll();
	}

	public Employee getById(Long employeeId) {
		return employeeDao.getById(employeeId);
	}

	public void delete(Long employeeId) {
		// TODO Auto-generated method stub
		
	}

	public void update(Employee currentEmployee) {
		// TODO Auto-generated method stub
		
	}
}
