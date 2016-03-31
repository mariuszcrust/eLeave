package com.company.eleave.leave.rest;

import com.company.eleave.leave.entity.LeaveType;
import com.company.eleave.leave.service.LeaveTypeService;
import com.company.eleave.rest.dto.LeaveTypeDTO;
import com.company.eleave.rest.exception.BadParameterException;
import com.company.eleave.rest.exception.ElementNotFoundException;
import com.company.eleave.rest.exception.ErrorCode;
import com.company.eleave.rest.exception.ExceptionMessage;
import com.company.eleave.rest.mapper.LeaveTypeMapper;
import com.google.common.annotations.VisibleForTesting;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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

        return new ResponseEntity<>(leaveTypes, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<LeaveTypeDTO> getById(@PathVariable("id") final Long leaveTypeId) {
        LeaveType leaveType = leaveTypeService.getById(leaveTypeId);
        if (leaveType == null) {
            throw new ElementNotFoundException(leaveTypeId, ErrorCode.LEAVE_TYPE_NOT_FOUND.getCode());
        }

        return new ResponseEntity<>(mapper.toDto(leaveType), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Long> createNewLeaveType(@RequestBody LeaveTypeDTO leaveType) {
        LOGGER.log(Level.INFO, "Received leave type: {0}", leaveType.toString());

        final Long leaveTypeId = leaveTypeService.createNewLeaveType(mapper.toEntity(leaveType));

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(UriComponentsBuilder.fromPath("/leaveTypes/{id}").buildAndExpand(leaveTypeId).toUri());

        LOGGER.log(Level.INFO, "Leave type stored with id: {0}", leaveTypeId);
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<LeaveTypeDTO> updateLeaveType(@PathVariable("id") final Long leaveTypeId, final @RequestBody LeaveTypeDTO leaveTypeDTO) {
        LeaveType leaveType = leaveTypeService.getById(leaveTypeId);
        if (leaveType == null) {
            throw new ElementNotFoundException(leaveTypeId, ErrorCode.LEAVE_TYPE_NOT_FOUND.getCode());
        }

        leaveType.setComment(leaveTypeDTO.getComment());
        leaveType.setDefaultDaysAllowed(leaveTypeDTO.getDefaultDaysAllowed());
        leaveType.setLeaveTypeName(leaveTypeDTO.getLeaveTypeName());

        leaveTypeService.update(leaveType);

        return new ResponseEntity<>(mapper.toDto(leaveType), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable("id") final Long leaveTypeId) {
        final LeaveType leaveType = leaveTypeService.getById(leaveTypeId);

        if (leaveType == null) {
            throw new ElementNotFoundException(leaveTypeId, ErrorCode.LEAVE_TYPE_NOT_FOUND.getCode());
        }

        if (leaveTypeService.isLeaveTypeAssignedToAnyLeave(leaveTypeId)) {
            throw new BadParameterException.BadParameterExceptionBuilder()
                    .withValue(leaveTypeId.toString()).withCode(ErrorCode.LEAVE_TYPE_CANNOT_BE_REMOVED_DB_CONSTRAINTS.getCode())
                    .withMessage(ExceptionMessage.MESSAGE_ELEMENT_CANNOT_BE_REMOVED, leaveTypeId.toString()).createException();
        }

        leaveTypeService.delete(leaveTypeId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @VisibleForTesting
    public void setLeaveTypeService(final LeaveTypeService leaveTypeService) {
        this.leaveTypeService = leaveTypeService;
    }
    
    @VisibleForTesting
    public void setLeaveTypeMapper(final LeaveTypeMapper leaveTypeMapper) {
        this.mapper = leaveTypeMapper;
    }

}
