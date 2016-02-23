package com.company.eleave.leave.rest;

import com.company.eleave.leave.entity.LeaveType;
import com.company.eleave.leave.repository.LeaveTypeRepository;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Sebastian Szlachetka
 */
@RestController
@Transactional
@RequestMapping(path = "/leaveTypes", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
public class LeaveTypeController {

    private Logger LOGGER = Logger.getLogger(LeaveTypeController.class.getName());
    @Autowired
    private LeaveTypeRepository leaveTypeRepository;

    @Autowired
    private EntityManagerFactory emf;

    @RequestMapping(method = POST)
    public ResponseEntity<Long> createNewLeaveType(@RequestBody LeaveType aLeaveType) {
        LOGGER.log(Level.INFO, "Received leave type: {0}", aLeaveType.toString());
        final LeaveType leaveType = emf.createEntityManager().merge(aLeaveType);
        // final LeaveType leaveType = leaveTypeRepository.save(aLeaveType);
        LOGGER.log(Level.INFO, "Leave type stored with id: {0}", leaveType.getId());
        return new ResponseEntity<>(leaveType.getId(), HttpStatus.CREATED);
    }
}
