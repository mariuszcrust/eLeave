/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.eleave.leave.rest;

import com.company.eleave.employee.rest.IntegrationTest;
import com.company.eleave.rest.dto.AnnualBalanceLeaveDTO;
import com.company.eleave.rest.dto.EmployeeDTO;
import com.company.eleave.rest.exception.RestResponseExceptionHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import utils.RestURI;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

/**
 *
 * @author mdaniel
 */
public class ITAnnualBalanceLeaveController extends IntegrationTest {

    @Autowired
    AnnualBalanceLeaveController annualBalanceLeaveController;

    private MockMvc mockMvc;

    private String contentAsString;

    @Before
    public void before() {
        mockMvc = standaloneSetup(annualBalanceLeaveController).setControllerAdvice(new RestResponseExceptionHandler()).build();
    }

    @Test
    public void testGetAllLeaves() throws Exception {
        contentAsString = mockMvc.perform(get(RestURI.ANNUAL_BALANCE_LEAVES))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse().getContentAsString();

        List<AnnualBalanceLeaveDTO> result = new ObjectMapper().readValue(contentAsString, List.class);
        Assert.assertTrue("Wrong size of all annual balance leaves ", result.size() == 5);
    }
    
    @Test
    public void testGetLeavesForEmployee() throws Exception {
        final long employeeId = 1;
        
        contentAsString = mockMvc.perform(get(request(RestURI.ANNUAL_BALANCE_LEAVES_BY_EMPLOYEE, employeeId)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse().getContentAsString();
        
        List<AnnualBalanceLeaveDTO> result = new ObjectMapper().readValue(contentAsString, List.class);
        Assert.assertEquals(3, result.size());
        
        final AnnualBalanceLeaveDTO firstAnnualBalanceLeave = result.stream().filter(a -> a.getId() == 1).findAny().get();
        
        Assert.assertEquals(1l, firstAnnualBalanceLeave.getId());
        Assert.assertEquals(20, firstAnnualBalanceLeave.getLeaveDaysAllowed());
        Assert.assertEquals(15, firstAnnualBalanceLeave.getLeaveDaysRemaining());
        Assert.assertEquals(10, firstAnnualBalanceLeave.getLeaveTypeId());
        Assert.assertEquals("", firstAnnualBalanceLeave.getLeaveTypeName());
        Assert.assertTrue("2016-12-12".equals(firstAnnualBalanceLeave.getValidityDate()));
        
    }
    /*
    @Test
    public void testAddLeaveForEmployeeWhenEmployeeNotExists() {
        
    }
    
    @Test
    public void testAddLeaveForEmployeeSuccessfully() {
        
    }
    
    @Test
    public void testDeleteLeaveForEmployee(){

}
*/
}
