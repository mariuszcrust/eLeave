/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.eleave.leave.rest;

import com.company.eleave.employee.service.EmployeeService;
import com.company.eleave.leave.entity.AnnualBalanceLeave;
import com.company.eleave.leave.service.AnnualBalanceService;
import com.company.eleave.rest.dto.AnnualBalanceLeaveDTO;
import com.company.eleave.rest.exception.ElementNotFoundException;
import com.company.eleave.rest.exception.ExceptionElementType;
import com.company.eleave.rest.mapper.AnnualBalanceLeaveMapper;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author aga
 */
public class TestAnnualBalanceLeaveController {

    private static final long EMPLOYEE_ID = 1;

    private AnnualBalanceService annualBalanceServiceMock;
    private EmployeeService employeeServiceMock;
    private AnnualBalanceLeaveMapper mapperMock;

    private AnnualBalanceLeaveController testedObject;

    @Before
    public void before() {
        annualBalanceServiceMock = Mockito.mock(AnnualBalanceService.class);
        employeeServiceMock = Mockito.mock(EmployeeService.class);

        testedObject = new AnnualBalanceLeaveController();
        testedObject.setAnnualBalanceLeaveMapper(mapperMock);
        testedObject.setAnnualBalanceService(annualBalanceServiceMock);
        testedObject.setEmployeeService(employeeServiceMock);

    }

    @Test
    public void testGetAllLeaves() {
        //given
        ArrayList<AnnualBalanceLeave> allLeaves = Lists.newArrayList(new AnnualBalanceLeave(), new AnnualBalanceLeave());
        Mockito.when(annualBalanceServiceMock.getAllLeaves()).thenReturn(allLeaves);

        //when
        ResponseEntity<List<AnnualBalanceLeaveDTO>> result = testedObject.getAllLeaves();

        //then
        Mockito.verify(annualBalanceServiceMock).getAllLeaves();
        Assert.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assert.assertEquals(2, result.getBody().size());
    }

    @Test
    public void testGetLeavesForEmployeeWhenNotExists() {
        //given
        Mockito.when(employeeServiceMock.getById(EMPLOYEE_ID)).thenReturn(null);

        //when
        try {
            testedObject.getLeavesForEmployee(EMPLOYEE_ID);
        } catch (ElementNotFoundException e) {
            //then
            Assert.assertEquals(EMPLOYEE_ID, e.getElementId().longValue());
            Assert.assertEquals(ExceptionElementType.ANNUAL_BALANCE_LEAVE.getName(), e.getClazzType());
        }

    }

    /*
    @Test
    public void testGetLeavesForEmployeeSuccessfuly() {
        //given

        //when
        //then
    }

    @Test
    public void testAddLeaveForEmployeeWhenNotExists() {
        //given

        //when
        //then
    }

    @Test
    public void testaddLeaveForEmployeeSuccessfuly() {
        //given

        //when
        //then
    }

    @Test
    public void testDeleteLeaveForEmployeeByLeaveIdWhenLeaveNotExists() {
        //given

        //when
        //then
    }

    @Test
    public void testDeleteLeaveForEmployeeByLeaveIdSuccessfuly() {
        //given

        //when
        //then
    }
*/
}
