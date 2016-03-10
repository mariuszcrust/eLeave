package com.company.eleave.employee.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import com.company.eleave.rest.dto.EmployeeDTO;

public class ITEmployeeControllerRest extends IntegrationTest {

  @Autowired
  EmployeeController employeeController;

  private MockMvc mockMvc;

  @Before
  public void before() {
    mockMvc = standaloneSetup(employeeController).build();
  }

  @Test
  public void test_get_all() throws Exception {

    mockMvc.perform(get("/employees")).andExpect(status().isOk());

    ResponseEntity<List<EmployeeDTO>> response = employeeController.getAll();
    Assert.assertTrue(response.getBody().size() == 10);
  }
}
