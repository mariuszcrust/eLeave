package com.company.eleave.employee.rest;

import com.company.eleave.rest.dto.AnnualBalanceLeaveDTO;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import com.company.eleave.rest.dto.EmployeeDTO;
import com.company.eleave.rest.dto.LeaveTypeDTO;
import com.company.eleave.rest.exception.RestResponseExceptionHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import org.junit.Assert;
import utils.RestURI;
import utils.TestObjectConverter;

public class ITEmployeeControllerRest extends IntegrationTest {

    @Autowired
    EmployeeController employeeController;

    private MockMvc mockMvc;

    private String contentAsString;

    @Before
    public void before() {
        mockMvc = standaloneSetup(employeeController).setControllerAdvice(new RestResponseExceptionHandler()).build();
    }

    @Test
    public void testGetAllEmployees() throws Exception {
        contentAsString = mockMvc.perform(get(RestURI.EMPLOYEES)).andExpect(status().isOk()).andReturn()
                .getResponse().getContentAsString();

        List<EmployeeDTO> result = new ObjectMapper().readValue(contentAsString, List.class);
        Assert.assertTrue("Wrong size of all employees" + result.size(), result.size() == 10);
    }

    @Test
    public void testGetEmployeeByIdWhenExists() throws Exception {
        final long employeeId = 1;
        contentAsString = mockMvc.perform(get(RestURI.request(RestURI.GET_EMPLOYEE_BY_ID, employeeId))).andExpect(status().isOk()).andReturn()
                .getResponse().getContentAsString();
        EmployeeDTO result = new ObjectMapper().readValue(contentAsString, EmployeeDTO.class);

        Assert.assertNotNull(result);
        Assert.assertTrue("wrong first name", "john".equals(result.getFirstName()));
        Assert.assertTrue("wrong last name", "doe1".equals(result.getLastName()));
        Assert.assertTrue("wrong email", "doe1@mail.com".equals(result.getEmail()));
    }

    @Test
    public void testGetEmployeeByIdWhenNotExists() throws Exception {
        final long employeeId = 123;
        contentAsString = mockMvc.perform(get(RestURI.request(RestURI.GET_EMPLOYEE_BY_ID, employeeId))).andExpect(status().isNotFound()).andReturn().getResponse().getContentAsString();

        Assert.assertTrue("Element with id: 123 of type: Employee has not been found".equals(contentAsString));
    }

    //@Test
    public void testCreateEmployee() throws Exception {
        final EmployeeDTO newEmployee = new EmployeeDTO();
        newEmployee.setFirstName("jan");
        newEmployee.setLastName("kowalski");
        newEmployee.setEmail("kowalski@gmail.com");

        final LeaveTypeDTO leaveType = new LeaveTypeDTO();
        leaveType.setComment("normal holidays");
        leaveType.setDefaultDaysAllowed(26);
        leaveType.setLeaveTypeName("NormalHolidays");

        final AnnualBalanceLeaveDTO normalHolidays = new AnnualBalanceLeaveDTO();
        normalHolidays.setLeaveDaysAllowed(20);
        normalHolidays.setLeaveDaysRemaining(15);
        normalHolidays.setLeaveType(leaveType);

        final List<AnnualBalanceLeaveDTO> leaves = Arrays.asList(normalHolidays);

        newEmployee.setAnnualBalanceLeave(leaves);

        contentAsString = mockMvc.perform(post(RestURI.EMPLOYEES).contentType(TestObjectConverter.APPLICATION_JSON_UTF8).content(TestObjectConverter.convertObjectToJsonBytes(newEmployee))).andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();
    }

}
