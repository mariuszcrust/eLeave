package com.company.eleave.employee.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.eleave.employee.entity.Employee;
import com.company.eleave.employee.repository.EmployeeRepository;
import com.google.common.collect.Lists;

@Service("employeeService")
@Transactional
public class EmployeeService{

	@Autowired
	private EmployeeRepository employeeRepo;

	public List<Employee> getAll() {
		return Lists.newArrayList(employeeRepo.findAll());
	}

	public Employee getById(Long employeeId) {
		return employeeRepo.findOne(employeeId);
	}

	public void delete(Long employeeId) {
		employeeRepo.delete(employeeId);
	}

	public void update(Employee currentEmployee) {
		employeeRepo.save(currentEmployee);

	}

	public long create(Employee employee) {
		return employeeRepo.save(employee).getId();
	}
}
