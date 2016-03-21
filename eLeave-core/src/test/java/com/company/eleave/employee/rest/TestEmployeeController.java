package com.company.eleave.employee.rest;

import com.company.eleave.employee.entity.Employee;
import org.junit.Assert;
import org.junit.Test;

import com.company.eleave.employee.service.ApproverService;
import com.company.eleave.employee.service.EmployeeService;
import com.company.eleave.rest.dto.EmployeeDTO;
import com.company.eleave.rest.exception.ElementNotFoundException;
import com.company.eleave.rest.exception.ExceptionElementType;
import com.company.eleave.rest.mapper.EmployeeMapper;
import com.google.common.collect.Lists;
import java.util.List;
import org.junit.Before;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class TestEmployeeController {

    private static final long EMPLOYEE_ID = 1;

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
    public void getAll() {
        //given
        final List<Employee> employees = Lists.newArrayList(new Employee(), new Employee());
        Mockito.when(employeeServiceMock.getAll()).thenReturn(employees);

        //when
        ResponseEntity<List<EmployeeDTO>> result = testedObject.getAll();

        //then
        Mockito.verify(employeeServiceMock).getAll();
        Assert.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assert.assertEquals(2, result.getBody().size());
    }

    @Test
    public void getByIdWhenEmployeeNotExists() {
        //given
        Mockito.when(employeeServiceMock.getById(EMPLOYEE_ID)).thenThrow(new ElementNotFoundException(EMPLOYEE_ID, ExceptionElementType.EMPLOYEE));
        
        //when
        try {
            testedObject.getById(EMPLOYEE_ID);
        } catch (ElementNotFoundException e) {
            //then
            Mockito.verify(employeeServiceMock).getById(EMPLOYEE_ID);
            Assert.assertEquals(EMPLOYEE_ID, e.getElementId().longValue());
            Assert.assertEquals(ExceptionElementType.EMPLOYEE.getName(), e.getClazzType());
        }

    }

    @Test
    public void getByIdWhenEmployeeExists() {
        //given
        final Employee employee = new Employee();
        employee.setFirstName("stephen");
        employee.setLastName("curry");
        
        Mockito.when(employeeServiceMock.getById(EMPLOYEE_ID)).thenReturn(employee);

        //when
        ResponseEntity<EmployeeDTO> result = testedObject.getById(EMPLOYEE_ID);
        
        //then
        Assert.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assert.assertEquals(employee.getFirstName(), result.getBody().getFirstName());
        Assert.assertEquals(employee.getLastName(), result.getBody().getLastName());
    }
}
