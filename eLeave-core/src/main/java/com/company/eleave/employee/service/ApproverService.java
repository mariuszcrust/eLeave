package com.company.eleave.employee.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.eleave.employee.entity.Approver;
import com.company.eleave.employee.repository.ApproverRepository;
import java.util.Date;
import java.util.List;

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

    public void removeApproverForEmployee(Long employeeId) {
        approverRepo.delete(getById(employeeId));

    }

    public List<Approver> getEmployeesAssignedToApprover(Long employeeId) {
        return approverRepo.getEmployeesAssignedToApprover(employeeId, new Date());
    }
}
