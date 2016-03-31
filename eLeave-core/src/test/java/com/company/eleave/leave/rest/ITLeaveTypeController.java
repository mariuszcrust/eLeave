/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.eleave.leave.rest;

import utils.IntegrationTest;
import com.company.eleave.rest.dto.LeaveTypeDTO;
import com.company.eleave.rest.exception.ElementNotFoundException;
import com.company.eleave.rest.exception.ErrorCode;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import utils.RestURI;
import utils.TestObjectConverter;

/**
 *
 * @author mdaniel
 */
public class ITLeaveTypeController extends IntegrationTest {

    @Autowired
    LeaveTypeController leaveTypeController;

    private MockMvc mockMvc;

    private static final long STANDARD_HOLIDAY_LEAVE_TYPE = 10;

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

    @Test
    public void testGetByIdWhenLeaveTypeNotExists() throws Exception {
        final long nonExistingLeaveType = 123;
        final String contentAsString = mockMvc.perform(get(request(RestURI.LEAVE_TYPES_BY_ID, nonExistingLeaveType)))
                .andExpect(status().isNotFound())
                .andReturn()
                .getResponse().getContentAsString();

        ElementNotFoundException exception = new ObjectMapper().readValue(contentAsString, ElementNotFoundException.class);
        Assert.assertEquals(nonExistingLeaveType, exception.getElementId());
        Assert.assertEquals(ErrorCode.LEAVE_TYPE_NOT_FOUND.getCode(), exception.getCode());
    }

    @Test
    public void testGetByIdSuccessfully() throws Exception {
        final String contentAsString = mockMvc.perform(get(request(RestURI.LEAVE_TYPES_BY_ID, STANDARD_HOLIDAY_LEAVE_TYPE)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse().getContentAsString();

        LeaveTypeDTO result = new ObjectMapper().readValue(contentAsString, LeaveTypeDTO.class);
        Assert.assertNotNull(result);
        Assert.assertEquals("No comment", result.getComment());
        Assert.assertEquals(20, result.getDefaultDaysAllowed());
        Assert.assertEquals("Standard holiday", result.getLeaveTypeName());
    }

    @Test
    public void testCreateNewLeaveType() throws Exception {
        final LeaveTypeDTO leaveTypeDTO = new LeaveTypeDTO();
        leaveTypeDTO.setComment("java is better then CF");
        leaveTypeDTO.setDefaultDaysAllowed(25);
        leaveTypeDTO.setLeaveTypeName("Devox");

        mockMvc.perform(post(RestURI.LEAVE_TYPES).contentType(TestObjectConverter.APPLICATION_JSON_UTF8).content(TestObjectConverter.convertObjectToJsonBytes(leaveTypeDTO)))
                .andExpect(status().isCreated())
                .andExpect(header().string("location", "/leaveTypes/11"));

        //check if size of all leave types has increased by one
        final String contentAsString = mockMvc.perform(get(RestURI.LEAVE_TYPES))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse().getContentAsString();

        List<LeaveTypeDTO> result = new ObjectMapper().readValue(contentAsString, List.class);
        Assert.assertTrue("Wrong size of all leave types ", result.size() == 11);
    }

    @Test
    public void testUpdateLeaveTypeWhenLeaveTypeNotExists() throws Exception {
        final long leaveTypeNotExisting = 123;
        final String contentAsString = mockMvc.perform(get(request(RestURI.LEAVE_TYPES_BY_ID, leaveTypeNotExisting)))
                .andExpect(status().isNotFound())
                .andReturn()
                .getResponse().getContentAsString();

        ElementNotFoundException exception = new ObjectMapper().readValue(contentAsString, ElementNotFoundException.class);
        Assert.assertEquals(leaveTypeNotExisting, exception.getElementId());
        Assert.assertEquals(ErrorCode.LEAVE_TYPE_NOT_FOUND.getCode(), exception.getCode());
    }

    @Test
    public void testUpdateLeaveSuccessfully() throws Exception {
        final LeaveTypeDTO leaveTypeDTO = new LeaveTypeDTO();
        leaveTypeDTO.setComment("java is better then CF");
        leaveTypeDTO.setDefaultDaysAllowed(40);
        leaveTypeDTO.setLeaveTypeName("Standard holiday in SS");

        final String contentAsString = mockMvc.perform(put(request(RestURI.LEAVE_TYPES_BY_ID, STANDARD_HOLIDAY_LEAVE_TYPE)).contentType(TestObjectConverter.APPLICATION_JSON_UTF8).content(TestObjectConverter.convertObjectToJsonBytes(leaveTypeDTO)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse().getContentAsString();

        LeaveTypeDTO result = new ObjectMapper().readValue(contentAsString, LeaveTypeDTO.class);
        Assert.assertNotNull(result);
        Assert.assertEquals("java is better then CF", result.getComment());
        Assert.assertEquals(40, result.getDefaultDaysAllowed());
        Assert.assertEquals("Standard holiday in SS", result.getLeaveTypeName());
    }

    @Test
    public void testDeleteWhenLeaveTypeNotExists() throws Exception {
        final long leaveTypeNotExisting = 123;
        final String contentAsString = mockMvc.perform(get(request(RestURI.LEAVE_TYPES_BY_ID, leaveTypeNotExisting)))
                .andExpect(status().isNotFound())
                .andReturn()
                .getResponse().getContentAsString();

        ElementNotFoundException exception = new ObjectMapper().readValue(contentAsString, ElementNotFoundException.class);
        Assert.assertEquals(leaveTypeNotExisting, exception.getElementId());
        Assert.assertEquals(ErrorCode.LEAVE_TYPE_NOT_FOUND.getCode(), exception.getCode());
    }

    @Test
    public void testDeleteSuccesfully() throws Exception {
        final long leaveTypeNotUsesAnywhere = 1;
        mockMvc.perform(delete(request(RestURI.LEAVE_TYPES_BY_ID, leaveTypeNotUsesAnywhere)))
                .andExpect(status().isOk());

        final String contentAsString = mockMvc.perform(get(RestURI.LEAVE_TYPES))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse().getContentAsString();

        List<LeaveTypeDTO> result = new ObjectMapper().readValue(contentAsString, List.class);
        Assert.assertTrue("Wrong size of all leave types ", result.size() == 9);
    }

}
