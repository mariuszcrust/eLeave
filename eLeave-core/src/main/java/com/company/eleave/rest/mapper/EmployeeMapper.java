package com.company.eleave.rest.mapper;

import com.company.eleave.employee.entity.Approver;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.company.eleave.employee.entity.Employee;
import com.company.eleave.employee.repository.ApproverRepository;
import com.company.eleave.leave.entity.AnnualBalanceLeave;
import com.company.eleave.rest.dto.AnnualBalanceLeaveDTO;
import com.company.eleave.rest.dto.EmployeeAccountDTO;
import com.company.eleave.rest.dto.EmployeeDTO;
import com.company.eleave.rest.dto.UserDTO;
import com.company.eleave.rest.dto.UserRoleDTO;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

@Component
public class EmployeeMapper{

    private ModelMapper mapper = new ModelMapper();
    
    @Autowired
    ApproverRepository approverRepository;
    
    @Autowired
    RoleMapper roleMapper;

    public EmployeeDTO toBasicDto(Employee employee) {
        EmployeeDTO employeeDto = mapper.map(employee, EmployeeDTO.class);
        
        return employeeDto;
    }
    
    public EmployeeDTO toDetailsDto(Employee employee) {
        EmployeeDTO employeeDto = mapper.map(employee, EmployeeDTO.class);
        
        final Date currentDate = new Date();
        Approver approverForEmployee = approverRepository.getApproverForEmployee(employee.getId(), currentDate);
        employeeDto.setApproverId(approverForEmployee.getApprover().getId());
        
        employeeDto.setAnnualBalanceLeaves(employee.getAnnualBalanceLeave().stream().map(annualBalance -> annualBalanceToDto(annualBalance)).collect(Collectors.toList()));
        employeeDto.setRoles(employee.getUser().getUserRoles().stream().map(role -> roleMapper.toDto(role)).collect(Collectors.toList()));
        
        return employeeDto;
    }

    public Employee toEntity(EmployeeDTO employeeDto) {
        Employee employee = mapper.map(employeeDto, Employee.class);
        return employee;
    }
    
    public EmployeeAccountDTO toEmployeeAccountDto(Employee employee) {
        EmployeeDTO employeeDto = mapper.map(employee, EmployeeDTO.class);
        employeeDto.setAnnualBalanceLeaves(employee.getAnnualBalanceLeave().stream()
                .map(annualBalance -> annualBalanceToDto(annualBalance)).collect(Collectors.toList()));
        UserDTO userDTO = mapper.map(employee.getUser(), UserDTO.class);
        
        final EmployeeAccountDTO employeeAccountDTO = new EmployeeAccountDTO();
        employeeAccountDTO.setEmployee(employeeDto);
        employeeAccountDTO.setUserDTO(userDTO);
        
        return employeeAccountDTO;
    }

    private AnnualBalanceLeaveDTO annualBalanceToDto(AnnualBalanceLeave balanceLeave) {
        AnnualBalanceLeaveDTO annualBalanceDto = mapper.map(balanceLeave, AnnualBalanceLeaveDTO.class);
        annualBalanceDto.setLeaveTypeId(balanceLeave.getLeaveType().getId());
        annualBalanceDto.setLeaveTypeName(balanceLeave.getLeaveType().getLeaveTypeName());
        return annualBalanceDto;
    }
}
