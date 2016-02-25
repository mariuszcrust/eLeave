package com.company.eleave.employee.rest;

import java.sql.DriverManager;
import java.util.List;

import org.hsqldb.jdbcDriver;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import com.company.eleave.employee.entity.Employee;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
      DirtiesContextTestExecutionListener.class,
      TransactionalTestExecutionListener.class,
      DbUnitTestExecutionListener.class })
@ContextConfiguration("file:src/main/resources/test/context-test.xml")
@DatabaseSetup("toDoData.xml")
public class ITEmployeeControllerRest {
	
    @Autowired
    EmployeeController employeeController;
  
	  @Test
	  public void findAll() throws Exception {
	    ResponseEntity<List<Employee>> response = employeeController.getAll();
	    Assert.assertTrue("size of list should be 1", response.getBody().size() == 1);
	  }
}
