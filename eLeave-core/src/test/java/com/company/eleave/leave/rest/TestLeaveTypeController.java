/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.eleave.leave.rest;

import com.company.eleave.leave.entity.LeaveType;
import com.company.eleave.leave.service.LeaveTypeService;
import com.company.eleave.rest.dto.LeaveTypeDTO;
import com.company.eleave.rest.exception.BadParameterException;
import com.company.eleave.rest.exception.ElementNotFoundException;
import com.company.eleave.rest.exception.ErrorCode;
import com.company.eleave.rest.mapper.LeaveTypeMapper;
import com.google.common.collect.Lists;
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
public class TestLeaveTypeController {

    private static final long LEAVE_TYPE_ID = 1l;

    private LeaveTypeService leaveTypeServiceMock;
    private LeaveTypeMapper mapperMock;

    private LeaveTypeController testedObject;

    @Before
    public void before() {
        leaveTypeServiceMock = Mockito.mock(LeaveTypeService.class);
        mapperMock = Mockito.mock(LeaveTypeMapper.class);

        testedObject = new LeaveTypeController();
        testedObject.setLeaveTypeMapper(mapperMock);
        testedObject.setLeaveTypeService(leaveTypeServiceMock);
    }

    @Test
    public void testGetAll() {
        //given
        Mockito.when(leaveTypeServiceMock.getAll()).thenReturn(Lists.newArrayList(new LeaveType(), new LeaveType()));

        //when
        ResponseEntity<List<LeaveTypeDTO>> result = testedObject.getAll();

        //then
        Assert.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assert.assertEquals(2, result.getBody().size());
    }

    @Test
    public void testGetByIdSuccessfuly() {
        //given
        final LeaveType leaveType = new LeaveType();
        leaveType.setComment("testComment");
        leaveType.setLeaveTypeName("testLeaveTypeName");

        final LeaveTypeDTO leaveTypeDTO = new LeaveTypeDTO();
        leaveTypeDTO.setComment("testComment");
        leaveTypeDTO.setLeaveTypeName("testLeaveTypeName");

        Mockito.when(leaveTypeServiceMock.getById(LEAVE_TYPE_ID)).thenReturn(leaveType);
        Mockito.when(mapperMock.toDto(leaveType)).thenReturn(leaveTypeDTO);

        //when
        ResponseEntity<LeaveTypeDTO> result = testedObject.getById(LEAVE_TYPE_ID);

        //then
        Assert.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assert.assertNotNull(result.getBody());
        Assert.assertEquals("testComment", result.getBody().getComment());
        Assert.assertEquals("testLeaveTypeName", result.getBody().getLeaveTypeName());
    }

    @Test
    public void testGetByIdWhenNotExists() {
        //given
        Mockito.when(leaveTypeServiceMock.getById(LEAVE_TYPE_ID)).thenReturn(null);

        //when
        try {
            testedObject.getById(LEAVE_TYPE_ID);
        } catch (ElementNotFoundException e) {
            //then
            Assert.assertEquals(LEAVE_TYPE_ID, e.getElementId());
            Assert.assertEquals(ErrorCode.LEAVE_TYPE_NOT_FOUND.getCode(), e.getCode());
        }
    }

    @Test
    public void testCreateLeaveType() {
        //given
        final long newLeaveTypeId = 10l;

        final LeaveTypeDTO leaveTypeDTO = new LeaveTypeDTO();
        leaveTypeDTO.setComment("testComment");
        leaveTypeDTO.setDefaultDaysAllowed(20);
        leaveTypeDTO.setLeaveTypeName("testLeaveTypeName");

        final LeaveType leaveType = new LeaveType();
        leaveType.setComment("testComment");
        leaveType.setDefaultDaysAllowed(20);
        leaveType.setLeaveTypeName("testLeaveTypeName");

        Mockito.when(mapperMock.toEntity(leaveTypeDTO)).thenReturn(leaveType);
        Mockito.when(leaveTypeServiceMock.createNewLeaveType(leaveType)).thenReturn(newLeaveTypeId);

        //when
        ResponseEntity<Long> result = testedObject.createNewLeaveType(leaveTypeDTO);

        //then
        Assert.assertEquals(HttpStatus.CREATED, result.getStatusCode());
        Assert.assertEquals("/leaveTypes/10", result.getHeaders().getLocation().toString());
    }

    @Test
    public void testUpdateLeaveType() {
        //given
        final LeaveTypeDTO leaveTypeDTO = new LeaveTypeDTO();
        leaveTypeDTO.setComment("newComment");
        leaveTypeDTO.setDefaultDaysAllowed(200);
        leaveTypeDTO.setLeaveTypeName("newTestLeaveTypeName");

        final LeaveType leaveType = new LeaveType();
        leaveType.setComment("testComment");
        leaveType.setDefaultDaysAllowed(20);
        leaveType.setLeaveTypeName("testLeaveTypeName");

        Mockito.when(leaveTypeServiceMock.getById(LEAVE_TYPE_ID)).thenReturn(leaveType);
        Mockito.when(mapperMock.toDto(leaveType)).thenReturn(leaveTypeDTO);

        //when
        ResponseEntity<LeaveTypeDTO> result = testedObject.updateLeaveType(LEAVE_TYPE_ID, leaveTypeDTO);

        //then
        Mockito.verify(leaveTypeServiceMock).update(leaveType);
        Assert.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assert.assertNotNull(result.getBody());
        Assert.assertEquals("newComment", result.getBody().getComment());
        Assert.assertEquals(200, result.getBody().getDefaultDaysAllowed());
        Assert.assertEquals("newTestLeaveTypeName", result.getBody().getLeaveTypeName());
    }

    @Test
    public void testDeleteLeaveTypeWhenNotExists() {
        //given
        Mockito.when(leaveTypeServiceMock.getById(LEAVE_TYPE_ID)).thenReturn(null);

        //when
        try {
            testedObject.delete(LEAVE_TYPE_ID);
        } catch (ElementNotFoundException e) {
            Assert.assertEquals(LEAVE_TYPE_ID, e.getElementId());
            Assert.assertEquals(ErrorCode.LEAVE_TYPE_NOT_FOUND.getCode(), e.getCode());
        }

    }

    @Test
    public void testDeleteLeaveTypeSuccessfuly() {
        //given
        Mockito.when(leaveTypeServiceMock.getById(LEAVE_TYPE_ID)).thenReturn(new LeaveType());
        Mockito.when(leaveTypeServiceMock.isLeaveTypeAssignedToAnyLeave(LEAVE_TYPE_ID)).thenReturn(false);

        //when
        ResponseEntity<Void> result = testedObject.delete(LEAVE_TYPE_ID);

        //then
        Mockito.verify(leaveTypeServiceMock).delete(LEAVE_TYPE_ID);
        Assert.assertEquals(HttpStatus.OK, result.getStatusCode());

    }

    @Test
    public void testDeleteLeaveTypeWhenLeaveTypeAssignedToAnyLeave() {
        //given
        Mockito.when(leaveTypeServiceMock.getById(LEAVE_TYPE_ID)).thenReturn(new LeaveType());
        Mockito.when(leaveTypeServiceMock.isLeaveTypeAssignedToAnyLeave(LEAVE_TYPE_ID)).thenReturn(true);

        //when
        try {
            testedObject.delete(LEAVE_TYPE_ID);
        } catch (BadParameterException e) {
            //then
            Assert.assertEquals(ErrorCode.LEAVE_TYPE_CANNOT_BE_REMOVED_DB_CONSTRAINTS.getCode(), e.getCode());
            Assert.assertEquals("Element with id: 1 cannot be removed. ", e.getMessage());
            Assert.assertEquals(String.valueOf(LEAVE_TYPE_ID), e.getValue());
        }
    }
}
