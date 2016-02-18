package com.company.eleave.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.company.eleave.employee.entity.Employee;
import com.company.eleave.service.EmployeeService;

@RestController
public class EmployeeController {

	@Autowired
	EmployeeService employeeService;

	@RequestMapping(value = "/employee", method = RequestMethod.GET)
	public ResponseEntity<List<Employee>> getAll() {
		return new ResponseEntity<List<Employee>>(employeeService.getAll(), HttpStatus.OK);
	}

	@RequestMapping(value = "/employee/{id}", method = RequestMethod.GET)
	public ResponseEntity<Employee> getById(@PathVariable("id") final Long employeeId) {
		final Employee result = employeeService.getById(employeeId);
		return result != null ? new ResponseEntity<Employee>(employeeService.getById(employeeId), HttpStatus.OK)
				: new ResponseEntity<Employee>(HttpStatus.NOT_FOUND);
	}

	@RequestMapping(value = "/employee/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Employee> update(final @PathVariable("id") Long employeeId, final Employee employee) {
		final Employee currentEmployee = employeeService.getById(employeeId);
		
		if(currentEmployee == null) {
			return new ResponseEntity<Employee>(HttpStatus.NOT_FOUND);
		}
		
		//TODO make some propagate methods 
		currentEmployee.setEmail(employee.getEmail());
		currentEmployee.setFirstName(employee.getFirstName());
		currentEmployee.setLastName(employee.getLastName());
		
		employeeService.update(currentEmployee);
		
		return new ResponseEntity<Employee>(currentEmployee, HttpStatus.OK);
	}

	@RequestMapping(value = "/employee/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Employee> delete(@PathVariable("id") final Long employeeId) {
		final Employee currentEmployee = employeeService.getById(employeeId);
		
		if(currentEmployee == null) {
			return new ResponseEntity<Employee>(HttpStatus.NOT_FOUND);
		}
		
		employeeService.delete(employeeId);
		return new ResponseEntity<Employee>(HttpStatus.OK);
	}
}
