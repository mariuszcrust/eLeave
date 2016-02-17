package com.company.eleave.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.company.eleave.employee.entity.Employee;
import com.company.eleave.service.EmployeeService;

@RestController
public class EmployeeController {

	@Autowired
	EmployeeService employeeService;
	
	@RequestMapping(value="/employee", method = RequestMethod.GET, produces="application/json")
	public ResponseEntity<List<Employee>> getAllEmployees() {
		return new ResponseEntity<List<Employee>>(employeeService.getAllEmployees(), HttpStatus.OK);
	}
}
