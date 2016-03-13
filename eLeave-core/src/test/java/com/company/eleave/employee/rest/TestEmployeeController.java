package com.company.eleave.employee.rest;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

import com.company.eleave.employee.entity.Employee;

public class TestEmployeeController {

	@Test
	public void dummyTest() {
		Employee employee = new Employee();
		employee.setAnnualBalanceLeave(new ArrayList<>());
		employee.setEmail("mail");
		employee.setFirstName("john");
		employee.setLastName("doe");
		
		Assert.assertEquals("mail", employee.getEmail());
		Assert.assertEquals("john", employee.getFirstName());
		Assert.assertEquals("doe", employee.getLastName());
		Assert.assertTrue(employee.getAnnualBalanceLeave().size() == 0);
		
	}
}
