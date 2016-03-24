/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.eleave.leave.rest;

import utils.IntegrationTest;
import com.company.eleave.rest.dto.LeaveStatusDTO;
import com.company.eleave.rest.dto.TakenLeaveDTO;
import com.company.eleave.rest.exception.RestResponseExceptionHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
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
import utils.RestURI;
import utils.TestObjectConverter;

/**
 *
 * @author mdaniel
 */
public class ITTakenLeaveController extends IntegrationTest {

    @Autowired
    TakenLeaveController takenLeaveController;

    private MockMvc mockMvc;

    private static final long EMPLOYEE_ID = 1;

    private static final long APPROVER_ID = 5;
    
    private static final long TAKEN_LEAVE_ID = 1;
    
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    @Before
    public void before() {
        mockMvc = standaloneSetup(takenLeaveController).setControllerAdvice(new RestResponseExceptionHandler()).build();
    }

    @Test
    public void testCreateNewLeaveSuccessfully() throws Exception {
        final TakenLeaveDTO newTakenLeaveDTO = new TakenLeaveDTO();
        newTakenLeaveDTO.setAnnualBalanceLeaveId(1);
        newTakenLeaveDTO.setApproverId(APPROVER_ID);
        newTakenLeaveDTO.setLeaveDaysTaken(10);
        newTakenLeaveDTO.setLeaveFrom(DATE_FORMAT.parse("2016-05-05"));
        newTakenLeaveDTO.setLeaveTo(DATE_FORMAT.parse("2016-05-14"));
        newTakenLeaveDTO.setLeaveTypeId(EMPLOYEE_ID);
        
        LeaveStatusDTO leaveStatusDTO = new LeaveStatusDTO();
        leaveStatusDTO.setComment("pleaseeee");
        leaveStatusDTO.setStatus("PENDING");
        
        newTakenLeaveDTO.setStatus(leaveStatusDTO);
        
        mockMvc.perform(post(RestURI.TAKEN_LEAVES).contentType(TestObjectConverter.APPLICATION_JSON_UTF8).content(TestObjectConverter.convertObjectToJsonBytes(newTakenLeaveDTO)))
                .andExpect(status().isCreated())
                .andExpect(header().string("location", "/takenLeaves/6"));

        final String contentAsString = mockMvc.perform(get(request(RestURI.TAKEN_LEAVES_BY_EMPLOYEE, EMPLOYEE_ID)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse().getContentAsString();

        List<LinkedHashMap> result = new ObjectMapper().readValue(contentAsString, List.class);
        Assert.assertEquals(5, result.size());
    }

    @Test
    public void testUpdateStatusSuccessfully() {

    }

    //@RequestMapping(path = "/{id}", method = DELETE)
    //public ResponseEntity<Void> deleteLeave(@PathVariable("id") final Long takenLeaveId) {
    @Test
    public void testDeleteWhenTakenLeaveNotExists() throws Exception {
        final long takenLeaveNotExisting = 123;
        final String contentAsString = mockMvc.perform(delete(request(RestURI.TAKEN_LEAVES_BY_ID, takenLeaveNotExisting)))
                .andExpect(status().isNotFound())
                .andReturn()
                .getResponse().getContentAsString();

        Assert.assertTrue("Element with id: 123 of type: TakenLeave has not been found".equals(contentAsString));
    }

    @Test
    public void testDeleteSuccessfully() throws Exception {
        mockMvc.perform(delete(request(RestURI.TAKEN_LEAVES_BY_ID, TAKEN_LEAVE_ID)))
                .andExpect(status().isOk());

                final String contentAsString = mockMvc.perform(get(request(RestURI.TAKEN_LEAVES_BY_EMPLOYEE, EMPLOYEE_ID)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse().getContentAsString();

        List<LinkedHashMap> result = new ObjectMapper().readValue(contentAsString, List.class);
        Assert.assertEquals(3, result.size());
    }

    @Test
    public void testGetTakenLeavesForEmployeeNotExists() throws Exception {
        final long employeeIdNotExisting = 100l;
        final String contentAsString = mockMvc.perform(get(request(RestURI.TAKEN_LEAVES_BY_EMPLOYEE, employeeIdNotExisting)))
                .andExpect(status().isNotFound())
                .andReturn()
                .getResponse().getContentAsString();

        Assert.assertEquals("Element with id: 100 of type: Employee has not been found", contentAsString);
    }

    @Test
    public void testGetTakenLeavesForEmployeeSuccessfuly() throws Exception {
        final String contentAsString = mockMvc.perform(get(request(RestURI.TAKEN_LEAVES_BY_EMPLOYEE, EMPLOYEE_ID)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse().getContentAsString();

        List<LinkedHashMap> result = new ObjectMapper().readValue(contentAsString, List.class);
        Assert.assertEquals(4, result.size());

        final LinkedHashMap standardAnnualBalanceLeave = result.stream().filter(map -> ((int) map.get("id") == 1)).findAny().get();
        Assert.assertEquals(5, (int) standardAnnualBalanceLeave.get("approverId"));
        Assert.assertEquals("john doe5Approver", standardAnnualBalanceLeave.get("approverName"));
        Assert.assertEquals(1, (int) standardAnnualBalanceLeave.get("leaveDaysTaken"));
        Assert.assertEquals("2016-01-01", standardAnnualBalanceLeave.get("leaveFrom"));
        Assert.assertEquals("2016-01-01", standardAnnualBalanceLeave.get("leaveTo"));
        Assert.assertEquals("Standard holiday", standardAnnualBalanceLeave.get("leaveType"));

        final LinkedHashMap bloodDonationAnnualBalanceLeave = result.stream().filter(map -> ((int) map.get("id") == 4)).findAny().get();
        Assert.assertEquals(10, (int) bloodDonationAnnualBalanceLeave.get("approverId"));
        Assert.assertEquals("john doeApprover10", bloodDonationAnnualBalanceLeave.get("approverName"));
        Assert.assertEquals(1, (int) bloodDonationAnnualBalanceLeave.get("leaveDaysTaken"));
        Assert.assertEquals("2016-02-02", bloodDonationAnnualBalanceLeave.get("leaveFrom"));
        Assert.assertEquals("2016-02-02", bloodDonationAnnualBalanceLeave.get("leaveTo"));
        Assert.assertEquals("Blood donation", bloodDonationAnnualBalanceLeave.get("leaveType"));
    }

    @Test
    public void testGetAllLeavesForApproverNotExisting() throws Exception {
        final long employeeIdNotExisting = 100l;
        final String contentAsString = mockMvc.perform(get(request(RestURI.TAKEN_LEAVES_BY_APPROVER, employeeIdNotExisting)))
                .andExpect(status().isNotFound())
                .andReturn()
                .getResponse().getContentAsString();

        Assert.assertEquals("Element with id: 100 of type: Employee has not been found", contentAsString);
    }
    
    @Test
    public void testGetAllLeavesForApproverSuccessfully() throws Exception {
        final String contentAsString = mockMvc.perform(get(request(RestURI.TAKEN_LEAVES_BY_APPROVER, APPROVER_ID)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse().getContentAsString();

        List<LinkedHashMap> result = new ObjectMapper().readValue(contentAsString, List.class);
        Assert.assertEquals(3, result.size());

        final LinkedHashMap approvedStandardAnnualBalanceLeave = result.stream().filter(map -> ((int) map.get("id") == 1)).findAny().get();
        Assert.assertEquals(5, (int) approvedStandardAnnualBalanceLeave.get("approverId"));
        Assert.assertEquals("john doe5Approver", approvedStandardAnnualBalanceLeave.get("approverName"));
        Assert.assertEquals(1, (int) approvedStandardAnnualBalanceLeave.get("leaveDaysTaken"));
        Assert.assertEquals("2016-01-01", approvedStandardAnnualBalanceLeave.get("leaveFrom"));
        Assert.assertEquals("2016-01-01", approvedStandardAnnualBalanceLeave.get("leaveTo"));
        Assert.assertEquals("Standard holiday", approvedStandardAnnualBalanceLeave.get("leaveType"));

        final LinkedHashMap approvedStandard2AnnualBalanceLeave = result.stream().filter(map -> ((int) map.get("id") == 2)).findAny().get();
        Assert.assertEquals(5, (int) approvedStandard2AnnualBalanceLeave.get("approverId"));
        Assert.assertEquals("john doe5Approver", approvedStandard2AnnualBalanceLeave.get("approverName"));
        Assert.assertEquals(4, (int) approvedStandard2AnnualBalanceLeave.get("leaveDaysTaken"));
        Assert.assertEquals("2016-02-02", approvedStandard2AnnualBalanceLeave.get("leaveFrom"));
        Assert.assertEquals("2016-02-05", approvedStandard2AnnualBalanceLeave.get("leaveTo"));
        Assert.assertEquals("Standard holiday", approvedStandard2AnnualBalanceLeave.get("leaveType"));
    }

}
