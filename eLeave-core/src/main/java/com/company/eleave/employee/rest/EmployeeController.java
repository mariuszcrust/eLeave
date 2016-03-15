package com.company.eleave.employee.rest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.company.eleave.employee.entity.Approver;
import com.company.eleave.employee.entity.Employee;
import com.company.eleave.employee.service.ApproverService;
import com.company.eleave.employee.service.EmployeeService;
import com.company.eleave.rest.dto.ApproverDTO;
import com.company.eleave.rest.dto.EmployeeDTO;
import com.company.eleave.rest.exception.BadParameterException;
import com.company.eleave.rest.exception.ElementNotFoundException;
import com.company.eleave.rest.exception.ExceptionElementType;
import com.company.eleave.rest.exception.ExceptionParameterType;
import com.company.eleave.rest.mapper.EmployeeMapper;

@RestController
@RequestMapping(value = "/employees")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @Autowired
    ApproverService approverService;

    @Autowired
    EmployeeMapper mapper;

    private static final SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyy-MM-dd");

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<EmployeeDTO>> getAll() {
        List<EmployeeDTO> result = employeeService.getAll().stream().map(employee -> mapper.toDto(employee)).collect(Collectors.toList());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<EmployeeDTO> getById(@PathVariable("id") final Long employeeId) {
        final Employee result = employeeService.getById(employeeId);
        if (result == null) {
            throw new ElementNotFoundException(employeeId, ExceptionElementType.EMPLOYEE);
        }

        return new ResponseEntity<>(mapper.toDto(result), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Long> create(@RequestBody EmployeeDTO employee) {
        final long employeeId = employeeService.create(mapper.toEntity(employee));

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(UriComponentsBuilder.fromPath("/employees/{id}").buildAndExpand(employeeId).toUri());

        return new ResponseEntity<Long>(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<EmployeeDTO> update(final @PathVariable("id") Long employeeId, final @RequestBody EmployeeDTO employeeDTO) {
        final Employee currentEmployee = employeeService.getById(employeeId);

        if (currentEmployee == null) {
            throw new ElementNotFoundException(employeeId, ExceptionElementType.EMPLOYEE);
        }

        // TODO make some propagate methods
        currentEmployee.setEmail(employeeDTO.getEmail());
        currentEmployee.setFirstName(employeeDTO.getFirstName());
        currentEmployee.setLastName(employeeDTO.getLastName());

        employeeService.update(currentEmployee);

        return new ResponseEntity<>(mapper.toDto(currentEmployee), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}/approver", method = RequestMethod.PUT)
    public ResponseEntity<Void> assignApprover(final @PathVariable("id") Long employeeId, final @RequestBody ApproverDTO approverDTO) {
        final Employee currentEmployee = employeeService.getById(employeeId);
        if (currentEmployee == null) {
            throw new ElementNotFoundException(employeeId, ExceptionElementType.EMPLOYEE);
        }

        final Employee newApprover = employeeService.getById(approverDTO.getApproverId());
        if (newApprover == null) {
            throw new ElementNotFoundException(approverDTO.getApproverId(), ExceptionElementType.EMPLOYEE);
        }

        final Approver approver = new Approver();
        approver.setEmployee(currentEmployee);
        approver.setApprover(newApprover);
        try {
            approver.setStartDate(FORMATTER.parse(approverDTO.getStartDate()));
            approver.setEndDate(FORMATTER.parse(approverDTO.getEndDate()));
        } catch (ParseException e) {
            throw new BadParameterException(approverDTO.getStartDate(), ExceptionParameterType.START_END_DATE_FOR_APPROVER.getName(), e.getMessage());
        }

        approverService.assignApprover(approver);

        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}/approver/{approverId}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> removeApproverForEmployee(final @PathVariable("id") Long employeeId, final @PathVariable("approverId") Long approverId) {
        final Employee employee = employeeService.getById(employeeId);
        if (employee == null) {
            throw new ElementNotFoundException(employeeId, ExceptionElementType.EMPLOYEE);
        }

        final Employee approver = employeeService.getById(approverId);
        if (approver == null) {
            throw new ElementNotFoundException(approverId, ExceptionElementType.EMPLOYEE);
        }

        approverService.removeApproverForEmployee(employeeId);

        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable("id") final Long employeeId) {
        final Employee currentEmployee = employeeService.getById(employeeId);

        if (currentEmployee == null) {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }

        employeeService.delete(employeeId);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

}
