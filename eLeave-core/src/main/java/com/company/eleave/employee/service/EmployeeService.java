package com.company.eleave.employee.service;

import com.company.eleave.employee.entity.Approver;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.eleave.employee.entity.Employee;
import com.company.eleave.employee.repository.ApproverRepository;
import com.company.eleave.employee.repository.EmployeeRepository;
import com.company.eleave.leave.entity.AnnualBalanceLeave;
import com.company.eleave.leave.entity.TakenLeave;
import com.company.eleave.leave.repository.AnnualBalanceLeaveRepository;
import com.company.eleave.leave.repository.TakenLeaveRepository;
import com.company.eleave.security.entity.User;
import com.company.eleave.security.repository.UserRepository;
import com.google.common.collect.Lists;
import java.util.Date;
import java.util.Iterator;

@Service("employeeService")
@Transactional
public class EmployeeService {
    
    @Autowired
    private EmployeeRepository employeeRepo;
    
    @Autowired
    private TakenLeaveRepository takenLeaveRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private ApproverRepository approverRepository;
    
    @Autowired
    private AnnualBalanceLeaveRepository annualBalanceLeaveRepository;
    
    public List<Employee> getAll() {
        return Lists.newArrayList(employeeRepo.findAll());
    }
    
    public Employee getById(Long employeeId) {
        return employeeRepo.findOne(employeeId);
    }
    
    public void delete(Long employeeId) {
        final Approver approverForDeletedElement = approverRepository.findOne(employeeId);
        final List<Approver> employeesAsignedToBeDeletedElement = approverRepository.getEmployeesAssignedToApprover(employeeId);
        final Date currentDate = new Date();
        for (final Approver approver : employeesAsignedToBeDeletedElement) {
            approver.setEndDate(currentDate);
            approverRepository.save(approver);
            
            Approver newApprover = new Approver();
            newApprover.setStartDate(currentDate);
            newApprover.setEmployee(approver.getEmployee());
            newApprover.setApprover(approverForDeletedElement.getApprover());
            
            approverRepository.save(newApprover);
        }
        
        final List<TakenLeave> leavesForEmployee = takenLeaveRepository.findTakenLeavesForEmployeeId(employeeId);
        takenLeaveRepository.delete(leavesForEmployee);
        
        final List<AnnualBalanceLeave> annualBalanceLeaves = annualBalanceLeaveRepository.findByEmployee(employeeId);
        annualBalanceLeaveRepository.delete(annualBalanceLeaves);
        
        employeeRepo.delete(employeeId);
    }
    
    public void update(Employee currentEmployee) {
        employeeRepo.save(currentEmployee);
    }
    
    public long create(Employee employee) {
        final User user = new User();
        user.setUserName(employee.getFirstName() + "." + employee.getLastName());
        user.setPassword("password");
        
        employee.setUser(user);
        
        return employeeRepo.save(employee).getId();
    }
}
