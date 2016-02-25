package com.company.eleave.employee.rest;

import java.sql.DriverManager;

import org.hsqldb.jdbcDriver;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.github.springtestdbunit.annotation.ExpectedDatabase;

@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
      DirtiesContextTestExecutionListener.class,
      TransactionalTestExecutionListener.class,
      DbUnitTestExecutionListener.class })
@ContextConfiguration("file:src/main/resources/test/context-test.xml")
//@DbUnitConfiguration(databaseConnection={"dataSource"})
@DatabaseSetup("toDoData.xml")
public class ITEmployeeControllerRest {
	
	  @Test
	  @ExpectedDatabase("toDoData.xml")
	  public void findAll() throws Exception {
	      Assert.assertTrue(true);
	  }
}
