package com.company.eleave.leave.rest;

import com.company.eleave.employee.entity.Employee;
import com.company.eleave.leave.entity.AnnualBalanceLeave;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Sebastian Szlachetka
 */
@RestController
@RequestMapping(path = "/annualBalanceLeaves", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
public class AnnualBalanceLeaveController {

    @RequestMapping(path = "/employee/{id}", method = GET)
    public ResponseEntity<List<AnnualBalanceLeave>> getAnnualBalanceLeavesForEmployee(@PathVariable("id") long employeeId) {
        
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
