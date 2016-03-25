/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.eleave.leave.rest;

import com.company.eleave.employee.entity.Employee;
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
        mapperMock = Mockito.mock(AnnualBalanceLeaveMapper.class);

        testedObject = new AnnualBalanceLeaveController();
        testedObject.setAnnualBalanceLeaveMapper(mapperMock);
        testedObject.setAnnualBalanceService(annualBalanceServiceMock);
        testedObject.setEmployeeService(employeeServiceMock);

    }

    @Test
    public void testGetAllLeaves() {
        //given
        AnnualBalanceLeave firstAnnualBalanceLeave = new AnnualBalanceLeave();
        AnnualBalanceLeave secondAnnualBalanceLeave = new AnnualBalanceLeave();
        ArrayList<AnnualBalanceLeave> allLeaves = Lists.newArrayList(firstAnnualBalanceLeave, secondAnnualBalanceLeave);
        Mockito.when(annualBalanceServiceMock.getAllLeaves()).thenReturn(allLeaves);
        Mockito.when(mapperMock.toDto(firstAnnualBalanceLeave)).thenReturn(new AnnualBalanceLeaveDTO());
        Mockito.when(mapperMock.toDto(secondAnnualBalanceLeave)).thenReturn(new AnnualBalanceLeaveDTO());

        //when
        ResponseEntity<List<AnnualBalanceLeaveDTO>> result = testedObject.getAllLeaves();

        //then
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
            Assert.assertEquals(ExceptionElementType.EMPLOYEE.getName(), e.getClazzType());
        }

    }

    @Test
    public void testGetLeavesForEmployeeSuccessfuly() {
        //given
        Mockito.when(employeeServiceMock.getById(EMPLOYEE_ID)).thenReturn(new Employee());
        Mockito.when(annualBalanceServiceMock.getLeavesForUser(EMPLOYEE_ID)).thenReturn(Lists.newArrayList(new AnnualBalanceLeave(), new AnnualBalanceLeave()));

        //when
        ResponseEntity<List<AnnualBalanceLeaveDTO>> result = testedObject.getLeavesForEmployee(EMPLOYEE_ID);

        //then
        Assert.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assert.assertEquals(2, result.getBody().size());
    }

    @Test
    public void testAddLeaveForEmployeeWhenNotExists() {
        //given
        final AnnualBalanceLeaveDTO newAnnualBalance = new AnnualBalanceLeaveDTO();
        Mockito.when(employeeServiceMock.getById(EMPLOYEE_ID)).thenReturn(null);

        //when
        try {
            testedObject.addLeaveForEmployee(EMPLOYEE_ID, newAnnualBalance);
        } catch (ElementNotFoundException e) {

            //then
            Assert.assertEquals(EMPLOYEE_ID, e.getElementId().longValue());
            Assert.assertEquals(ExceptionElementType.EMPLOYEE.getName(), e.getClazzType());
        }
    }

    @Test
    public void testaddLeaveForEmployeeSuccessfuly() {
        //given
        final long createdAnnualBalanceId = 10l;
        final AnnualBalanceLeaveDTO newAnnualBalanceDTO = new AnnualBalanceLeaveDTO();
        
        final AnnualBalanceLeave createdAnnualBalanceLeave = new AnnualBalanceLeave();
        createdAnnualBalanceLeave.setId(createdAnnualBalanceId);
        
        Mockito.when(employeeServiceMock.getById(EMPLOYEE_ID)).thenReturn(new Employee());
        Mockito.when(mapperMock.toEntity(newAnnualBalanceDTO)).thenReturn(createdAnnualBalanceLeave);
        Mockito.when(annualBalanceServiceMock.createLeave(createdAnnualBalanceLeave)).thenReturn(createdAnnualBalanceId);

        //when
        ResponseEntity<Void> result = testedObject.addLeaveForEmployee(EMPLOYEE_ID, newAnnualBalanceDTO);

        //then
        Assert.assertEquals(HttpStatus.CREATED, result.getStatusCode());
        Assert.assertEquals("/annualBalanceLeaves/10", result.getHeaders().getLocation().toString());
    }

    /*
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
