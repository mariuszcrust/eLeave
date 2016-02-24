package com.company.eleave.leave.rest;

import com.company.eleave.leave.entity.LeaveType;
import com.company.eleave.leave.service.LeaveTypeService;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import org.springframework.web.bind.annotation.RestController;

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

    @RequestMapping(method = POST)
    public ResponseEntity<Long> createNewLeaveType(@RequestBody LeaveType aLeaveType) {
        LOGGER.log(Level.INFO, "Received leave type: {0}", aLeaveType.toString());
        final Long leaveTypeId = leaveTypeService.createNewLeaveType(aLeaveType);
        LOGGER.log(Level.INFO, "Leave type stored with id: {0}", leaveTypeId);
        return new ResponseEntity<>(leaveTypeId, HttpStatus.CREATED);
    }
}
