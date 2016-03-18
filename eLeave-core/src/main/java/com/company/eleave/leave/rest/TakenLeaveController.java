package com.company.eleave.leave.rest;

import com.company.eleave.employee.entity.Employee;
import com.company.eleave.employee.service.EmployeeService;
import com.company.eleave.leave.entity.LeaveStatus;
import com.company.eleave.leave.entity.TakenLeave;
import com.company.eleave.leave.service.TakenLeaveService;
import com.company.eleave.rest.dto.LeaveStatusDTO;
import com.company.eleave.rest.dto.TakenLeaveDTO;
import com.company.eleave.rest.exception.BadParameterException;
import com.company.eleave.rest.exception.ElementNotFoundException;
import com.company.eleave.rest.exception.ExceptionElementType;
import com.company.eleave.rest.exception.ExceptionParameterType;
import com.company.eleave.rest.mapper.LeaveStatusMapper;
import com.company.eleave.rest.mapper.TakenLeaveMapper;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping(path = "/takenLeaves")
public class TakenLeaveController {

    @Autowired
    TakenLeaveService takenLeaveService;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    TakenLeaveMapper takenLeaveMapper;

    @Autowired
    LeaveStatusMapper leaveStatusMapper;

    @RequestMapping(method = POST)
    public ResponseEntity<Long> createNewLeave(@RequestBody TakenLeaveDTO takenLeaveDTO) {
        final long takenLeaveId = takenLeaveService.create(takenLeaveMapper.toEntity(takenLeaveDTO));

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(UriComponentsBuilder.fromPath("/takenLeaves/{id}").buildAndExpand(takenLeaveId).toUri());

        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @RequestMapping(path = "/{id}/status", method = PUT)
    public ResponseEntity<Void> updateStatusLeave(@PathVariable("id") final Long takenLeaveId, @RequestBody LeaveStatusDTO leaveStatusDTO) {
        final TakenLeave takenLeave = takenLeaveService.getById(takenLeaveId);
        if (takenLeave == null) {
            throw new ElementNotFoundException(takenLeaveId, ExceptionElementType.TAKEN_LEAVE);
        }

        if (!LeaveStatus.StatusName.contains(leaveStatusDTO.getStatus())) {
            throw new BadParameterException(leaveStatusDTO.getStatus(), ExceptionParameterType.ANNUAL_BALANCE_LEAVE_TYPE_STATUS.getName(), "");
        }

        takenLeave.setLeaveStatus(leaveStatusMapper.toEntity(leaveStatusDTO));
        takenLeaveService.update(takenLeave);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    //do we need it really ?
    //@RequestMapping(path = "{id}", method = PUT)
    //public ResponseEntity<Void> updateLeave(@PathVariable("id") final long takenLeaveId) {
    //    return new ResponseEntity<>(HttpStatus.OK);
    //}
    @RequestMapping(path = "/{id}", method = DELETE)
    public ResponseEntity<Void> deleteLeave(@PathVariable("id") final Long takenLeaveId) {
        final TakenLeave takenLeave = takenLeaveService.getById(takenLeaveId);
        if (takenLeave == null) {
            throw new ElementNotFoundException(takenLeaveId, ExceptionElementType.TAKEN_LEAVE);
        }

        takenLeaveService.delete(takenLeave);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(path = "/employee/{employeeId}", method = GET)
    public ResponseEntity<List<TakenLeaveDTO>> getAllLeavesForEmployee(@PathVariable("employeeId") final Long employeeId) {
        final Employee employee = employeeService.getById(employeeId);
        if (employee == null) {
            throw new ElementNotFoundException(employeeId, ExceptionElementType.EMPLOYEE);
        }

        List<TakenLeaveDTO> takenLeavesForEmployee = takenLeaveService.findTakenLeavesByEmployeeId(employeeId).stream().map(takenLeave -> takenLeaveMapper.toDto(takenLeave)).collect(Collectors.toList());

        return new ResponseEntity<>(takenLeavesForEmployee, HttpStatus.OK);
    }

    @RequestMapping(path = "/approver/{approverId}", method = GET)
    public ResponseEntity<List<TakenLeaveDTO>> getAllLeavesForApprover(@PathVariable("approverId") final Long approverId) {
        final Employee employee = employeeService.getById(approverId);
        if (employee == null) {
            throw new ElementNotFoundException(approverId, ExceptionElementType.EMPLOYEE);
        }
        
        List<TakenLeaveDTO> takenLeavesForApprover = takenLeaveService.findTakenLeavesByApproverId(approverId).stream().map(takenLeave -> takenLeaveMapper.toDto(takenLeave)).collect(Collectors.toList());
        
        return new ResponseEntity<>(takenLeavesForApprover, HttpStatus.OK);
    }

}
