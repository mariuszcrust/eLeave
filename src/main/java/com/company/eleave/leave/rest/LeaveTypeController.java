package com.company.eleave.leave.rest;

import com.company.eleave.leave.entity.LeaveType;
import org.springframework.http.HttpStatus;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
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
@RequestMapping(path = "/leaveTypes", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
public class LeaveTypeController {
   
    @RequestMapping(method = POST)
    public ResponseEntity<Long> createNewLeaveType(@RequestBody LeaveType aLeaveType) {
        
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
