package com.company.eleave.employee.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.eleave.employee.entity.Approver;
import com.company.eleave.employee.entity.Employee;
import com.company.eleave.employee.repository.ApproverRepository;
import com.company.eleave.employee.repository.EmployeeRepository;
import com.google.common.collect.Lists;

@Service("approverService")
@Transactional
public class ApproverService {

	@Autowired
	private ApproverRepository approverRepo;

	public void assignApprover(final Approver approver) {
		approverRepo.save(approver);
	}

	public Approver getById(Long employeeId) {
		return approverRepo.findOne(employeeId);
	}

	public void removeApproverForEmployee(Approver approver) {
		approverRepo.delete(approver);
		
	}
}
