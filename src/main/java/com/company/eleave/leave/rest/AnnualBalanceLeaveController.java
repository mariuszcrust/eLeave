package com.company.eleave.leave.rest;

import com.company.eleave.leave.entity.AnnualBalanceLeave;
import com.company.eleave.leave.service.AnnualBalanceService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
@RequestMapping(path = "/annualBalanceLeaves")
public class AnnualBalanceLeaveController {

  @Autowired
  AnnualBalanceService annualBalanceService;

  @RequestMapping(path = "/employee/{id}", method = GET)
  public ResponseEntity<List<AnnualBalanceLeave>> getLeaves(@PathVariable("id") long employeeId) {
    List<AnnualBalanceLeave> leavesForUser = annualBalanceService.getLeavesForUser(employeeId);

    return new ResponseEntity<List<AnnualBalanceLeave>>(leavesForUser, HttpStatus.OK);
  }

  public ResponseEntity<Void> addLeave() {
    return null;
  }

  public ResponseEntity<Void> removeLeave() {
    return null;
  }

  public ResponseEntity<Void> updateLeave() {
    return null;
  }
}
