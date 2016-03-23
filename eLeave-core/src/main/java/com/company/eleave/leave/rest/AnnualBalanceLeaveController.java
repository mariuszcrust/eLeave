package com.company.eleave.leave.rest;

import com.company.eleave.employee.entity.Employee;
import com.company.eleave.employee.service.EmployeeService;
import com.company.eleave.leave.entity.AnnualBalanceLeave;
import com.company.eleave.leave.service.AnnualBalanceService;
import com.company.eleave.rest.dto.AnnualBalanceLeaveDTO;
import com.company.eleave.rest.exception.ElementNotFoundException;
import com.company.eleave.rest.exception.ExceptionElementType;
import com.company.eleave.rest.mapper.AnnualBalanceLeaveMapper;
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
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

/**
 *
 * @author Sebastian Szlachetka
 */
@RestController
@RequestMapping(path = "/annualBalanceLeaves")
public class AnnualBalanceLeaveController {

    @Autowired
    AnnualBalanceService annualBalanceService;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    AnnualBalanceLeaveMapper mapper;

    @RequestMapping(method = GET)
    public ResponseEntity<List<AnnualBalanceLeaveDTO>> getAllLeaves() {
        List<AnnualBalanceLeave> allLeaves = annualBalanceService.getAllLeaves();
        List<AnnualBalanceLeaveDTO> annualBalanceLeaves = annualBalanceService.getAllLeaves().stream().map(annualBalanceLeave -> mapper.toDto(annualBalanceLeave)).collect(Collectors.toList());

        return new ResponseEntity<>(annualBalanceLeaves, HttpStatus.OK);
    }

    @RequestMapping(path = "/employee/{id}", method = RequestMethod.GET)
    public ResponseEntity<List<AnnualBalanceLeaveDTO>> getLeavesForEmployee(@PathVariable("id") long employeeId) {
        if (employeeService.getById(employeeId) == null) {
            throw new ElementNotFoundException(employeeId, ExceptionElementType.EMPLOYEE);
        }

        List<AnnualBalanceLeave> leavesForUser = annualBalanceService.getLeavesForUser(employeeId);

        return new ResponseEntity<>(leavesForUser.stream().map(annualBalanceLeave -> mapper.toDto(annualBalanceLeave)).collect(Collectors.toList()), HttpStatus.OK);
    }

    //TODO - needs to be checked what if we wane to add two types of the same annual type
    @RequestMapping(path = "/employee/{id}", method = RequestMethod.POST)
    public ResponseEntity<Void> addLeaveForEmployee(@PathVariable("id") long employeeId, final @RequestBody AnnualBalanceLeaveDTO annualBalanceLeaveDTO) {
        final Employee employee = employeeService.getById(employeeId);
        if (employee == null) {
            throw new ElementNotFoundException(employeeId, ExceptionElementType.EMPLOYEE);
        }

        final AnnualBalanceLeave annualBalanceLeave = mapper.toEntity(annualBalanceLeaveDTO);
        annualBalanceLeave.setEmployee(employee);

        final long annualBalanceId = annualBalanceService.createLeave(annualBalanceLeave);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(UriComponentsBuilder.fromPath("/annualBalanceLeaves/{id}").buildAndExpand(annualBalanceId).toUri());

        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @RequestMapping(path = "/employee/{id}/leave/{leaveId}", method = DELETE)
    public ResponseEntity<Void> deleteLeaveForEmployeeByLeaveId(@PathVariable("id") long employeeId, @PathVariable("leaveId") long leaveId) {
        final Employee employee = employeeService.getById(employeeId);
        if (employee == null) {
            throw new ElementNotFoundException(employeeId, ExceptionElementType.EMPLOYEE);
        }

        annualBalanceService.deleteLeave(leaveId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
