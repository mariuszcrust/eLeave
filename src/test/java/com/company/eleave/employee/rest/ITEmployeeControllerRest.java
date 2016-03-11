package com.company.eleave.employee.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.util.List;

import javax.swing.text.AbstractDocument.Content;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.ContentResultMatchers;

import com.company.eleave.rest.dto.EmployeeDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ITEmployeeControllerRest extends IntegrationTest {

  @Autowired
  EmployeeController employeeController;

  private MockMvc mockMvc;

  private String contentAsString;

  @Before
  public void before() {
    mockMvc = standaloneSetup(employeeController).build();
  }

  @Test
  public void testGetAllEmployees() throws Exception {
    contentAsString = mockMvc.perform(get("/employees")).andExpect(status().isOk()).andReturn()
        .getResponse().getContentAsString();
    List<EmployeeDTO> result = new ObjectMapper().readValue(contentAsString, List.class);



    // System.out.println(contentAsString);
    // ResponseEntity<List<EmployeeDTO>> response = employeeController.getAll();
    // Assert.assertTrue(response.getBody().size() == 10);
  }

}
