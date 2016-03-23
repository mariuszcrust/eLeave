/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.eleave.leave.rest;

import com.company.eleave.employee.rest.IntegrationTest;
import com.company.eleave.rest.dto.LeaveTypeDTO;
import com.company.eleave.rest.exception.RestResponseExceptionHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import utils.RestURI;

/**
 *
 * @author mdaniel
 */
public class ITLeaveTypeController extends IntegrationTest {

    @Autowired
    AnnualBalanceLeaveController leaveTypeController;

    private MockMvc mockMvc;

    @Before
    public void before() {
        mockMvc = standaloneSetup(leaveTypeController).setControllerAdvice(new RestResponseExceptionHandler()).build();
    }

    @Test
    public void testGetAll() throws Exception {
        final String contentAsString = mockMvc.perform(get(RestURI.LEAVE_TYPES))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse().getContentAsString();

        List<LeaveTypeDTO> result = new ObjectMapper().readValue(contentAsString, List.class);
        Assert.assertTrue("Wrong size of all leave types ", result.size() == 10);
    }

    /*
    @Test
    public void testGetByIdWhenLeaveTypeNotExists() {

    }

    @Test
    public void testGetByIdSuccessfully() {

    }

    @Test
    public void testCreateNewLeaveType() {

    }

    @Test
    public void testUpdateLeaveTypeWhenLeaveTypeNotExists() {

    }

    @Test
    public void testUpdateLeaveSuccessfully() {

    }

    @Test
    public void testDeleteWhenLeaveTypeNotExists() {

    }

    @Test
    public void testDeleteSuccesfully() {

    }
*/
}
