/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.eleave.leave.rest;

import com.company.eleave.employee.rest.IntegrationTest;
import com.company.eleave.rest.dto.TakenLeaveDTO;
import com.company.eleave.rest.exception.RestResponseExceptionHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import utils.RestURI;

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

    @Before
    public void before() {
        mockMvc = standaloneSetup(takenLeaveController).setControllerAdvice(new RestResponseExceptionHandler()).build();
    }

    //@RequestMapping(method = POST)
    //public ResponseEntity<Long> createNewLeave(@RequestBody TakenLeaveDTO takenLeaveDTO) {
    //@Test
    //public void testCreateNewLeaveSuccessfully() {

    //}

    //@RequestMapping(path = "/{id}/status", method = PUT)
    //public ResponseEntity<Void> updateStatusLeave(@PathVariable("id") final Long takenLeaveId, @RequestBody LeaveStatusDTO leaveStatusDTO) {
    //@Test
    //public void testUpdateStatusWhenTakenLeaveNotExists() {

    //}

    //@Test
    //public void testUpdateStatusSuccessfully() {

    //}

    //@RequestMapping(path = "/{id}", method = DELETE)
    //public ResponseEntity<Void> deleteLeave(@PathVariable("id") final Long takenLeaveId) {
    //@Test
    //public void testDeleteWhenTakenLeaveNotExists() {

    //}

    //@Test
    //public void testDeleteSuccessfully() {

    //}

    //@RequestMapping(path = "/employee/{employeeId}", method = GET)
    //public ResponseEntity<List<TakenLeaveDTO>> getAllLeavesForEmployee(@PathVariable("employeeId") final Long employeeId) {
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

        /*final LinkedHashMap standardAnnualBalanceLeave = result.stream().filter(map -> ((int) map.get("id") == 1)).findAny().get();
        Assert.assertEquals(20, (int) standardAnnualBalanceLeave.get("leaveDaysAllowed"));
        Assert.assertEquals(15, (int) standardAnnualBalanceLeave.get("leaveDaysRemaining"));
        Assert.assertEquals(10, (int) standardAnnualBalanceLeave.get("leaveTypeId"));
        Assert.assertEquals("Standard holiday", standardAnnualBalanceLeave.get("leaveTypeName"));
        Assert.assertEquals(1481497200000l, (long) standardAnnualBalanceLeave.get("validityDate"));
        
        final LinkedHashMap bloodDonationAnnualBalanceLeave = result.stream().filter(map -> ((int) map.get("id") == 4)).findAny().get();
        Assert.assertEquals(20, (int) bloodDonationAnnualBalanceLeave.get("leaveDaysAllowed"));
        Assert.assertEquals(15, (int) bloodDonationAnnualBalanceLeave.get("leaveDaysRemaining"));
        Assert.assertEquals(10, (int) bloodDonationAnnualBalanceLeave.get("leaveTypeId"));
        Assert.assertEquals("Standard holiday", bloodDonationAnnualBalanceLeave.get("leaveTypeName"));
        Assert.assertEquals(1481497200000l, (long) bloodDonationAnnualBalanceLeave.get("validityDate"));*/
    }

    //@RequestMapping(path = "/approver/{approverId}", method = GET)
    //public ResponseEntity<List<TakenLeaveDTO>> getAllLeavesForApprover(@PathVariable("approverId") final Long approverId) {
    //@Test
    //public void testGetAllLeavesForApproverNotExisting() {
//
    //}

    @Test
    public void testGetAllLeavesForApproverSuccessfully() throws Exception {
        final String contentAsString = mockMvc.perform(get(request(RestURI.TAKEN_LEAVES_BY_APPROVER, APPROVER_ID)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse().getContentAsString();

        List<LinkedHashMap> result = new ObjectMapper().readValue(contentAsString, List.class);
        Assert.assertEquals(3, result.size());
        
        /*TakenLeaveDTO t = new TakenLeaveDTO();
        t.getApproverId();
t.getApproverName();
t.getLeaveDaysTaken();
t.getLeaveFrom();
t.getLeaveTo();
t.getLeaveType();
t.getStatus().getComment();
t.getStatus().getStatus();*/
        
        /*
                final LinkedHashMap approvedStandardAnnualBalanceLeave = result.stream().filter(map -> ((int) map.get("id") == 1)).findAny().get();
        Assert.assertEquals(20, (int) approvedStandardAnnualBalanceLeave.get("leaveDaysAllowed"));
        Assert.assertEquals(15, (int) approvedStandardAnnualBalanceLeave.get("leaveDaysRemaining"));
        Assert.assertEquals(10, (int) approvedStandardAnnualBalanceLeave.get("leaveTypeId"));
        Assert.assertEquals("Standard holiday", approvedStandardAnnualBalanceLeave.get("leaveTypeName"));
        Assert.assertEquals(1481497200000l, (long) approvedStandardAnnualBalanceLeave.get("validityDate"));
        
        final LinkedHashMap bloodDonationAnnualBalanceLeave = result.stream().filter(map -> ((int) map.get("id") == 2)).findAny().get();
        Assert.assertEquals(20, (int) bloodDonationAnnualBalanceLeave.get("leaveDaysAllowed"));
        Assert.assertEquals(15, (int) bloodDonationAnnualBalanceLeave.get("leaveDaysRemaining"));
        Assert.assertEquals(10, (int) bloodDonationAnnualBalanceLeave.get("leaveTypeId"));
        Assert.assertEquals("Standard holiday", bloodDonationAnnualBalanceLeave.get("leaveTypeName"));
        Assert.assertEquals(1481497200000l, (long) bloodDonationAnnualBalanceLeave.get("validityDate"));
*/
    }

}
