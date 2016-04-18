package com.company.eleave.employee.service;

import com.company.eleave.employee.entity.Approver;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.eleave.employee.entity.Employee;
import com.company.eleave.employee.repository.ApproverRepository;
import com.company.eleave.employee.repository.EmployeeRepository;
import com.company.eleave.leave.repository.AnnualBalanceLeaveRepository;
import com.company.eleave.leave.repository.TakenLeaveRepository;
import com.company.eleave.security.entity.User;
import com.company.eleave.security.entity.UserRole;
import com.company.eleave.security.repository.UserRepository;
import com.google.common.collect.Lists;
import java.util.Date;

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

    public List<Employee> getAll(boolean onlyActive) {
        return onlyActive == true ? Lists.newArrayList(employeeRepo.findAllActive()) : Lists.newArrayList(employeeRepo.findAll());
    }

    public Employee getById(Long employeeId) {
        return employeeRepo.findOne(employeeId);
    }
    
    public Employee getAllDetailsById(final Long employeeId) {
        Employee employee = employeeRepo.findByIdWithAccount(employeeId);
        return employee;
    }

    public void delete(Long employeeId) {
        final Approver approverForDeletedElement = approverRepository.getApproverByEmployee(employeeId);
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

        //final List<TakenLeave> leavesForEmployee = takenLeaveRepository.findTakenLeavesForEmployeeId(employeeId);
        //takenLeaveRepository.delete(leavesForEmployee);
        //final List<AnnualBalanceLeave> annualBalanceLeaves = annualBalanceLeaveRepository.findByEmployee(employeeId);
        //annualBalanceLeaveRepository.delete(annualBalanceLeaves);
        Employee employee = employeeRepo.findByIdWithAccount(employeeId);
        User user = employee.getUser();
        employee.setUser(null);
        employeeRepo.save(employee);
        userRepository.delete(user);
    }

    public void update(Employee currentEmployee) {
        employeeRepo.save(currentEmployee);
    }

    public long create(Employee employee) {
        final User user = new User();
        user.setUserName(employee.getFirstName() + "." + employee.getLastName());
        user.setPassword("password");
        //user.setActive(true);

        employee.setUser(user);

        return employeeRepo.save(employee).getId();
    }

    public List<Employee> getWithRole(UserRole.RoleName role) {
        return employeeRepo.getByRole(role);
    }
}
