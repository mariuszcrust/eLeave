package com.company.eleave.leave.rest;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.company.eleave.leave.entity.LeaveType;
import com.company.eleave.leave.service.LeaveTypeService;
import com.company.eleave.rest.dto.LeaveTypeDTO;
import com.company.eleave.rest.exception.ElementNotFoundException;
import com.company.eleave.rest.exception.ExceptionElementType;
import com.company.eleave.rest.mapper.LeaveTypeMapper;

/**
 *
 * @author Sebastian Szlachetka
 */
@RestController
@RequestMapping(path = "/leaveTypes")
public class LeaveTypeController {

  private Logger LOGGER = Logger.getLogger(LeaveTypeController.class.getName());

  @Autowired
  private LeaveTypeService leaveTypeService;

  @Autowired
  private LeaveTypeMapper mapper;

  @RequestMapping(method = RequestMethod.GET)
  public ResponseEntity<List<LeaveTypeDTO>> getAll() {
    List<LeaveTypeDTO> leaveTypes = leaveTypeService.getAll().stream().map(employee -> mapper.toDto(employee)).collect(Collectors.toList());;

    return new ResponseEntity<List<LeaveTypeDTO>>(leaveTypes, HttpStatus.OK);
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  public ResponseEntity<LeaveTypeDTO> getById(@PathVariable("id") final Long leaveTypeId) {
    LeaveType leaveType = leaveTypeService.getById(leaveTypeId);
    if (leaveType == null) {
      throw new ElementNotFoundException(leaveTypeId, ExceptionElementType.LEAVE_TYPE);
    }

    return new ResponseEntity<LeaveTypeDTO>(mapper.toDto(leaveType), HttpStatus.OK);
  }

  @RequestMapping(method = POST)
  public ResponseEntity<Long> createNewLeaveType(@RequestBody LeaveTypeDTO leaveType) {
    LOGGER.log(Level.INFO, "Received leave type: {0}", leaveType.toString());
    final Long leaveTypeId = leaveTypeService.createNewLeaveType(mapper.toEntity(leaveType));
    LOGGER.log(Level.INFO, "Leave type stored with id: {0}", leaveTypeId);
    return new ResponseEntity<>(leaveTypeId, HttpStatus.CREATED);
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
  public ResponseEntity<Void> delete(@PathVariable("id") final Long leaveTypeId) {
    final LeaveType leaveType = leaveTypeService.getById(leaveTypeId);

    if (leaveType == null) {
      throw new ElementNotFoundException(leaveTypeId, ExceptionElementType.LEAVE_TYPE);
    }

    leaveTypeService.delete(leaveTypeId);
    return new ResponseEntity<Void>(HttpStatus.OK);
  }

}
