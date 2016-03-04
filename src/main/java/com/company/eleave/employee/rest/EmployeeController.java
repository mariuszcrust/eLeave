package com.company.eleave.employee.rest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.company.eleave.employee.entity.Approver;
import com.company.eleave.employee.entity.Employee;
import com.company.eleave.employee.rest.dto.AnnualBalanceLeaveDTO;
import com.company.eleave.employee.rest.dto.ApproverDTO;
import com.company.eleave.employee.rest.dto.EmployeeDTO;
import com.company.eleave.employee.rest.dto.LeaveTypeDTO;
import com.company.eleave.employee.service.ApproverService;
import com.company.eleave.employee.service.EmployeeService;
import com.company.eleave.leave.entity.AnnualBalanceLeave;
import com.company.eleave.leave.entity.LeaveType;

@RestController
@RequestMapping(value = "/employees")
public class EmployeeController {

  @Autowired
  EmployeeService employeeService;

  @Autowired
  ApproverService approverService;
  
  private ModelMapper mapper = new ModelMapper();

  private static final SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss a");

  @RequestMapping(method = RequestMethod.GET)
  public ResponseEntity<List<EmployeeDTO>> getAll() {
    List<EmployeeDTO> result = employeeService.getAll().stream().map(employee -> convertToDto(employee)).collect(Collectors.toList());

    return new ResponseEntity<List<EmployeeDTO>>(result, HttpStatus.OK);
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  public ResponseEntity<Employee> getById(@PathVariable("id") final Long employeeId) {
    final Employee result = employeeService.getById(employeeId);
    return result != null
        ? new ResponseEntity<Employee>(employeeService.getById(employeeId), HttpStatus.OK)
        : new ResponseEntity<Employee>(HttpStatus.NOT_FOUND);
  }

  @RequestMapping(method = RequestMethod.POST)
  public ResponseEntity<Long> create(@RequestBody Employee employee) {
    final long employeeId = employeeService.create(employee);

    return new ResponseEntity<Long>(employeeId, HttpStatus.CREATED);
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
  public ResponseEntity<Employee> update(final @PathVariable("id") Long employeeId, final Employee employee) {
    final Employee currentEmployee = employeeService.getById(employeeId);

    if (currentEmployee == null) {
      return new ResponseEntity<Employee>(HttpStatus.NOT_FOUND);
    }

    // TODO make some propagate methods
    currentEmployee.setEmail(employee.getEmail());
    currentEmployee.setFirstName(employee.getFirstName());
    currentEmployee.setLastName(employee.getLastName());

    employeeService.update(currentEmployee);

    return new ResponseEntity<Employee>(currentEmployee, HttpStatus.OK);
  }

  @RequestMapping(value = "/{id}/approver", method = RequestMethod.PUT)
  public ResponseEntity<Void> assignApprover(final @PathVariable("id") Long employeeId, @RequestBody ApproverDTO approverDTO) {
    final Employee currentEmployee = employeeService.getById(employeeId);
    if (currentEmployee == null) {
      return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
    }

    final Employee newApprover = employeeService.getById(approverDTO.getApproverId());
    if (newApprover == null) {
      return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
    }

    if (StringUtils.isNotBlank(approverDTO.getStartDate())
        || StringUtils.isNotBlank(approverDTO.getEndDate())) {
      return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
    }

    final Approver approver = new Approver();
    approver.setEmployee(currentEmployee);
    approver.setApprover(newApprover);
    try {
      approver.setStartDate(FORMATTER.parse(approverDTO.getStartDate()));
      approver.setEndDate(FORMATTER.parse(approverDTO.getEndDate()));
    } catch (ParseException e) {
      return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
    }

    approverService.assignApprover(approver);

    return new ResponseEntity<Void>(HttpStatus.OK);
  }

  @RequestMapping(value = "/{id}/approver/{approverId}", method = RequestMethod.DELETE)
  public ResponseEntity<Void> removeApproverForEmployee(final @PathVariable("id") Long employeeId) {
    final Approver approver = approverService.getById(employeeId);
    if (approver == null) {
      return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
    }

    approverService.removeApproverForEmployee(approver);

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

  private EmployeeDTO convertToDto(Employee employee) {
    EmployeeDTO employeeDto = mapper.map(employee, EmployeeDTO.class);
    employeeDto.setAnnualBalanceLeave(employee.getAnnualBalanceLeave().stream().map(annualBalance -> convertToDto(annualBalance)).collect(Collectors.toList()));
    return employeeDto;
  }
  
  private AnnualBalanceLeaveDTO convertToDto(AnnualBalanceLeave balanceLeave) {
    AnnualBalanceLeaveDTO annualBalanceDto = mapper.map(balanceLeave, AnnualBalanceLeaveDTO.class);
    annualBalanceDto.setLeaveType(mapper.map(balanceLeave.getLeaveType(), LeaveTypeDTO.class));
    return annualBalanceDto;
  }
}
