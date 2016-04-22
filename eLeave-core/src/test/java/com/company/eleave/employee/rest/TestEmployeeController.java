package com.company.eleave.employee.rest;

import com.company.eleave.employee.entity.Approver;
import com.company.eleave.employee.entity.Employee;
import org.junit.Assert;
import org.junit.Test;

import com.company.eleave.employee.service.ApproverService;
import com.company.eleave.employee.service.EmployeeService;
import com.company.eleave.rest.dto.ApproverDTO;
import com.company.eleave.rest.dto.EmployeeAccountDTO;
import com.company.eleave.rest.dto.EmployeeDTO;
import com.company.eleave.rest.exception.BadParameterException;
import com.company.eleave.rest.exception.ElementNotFoundException;
import com.company.eleave.rest.exception.ErrorCode;
import com.company.eleave.rest.mapper.EmployeeMapper;
import com.company.eleave.security.entity.User;
import com.google.common.collect.Lists;
import java.util.List;
import org.junit.Before;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class TestEmployeeController {
    
    private static final long EMPLOYEE_ID = 1;
    private static final long APPROVER_ID = 10;
    
    EmployeeService employeeServiceMock;
    ApproverService approverServicMock;
    
    EmployeeController testedObject;
    
    @Before
    public void before() {
        employeeServiceMock = Mockito.mock(EmployeeService.class);
        approverServicMock = Mockito.mock(ApproverService.class);
        
        testedObject = new EmployeeController();
        
        testedObject.setApproverService(approverServicMock);
        testedObject.setEmployeeServie(employeeServiceMock);
        testedObject.setMapper(new EmployeeMapper());
    }
    
    @Test
    public void testGetAll() {
        //given
        Employee employee1 = new Employee();
        employee1.setUser(new User());
        
        Employee employee2 = new Employee();
        employee2.setUser(new User());
        
        final List<Employee> employees = Lists.newArrayList(employee1, employee2);
        Mockito.when(employeeServiceMock.getAll(false)).thenReturn(employees);

        //when
        ResponseEntity<List<EmployeeDTO>> result = testedObject.getAll(false);

        //then
        Assert.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assert.assertEquals(2, result.getBody().size());
    }
    
    @Test
    public void testGetByIdWhenEmployeeNotExists() {
        //given
        Mockito.when(employeeServiceMock.getById(EMPLOYEE_ID)).thenReturn(null);

        //when
        try {
            testedObject.getById(EMPLOYEE_ID);
        } catch (ElementNotFoundException e) {
            //then
            Assert.assertEquals(EMPLOYEE_ID, e.getElementId());
            Assert.assertEquals(ErrorCode.EMPLOYEE_NOT_FOUND.getCode(), e.getCode());
        }
    }
    
    //@Test
    public void testGetByIdWhenEmployeeExists() {
        //given
        final Employee employee = new Employee();
        employee.setFirstName("stephen");
        employee.setLastName("curry");

        Mockito.when(employeeServiceMock.getAllDetailsById(EMPLOYEE_ID)).thenReturn(employee);

        //when
        ResponseEntity<EmployeeDTO> result = testedObject.getById(EMPLOYEE_ID);

        //then
        Assert.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assert.assertEquals(employee.getFirstName(), result.getBody().getFirstName());
        Assert.assertEquals(employee.getLastName(), result.getBody().getLastName());
    }
    
    @Test
    public void testCreateNewEmployee() {
        //given
        final EmployeeDTO newEmployee = new EmployeeDTO();
        newEmployee.setEmail("john.rambo@gmail.com");
        newEmployee.setFirstName("john");
        newEmployee.setLastName("rambo");
        
        Mockito.when(employeeServiceMock.create(new EmployeeMapper().toEntity(newEmployee))).thenReturn(EMPLOYEE_ID);

        //when
        ResponseEntity<Long> result = testedObject.create(newEmployee);

        //then
        Assert.assertEquals(HttpStatus.CREATED, result.getStatusCode());
        Assert.assertEquals("/employees/1", result.getHeaders().getLocation().toString());
    }
    
    @Test
    public void testUpdateWhenEmployeeNotExists() {
        //given
        final EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setEmail("john.rambo");
        employeeDTO.setFirstName("john");
        employeeDTO.setLastName("rambo");
        
        Mockito.when(employeeServiceMock.getById(EMPLOYEE_ID)).thenReturn(null);

        //when
        try {
            testedObject.update(EMPLOYEE_ID, employeeDTO);
        } catch (ElementNotFoundException e) {
            //then
            Assert.assertEquals(EMPLOYEE_ID, e.getElementId());
            Assert.assertEquals(ErrorCode.EMPLOYEE_NOT_FOUND.getCode(), e.getCode());
        }
    }
    
    //@Test
    public void testUpdateEmployeeSuccessfully() {
        //given
        final EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setEmail("newEmail@gmail.com");
        employeeDTO.setFirstName("newJohn");
        employeeDTO.setLastName("newRambo");
        
        final Employee employee = new Employee();
        employee.setEmail("email@gmail.com");
        employee.setFirstName("john");
        employee.setLastName("rambo");
        
        Mockito.when(employeeServiceMock.getById(EMPLOYEE_ID)).thenReturn(employee);

        //when
        ResponseEntity<EmployeeDTO> result = testedObject.update(EMPLOYEE_ID, employeeDTO);

        //then
        Mockito.verify(employeeServiceMock).update(employee);
        Assert.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assert.assertEquals("newEmail@gmail.com", result.getBody().getEmail());
        Assert.assertEquals("newJohn", result.getBody().getFirstName());
        Assert.assertEquals("newRambo", result.getBody().getLastName());
    }
    
    @Test
    public void testAssignApproverWhenEmployeeNotExists() {
        //given
        final ApproverDTO approverDTO = new ApproverDTO();
        
        Mockito.when(employeeServiceMock.getById(EMPLOYEE_ID)).thenReturn(null);

        //when
        try {
            testedObject.assignApprover(EMPLOYEE_ID, approverDTO);
        } catch (ElementNotFoundException e) {
            //then
            Assert.assertEquals(EMPLOYEE_ID, e.getElementId());
            Assert.assertEquals(ErrorCode.EMPLOYEE_NOT_FOUND.getCode(), e.getCode());
        }
    }
    
    @Test
    public void testAssignApproverWhenApproverNotExists() {
        //given
        final ApproverDTO approverDTO = new ApproverDTO();
        approverDTO.setApproverId(APPROVER_ID);
        
        final Employee employee = new Employee();
        
        Mockito.when(employeeServiceMock.getById(EMPLOYEE_ID)).thenReturn(employee);
        Mockito.when(employeeServiceMock.getById(APPROVER_ID)).thenReturn(null);

        //when
        try {
            testedObject.assignApprover(EMPLOYEE_ID, approverDTO);
        } catch (ElementNotFoundException e) {
            //then
            Assert.assertEquals(APPROVER_ID, e.getElementId());
            Assert.assertEquals(ErrorCode.EMPLOYEE_NOT_FOUND.getCode(), e.getCode());
        }
        
    }
    
    @Test
    public void testAssignApprverWhenStartDateNotSet() {
        //given
        final long approverId = 10;
        final ApproverDTO approverDTO = new ApproverDTO();
        approverDTO.setApproverId(approverId);
        approverDTO.setStartDate("1/1/2016");
        
        final Employee employee = new Employee();
        employee.setEmail("john.rambo@gmail.com");
        
        final Employee approver = new Employee();
        approver.setEmail("approver@gmail.com");
        
        Mockito.when(employeeServiceMock.getById(EMPLOYEE_ID)).thenReturn(employee);
        Mockito.when(employeeServiceMock.getById(approverId)).thenReturn(approver);

        //when
        try {
            testedObject.assignApprover(EMPLOYEE_ID, approverDTO);
        } catch (BadParameterException e) {
            //then
            Assert.assertEquals("Element with value: 1/1/2016 is invalid. ", e.getMessage());
            Assert.assertEquals("1/1/2016", e.getValue());
        }
    }
    
    @Test
    public void testAssignApproverSuccessfully() {
        //given
        final long approverId = 10;
        final ApproverDTO approverDTO = new ApproverDTO();
        approverDTO.setApproverId(approverId);
        approverDTO.setStartDate("2016-01-01");
        
        final Employee employee = new Employee();
        employee.setEmail("john.rambo@gmail.com");
        
        final Employee newApprover = new Employee();
        newApprover.setEmail("approver@gmail.com");
        
        final Approver approver = new Approver();
        approver.setEmployee(employee);
        approver.setApprover(employee);
        
        Mockito.when(employeeServiceMock.getById(EMPLOYEE_ID)).thenReturn(employee);
        Mockito.when(employeeServiceMock.getById(approverId)).thenReturn(newApprover);

        //when
        ResponseEntity<Void> result = testedObject.assignApprover(EMPLOYEE_ID, approverDTO);

        //then
        Assert.assertEquals(HttpStatus.OK, result.getStatusCode());
    }
    
    @Test
    public void testReassignApproverWhenEmployeeNotExists() {
        //given
        final long approverId = 10;
        
        Mockito.when(employeeServiceMock.getById(EMPLOYEE_ID)).thenReturn(null);

        //when
        try {
            testedObject.reassignApproverForEmployee(EMPLOYEE_ID, approverId);
        } catch (ElementNotFoundException e) {
            //then
            Assert.assertEquals(EMPLOYEE_ID, e.getElementId());
            Assert.assertEquals(ErrorCode.EMPLOYEE_NOT_FOUND.getCode(), e.getCode());
        }
    }
    
    @Test
    public void testReassignApproverWhenApproverNotExists() {
        //given
        final long approverId = 10;
        
        Mockito.when(employeeServiceMock.getById(EMPLOYEE_ID)).thenReturn(new Employee());
        Mockito.when(employeeServiceMock.getById(approverId)).thenReturn(null);

        //when
        try {
            testedObject.reassignApproverForEmployee(EMPLOYEE_ID, approverId);
        } catch (ElementNotFoundException e) {
            //then
            Assert.assertEquals(approverId, e.getElementId());
            Assert.assertEquals(ErrorCode.EMPLOYEE_NOT_FOUND.getCode(), e.getCode());
        }
    }
    
    @Test
    public void testReassignApproverSuccessfully() {
        //given
        Mockito.when(employeeServiceMock.getById(EMPLOYEE_ID)).thenReturn(new Employee());
        Mockito.when(employeeServiceMock.getById(APPROVER_ID)).thenReturn(new Employee());

        //when
        ResponseEntity<Void> result = testedObject.reassignApproverForEmployee(EMPLOYEE_ID, APPROVER_ID);

        //then
        Mockito.verify(approverServicMock).removeApproverForEmployee(EMPLOYEE_ID);
        Assert.assertEquals(HttpStatus.OK, result.getStatusCode());
    }
    
    @Test
    public void testDeleteEmployeeWhenEmployeeNotExists() {
        //given
        Mockito.when(employeeServiceMock.getById(EMPLOYEE_ID)).thenReturn(null);

        //when
        try {
            testedObject.delete(EMPLOYEE_ID);
        } catch (ElementNotFoundException e) {
            //then
            Assert.assertEquals(EMPLOYEE_ID, e.getElementId());
            Assert.assertEquals(ErrorCode.EMPLOYEE_NOT_FOUND.getCode(), e.getCode());
        }
        
    }
    
    @Test
    public void testDeleteEmployeeSuccessfully() {
        //given
        Mockito.when(employeeServiceMock.getById(EMPLOYEE_ID)).thenReturn(new Employee());

        //when
        ResponseEntity<Void> result = testedObject.delete(EMPLOYEE_ID);

//then
        Assert.assertEquals(HttpStatus.OK, result.getStatusCode());
    }
    
}
