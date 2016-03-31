/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.eleave.leave.rest;

import utils.IntegrationTest;
import com.company.eleave.rest.dto.AnnualBalanceLeaveDTO;
import com.company.eleave.rest.exception.ElementNotFoundException;
import com.company.eleave.rest.exception.ErrorCode;
import com.company.eleave.rest.exception.RestResponseExceptionHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.sql.Date;
import java.util.LinkedHashMap;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import utils.RestURI;
import utils.TestObjectConverter;

/**
 *
 * @author mdaniel
 */
public class ITAnnualBalanceLeaveController extends IntegrationTest {

    @Autowired
    AnnualBalanceLeaveController annualBalanceLeaveController;

    private MockMvc mockMvc;

    private static final long EMPLOYEE_ID = 1l;

    private static final long EMPLOYEE_ID_NOT_EXISTING = 200l;

    @Before
    public void before() {
        mockMvc = standaloneSetup(annualBalanceLeaveController).setControllerAdvice(new RestResponseExceptionHandler()).build();
    }

    @Test
    public void testGetAllLeaves() throws Exception {
        final String contentAsString = mockMvc.perform(get(RestURI.ANNUAL_BALANCE_LEAVES))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse().getContentAsString();

        List<AnnualBalanceLeaveDTO> result = new ObjectMapper().readValue(contentAsString, List.class);
        Assert.assertTrue("Wrong size of all annual balance leaves ", result.size() == 5);
    }

    @Test
    public void testGetLeavesForEmployee() throws Exception {
        final String contentAsString = mockMvc.perform(get(request(RestURI.ANNUAL_BALANCE_LEAVES_BY_EMPLOYEE, EMPLOYEE_ID)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse().getContentAsString();

        List<LinkedHashMap> result = new ObjectMapper().readValue(contentAsString, List.class);
        Assert.assertEquals(3, result.size());

        final LinkedHashMap firstAnnualBalanceLeave = result.stream().filter(map -> ((int) map.get("id") == 1)).findAny().get();
        Assert.assertEquals(20, (int) firstAnnualBalanceLeave.get("leaveDaysAllowed"));
        Assert.assertEquals(15, (int) firstAnnualBalanceLeave.get("leaveDaysRemaining"));
        Assert.assertEquals(10, (int) firstAnnualBalanceLeave.get("leaveTypeId"));
        Assert.assertEquals("Standard holiday", firstAnnualBalanceLeave.get("leaveTypeName"));
        Assert.assertEquals("2016-12-12", firstAnnualBalanceLeave.get("validityDate"));
    }

    @Test
    public void testGetLeavesForEmployeeWhenEmployeeNotExists() throws Exception {
        final String contentAsString = mockMvc.perform(get(request(RestURI.ANNUAL_BALANCE_LEAVES_BY_EMPLOYEE, EMPLOYEE_ID_NOT_EXISTING)))
                .andExpect(status().isNotFound())
                .andReturn()
                .getResponse().getContentAsString();

        ElementNotFoundException exception = new ObjectMapper().readValue(contentAsString, ElementNotFoundException.class);
        Assert.assertEquals(EMPLOYEE_ID_NOT_EXISTING, exception.getElementId());
        Assert.assertEquals(ErrorCode.EMPLOYEE_NOT_FOUND.getCode(), exception.getCode());
    }

    @Test
    public void testAddLeaveForEmployeeWhenEmployeeNotExists() throws Exception {
        final AnnualBalanceLeaveDTO newAnnualBalanceLeaveDTO = new AnnualBalanceLeaveDTO();

        final String contentAsString = mockMvc.perform(post(request(RestURI.ANNUAL_BALANCE_LEAVES_BY_EMPLOYEE, EMPLOYEE_ID_NOT_EXISTING))
                .contentType(TestObjectConverter.APPLICATION_JSON_UTF8).content(TestObjectConverter.convertObjectToJsonBytes(newAnnualBalanceLeaveDTO)))
                .andExpect(status().isNotFound())
                .andReturn()
                .getResponse().getContentAsString();

        ElementNotFoundException exception = new ObjectMapper().readValue(contentAsString, ElementNotFoundException.class);
        Assert.assertEquals(EMPLOYEE_ID_NOT_EXISTING, exception.getElementId());
        Assert.assertEquals(ErrorCode.EMPLOYEE_NOT_FOUND.getCode(), exception.getCode());
    }

    @Test
    public void testAddLeaveForEmployeeSuccessfully() throws Exception {
        final long overtimeLeaveTypeId = 8;
        final int leaveDaysAllowed = 6;
        final int leaveDaysRemaining = 6;
        final String leaveTypeName = "Overtime";
        final Date validityDate = Date.valueOf("2016-12-12");

        final AnnualBalanceLeaveDTO newAnnualBalanceLeaveDTO = new AnnualBalanceLeaveDTO();
        newAnnualBalanceLeaveDTO.setLeaveDaysAllowed(leaveDaysAllowed);
        newAnnualBalanceLeaveDTO.setLeaveDaysRemaining(leaveDaysRemaining);
        newAnnualBalanceLeaveDTO.setLeaveTypeId(overtimeLeaveTypeId);
        newAnnualBalanceLeaveDTO.setLeaveTypeName(leaveTypeName);
        newAnnualBalanceLeaveDTO.setValidityDate(validityDate);

        mockMvc.perform(post(request(RestURI.ANNUAL_BALANCE_LEAVES_BY_EMPLOYEE, EMPLOYEE_ID))
                .contentType(TestObjectConverter.APPLICATION_JSON_UTF8).content(TestObjectConverter.convertObjectToJsonBytes(newAnnualBalanceLeaveDTO)))
                .andExpect(status().isCreated())
                .andExpect(header().string("location", "/annualBalanceLeaves/6"));

        //check if size of annual balance leaves has increased
        final String contentAsString = mockMvc.perform(get(request(RestURI.ANNUAL_BALANCE_LEAVES_BY_EMPLOYEE, EMPLOYEE_ID)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse().getContentAsString();

        List<LinkedHashMap> result = new ObjectMapper().readValue(contentAsString, List.class);
        Assert.assertEquals(4, result.size());

        final LinkedHashMap firstAnnualBalanceLeave = result.stream().filter(map -> ((int) map.get("id") == 6)).findAny().get();
        Assert.assertEquals(leaveDaysAllowed, (int) firstAnnualBalanceLeave.get("leaveDaysAllowed"));
        Assert.assertEquals(leaveDaysRemaining, (int) firstAnnualBalanceLeave.get("leaveDaysRemaining"));
        Assert.assertEquals(overtimeLeaveTypeId, (int) firstAnnualBalanceLeave.get("leaveTypeId"));
        Assert.assertEquals(leaveTypeName, firstAnnualBalanceLeave.get("leaveTypeName"));
        Assert.assertEquals("2016-12-12", firstAnnualBalanceLeave.get("validityDate"));
    }

    @Test
    public void testDeleteLeaveForEmployeeSuccessfully() throws Exception {
        final long leaveId = 1;

        mockMvc.perform(delete(request(RestURI.ANNUAL_BALANCE_LEAVES_BY_EMPLOYEE_AND_LEAVE_ID, EMPLOYEE_ID, leaveId)))
                .andExpect(status().isOk());

        //check if size of annual balance leaves has decreased
        final String contentAsString = mockMvc.perform(get(request(RestURI.ANNUAL_BALANCE_LEAVES_BY_EMPLOYEE, EMPLOYEE_ID)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse().getContentAsString();

        List<LinkedHashMap> result = new ObjectMapper().readValue(contentAsString, List.class);
        Assert.assertEquals(2, result.size());
    }

    @Test
    public void testDeleteLeaveForEmployeeWhenEmployeeNotExists() throws Exception {
        final long leaveId = 1;

        final String contentAsString = mockMvc.perform(delete(request(RestURI.ANNUAL_BALANCE_LEAVES_BY_EMPLOYEE_AND_LEAVE_ID, EMPLOYEE_ID_NOT_EXISTING, leaveId)))
                .andExpect(status().isNotFound())
                .andReturn()
                .getResponse().getContentAsString();

        ElementNotFoundException exception = new ObjectMapper().readValue(contentAsString, ElementNotFoundException.class);
        Assert.assertEquals(EMPLOYEE_ID_NOT_EXISTING, exception.getElementId());
        Assert.assertEquals(ErrorCode.EMPLOYEE_NOT_FOUND.getCode(), exception.getCode());
    }

}
