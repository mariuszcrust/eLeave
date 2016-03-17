package com.company.eleave.leave.rest;

import com.company.eleave.leave.entity.TakenLeave;
import com.company.eleave.leave.service.TakenLeaveService;
import com.company.eleave.rest.dto.TakenLeaveDTO;
import com.company.eleave.rest.exception.ElementNotFoundException;
import com.company.eleave.rest.exception.ExceptionElementType;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/takenLeaves")
public class TakenLeaveController {

    @Autowired
    TakenLeaveService takenLeaveService;

    @RequestMapping(method = POST)
    public ResponseEntity<Long> createNewLeave(@RequestBody TakenLeaveDTO takenLeave) {

        return null;
    }

    @RequestMapping(path = "/{id}/status", method = PUT)
    public ResponseEntity<Void> updateStatusLeave(@PathVariable("id") final Long takenLeaveId, @RequestParam(required = true, value = "sortby") String sortBy) {
        final TakenLeave takenLeave = takenLeaveService.getById(takenLeaveId);
        if (takenLeave == null) {
            throw new ElementNotFoundException(takenLeaveId, ExceptionElementType.TAKEN_LEAVE);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(path = "{id}", method = PUT)
    public ResponseEntity<Void> updateLeave(@PathVariable("id") final long takenLeaveId) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(path = "/{id}", method = DELETE)
    public ResponseEntity<Void> deleteLeave(@PathVariable("id") final Long takenLeaveId) {

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(path = "/{employeeId}", method = GET)
    public ResponseEntity<List<TakenLeaveDTO>> getAllLeavesForEmployee(@PathVariable("employeeId") final Long employeeId) {

        return null;
    }

    @RequestMapping(path = "/{employeeId}", method = POST)
    public ResponseEntity<List<TakenLeaveDTO>> getAllLeavesForApprover(@PathVariable("id") final Long employeeId) {

        return null;
    }

}
