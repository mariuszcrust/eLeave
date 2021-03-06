package com.company.eleave.employee.rest;

import com.company.eleave.rest.dto.AnnualBalanceLeaveDTO;
import com.company.eleave.rest.dto.ApproverDTO;
import com.company.eleave.rest.dto.EmployeeAccountDTO;
import com.company.eleave.rest.dto.EmployeeDTO;
import com.company.eleave.rest.dto.LeaveTypeDTO;
import com.company.eleave.rest.exception.BadParameterException;
import com.company.eleave.rest.exception.ElementNotFoundException;
import com.company.eleave.rest.exception.ErrorCode;
import com.company.eleave.rest.exception.RestResponseExceptionHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import utils.IntegrationTest;
import utils.RestURI;
import utils.TestObjectConverter;

public class ITEmployeeControllerRest extends IntegrationTest {

    @Autowired
    EmployeeController employeeController;

    private MockMvc mockMvc;

    @Before
    public void before() {
        mockMvc = standaloneSetup(employeeController).setControllerAdvice(new RestResponseExceptionHandler()).build();
    }

    @Test
    public void testGetAllEmployees() throws Exception {
        final String contentAsString = mockMvc.perform(get(RestURI.EMPLOYEES))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse().getContentAsString();

        List<EmployeeDTO> result = new ObjectMapper().readValue(contentAsString, List.class);
        Assert.assertEquals("Wrong size of all employees", 10, result.size());
    }

    @Test
    public void testGetEmployeeByIdWhenExists() throws Exception {
        final long employeeId = 1;
        final String contentAsString = mockMvc.perform(get(request(RestURI.EMPLOYEE_BY_ID, employeeId)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse().getContentAsString();
        EmployeeDTO result = new ObjectMapper().readValue(contentAsString, EmployeeDTO.class);

        Assert.assertNotNull(result);
        Assert.assertEquals("wrong first name", "john", result.getFirstName());
        Assert.assertEquals("wrong last name", "doe1", result.getLastName());
        Assert.assertEquals("wrong email", "doe1@mail.com", result.getEmail());
    }

    @Test
    public void testGetEmployeeByIdWhenNotExists() throws Exception {
        final long employeeId = 123;
        final String contentAsString = mockMvc.perform(get(request(RestURI.EMPLOYEE_BY_ID, employeeId)))
                .andExpect(status().isNotFound())
                .andReturn()
                .getResponse().getContentAsString();

        ElementNotFoundException exception = new ObjectMapper().readValue(contentAsString, ElementNotFoundException.class);

        Assert.assertEquals(employeeId, exception.getElementId());
        Assert.assertEquals(ErrorCode.EMPLOYEE_NOT_FOUND.getCode(), exception.getCode());
    }

    @Test
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
        normalHolidays.setValidityDate(new Date());

        final List<AnnualBalanceLeaveDTO> leaves = Arrays.asList(normalHolidays);
        newEmployee.setAnnualBalanceLeaves(leaves);

        mockMvc.perform(post(RestURI.EMPLOYEES).contentType(TestObjectConverter.APPLICATION_JSON_UTF8).content(TestObjectConverter.convertObjectToJsonBytes(newEmployee)))
                .andExpect(status().isCreated())
                .andExpect(header().string("location", "/employees/11"));

        //check if size of all employees has increased by one
        final String contentAsString = mockMvc.perform(get(RestURI.EMPLOYEES))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse().getContentAsString();

        List<EmployeeDTO> result = new ObjectMapper().readValue(contentAsString, List.class);
        Assert.assertEquals(11, result.size());
    }

    @Test
    public void testUpdateEmployeeWhenExists() throws Exception {
        final long employeeId = 1;
        final EmployeeDTO updateEmployee = new EmployeeDTO();
        updateEmployee.setEmail("newDoe1@mail.com");
        updateEmployee.setFirstName("newJohn");
        updateEmployee.setLastName("newDoe1");

        final String contentAsString = mockMvc.perform(put(request(RestURI.EMPLOYEE_BY_ID, employeeId)).contentType(TestObjectConverter.APPLICATION_JSON_UTF8).content(TestObjectConverter.convertObjectToJsonBytes(updateEmployee)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse().getContentAsString();

        EmployeeDTO result = new ObjectMapper().readValue(contentAsString, EmployeeDTO.class);

        Assert.assertNotNull(result);
        Assert.assertTrue("wrong first name", "newJohn".equals(result.getFirstName()));
        Assert.assertTrue("wrong last name", "newDoe1".equals(result.getLastName()));
        Assert.assertTrue("wrong email", "newDoe1@mail.com".equals(result.getEmail()));

    }

    @Test
    public void testUpdateEmployeeWhenNotExists() throws Exception {
        final long employeeId = 123;
        final EmployeeDTO updateEmployee = new EmployeeDTO();
        updateEmployee.setEmail("newDoe1@mail.com");
        updateEmployee.setFirstName("newJohn");
        updateEmployee.setLastName("newDoe1");

        final String contentAsString = mockMvc.perform(put(request(RestURI.EMPLOYEE_BY_ID, employeeId)).contentType(TestObjectConverter.APPLICATION_JSON_UTF8).content(TestObjectConverter.convertObjectToJsonBytes(updateEmployee)))
                .andExpect(status().isNotFound())
                .andReturn()
                .getResponse().getContentAsString();

        ElementNotFoundException exception = new ObjectMapper().readValue(contentAsString, ElementNotFoundException.class);
        Assert.assertEquals(employeeId, exception.getElementId());
        Assert.assertEquals(ErrorCode.EMPLOYEE_NOT_FOUND.getCode(), exception.getCode());
    }

    @Test
    public void testAssignApproverWhenEmployeeNotExists() throws Exception {
        final long employeeId = 123;
        final long approverId = 5;

        final ApproverDTO approverDTO = new ApproverDTO();
        approverDTO.setApproverId(approverId);
        approverDTO.setStartDate(new Date().toString());
        approverDTO.setEndDate(null);

        final String contentAsString = mockMvc.perform(put(request(RestURI.EMPLOYEE_ASSIGN_APPROVER, employeeId)).contentType(TestObjectConverter.APPLICATION_JSON_UTF8).content(TestObjectConverter.convertObjectToJsonBytes(approverDTO)))
                .andExpect(status().isNotFound())
                .andReturn()
                .getResponse().getContentAsString();

        ElementNotFoundException exception = new ObjectMapper().readValue(contentAsString, ElementNotFoundException.class);
        Assert.assertEquals(employeeId, exception.getElementId());
        Assert.assertEquals(ErrorCode.EMPLOYEE_NOT_FOUND.getCode(), exception.getCode());
    }

    @Test
    public void testAssignApproverWhenApproverNotExists() throws Exception {
        final long employeeId = 1;
        final long approverId = 123;

        final ApproverDTO approverDTO = new ApproverDTO();
        approverDTO.setApproverId(approverId);
        approverDTO.setStartDate(new Date().toString());
        approverDTO.setEndDate(null);

        final String contentAsString = mockMvc.perform(put(request(RestURI.EMPLOYEE_ASSIGN_APPROVER, employeeId)).contentType(TestObjectConverter.APPLICATION_JSON_UTF8).content(TestObjectConverter.convertObjectToJsonBytes(approverDTO)))
                .andExpect(status().isNotFound())
                .andReturn()
                .getResponse().getContentAsString();

        ElementNotFoundException exception = new ObjectMapper().readValue(contentAsString, ElementNotFoundException.class);
        Assert.assertEquals(approverId, exception.getElementId());
        Assert.assertEquals(ErrorCode.EMPLOYEE_NOT_FOUND.getCode(), exception.getCode());
    }

    @Test
    public void testAssignApproverWhenStartDateHasWrongValue() throws Exception {
        final long employeeId = 1;
        final long approverId = 5;

        final ApproverDTO approverDTO = new ApproverDTO();
        approverDTO.setApproverId(approverId);
        approverDTO.setStartDate("1-April-2016");
        approverDTO.setEndDate(null);

        final String contentAsString = mockMvc.perform(put(request(RestURI.EMPLOYEE_ASSIGN_APPROVER, employeeId)).contentType(TestObjectConverter.APPLICATION_JSON_UTF8).content(TestObjectConverter.convertObjectToJsonBytes(approverDTO)))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse().getContentAsString();

        BadParameterException exception = new ObjectMapper().readValue(contentAsString, BadParameterException.class);
        Assert.assertEquals("1-April-2016", exception.getValue());
        Assert.assertEquals(ErrorCode.START_END_DATE_FOR_APPROVER_INVALID.getCode(), exception.getCode());
        Assert.assertEquals("Element with value: 1-April-2016 is invalid. ", exception.getMessage());
    }

    @Test
    public void testAssignApproverSuccessfuly() throws Exception {
        final long employeeId = 1;
        final long approverId = 5;

        final ApproverDTO approverDTO = new ApproverDTO();
        approverDTO.setApproverId(approverId);
        approverDTO.setStartDate("2016-01-01");
        approverDTO.setEndDate(null);

        final String contentAsString = mockMvc.perform(put(request(RestURI.EMPLOYEE_ASSIGN_APPROVER, employeeId)).contentType(TestObjectConverter.APPLICATION_JSON_UTF8).content(TestObjectConverter.convertObjectToJsonBytes(approverDTO)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse().getContentAsString();
    }

    @Test
    public void testReassignApproverWhenEmployeeNotExists() throws Exception {
        final long employeeId = 123;
        final long approverId = 5;

        final ApproverDTO approverDTO = new ApproverDTO();
        approverDTO.setApproverId(approverId);
        approverDTO.setStartDate(new Date().toString());
        approverDTO.setEndDate(null);

        final String contentAsString = mockMvc.perform(delete(request(RestURI.EMPLOYEE_REASSIGN_APPROVER, employeeId, approverId)))
                .andExpect(status().isNotFound())
                .andReturn()
                .getResponse().getContentAsString();

        ElementNotFoundException exception = new ObjectMapper().readValue(contentAsString, ElementNotFoundException.class);
        Assert.assertEquals(employeeId, exception.getElementId());
        Assert.assertEquals(ErrorCode.EMPLOYEE_NOT_FOUND.getCode(), exception.getCode());
    }

    @Test
    public void testReassignApproverWhenApproverNotExists() throws Exception {
        final long employeeId = 1;
        final long approverId = 123;

        final ApproverDTO approverDTO = new ApproverDTO();
        approverDTO.setApproverId(approverId);
        approverDTO.setStartDate(new Date().toString());
        approverDTO.setEndDate(null);

        final String contentAsString = mockMvc.perform(delete(request(RestURI.EMPLOYEE_REASSIGN_APPROVER, employeeId, approverId)))
                .andExpect(status().isNotFound())
                .andReturn()
                .getResponse().getContentAsString();

        ElementNotFoundException exception = new ObjectMapper().readValue(contentAsString, ElementNotFoundException.class);
        Assert.assertEquals(approverId, exception.getElementId());
        Assert.assertEquals(ErrorCode.EMPLOYEE_NOT_FOUND.getCode(), exception.getCode());
    }

    @Test
    public void testDeleteEmployeeWhenNotExists() throws Exception {
        final long employeeId = 123;

        final String contentAsString = mockMvc.perform(delete(request(RestURI.EMPLOYEE_BY_ID, employeeId)))
                .andExpect(status().isNotFound())
                .andReturn()
                .getResponse().getContentAsString();

        ElementNotFoundException exception = new ObjectMapper().readValue(contentAsString, ElementNotFoundException.class);
        Assert.assertEquals(employeeId, exception.getElementId());
        Assert.assertEquals(ErrorCode.EMPLOYEE_NOT_FOUND.getCode(), exception.getCode());
    }

    @Test
    public void testDeleteEmployeeSuccessfully() throws Exception {
        final long employeeId = 1;

        mockMvc.perform(delete(request(RestURI.EMPLOYEE_BY_ID, employeeId)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse().getContentAsString();

        final String contentAsString = mockMvc.perform(get(request(RestURI.EMPLOYEES_ACCOUNT_BY_ID, employeeId)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse().getContentAsString();

        EmployeeAccountDTO result = new ObjectMapper().readValue(contentAsString, EmployeeAccountDTO.class);
        Assert.assertFalse(result.getUser().isActive());
    }
}
