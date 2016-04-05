package com.company.eleave.rest.mapper;

import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.company.eleave.employee.entity.Employee;
import com.company.eleave.leave.entity.AnnualBalanceLeave;
import com.company.eleave.rest.dto.AnnualBalanceLeaveDTO;
import com.company.eleave.rest.dto.EmployeeAccountDTO;
import com.company.eleave.rest.dto.EmployeeDTO;
import com.company.eleave.rest.dto.UserDTO;

@Component
public class EmployeeMapper implements Mapper<EmployeeDTO, Employee> {

    private ModelMapper mapper = new ModelMapper();

    public EmployeeDTO toDto(Employee employee) {
        EmployeeDTO employeeDto = mapper.map(employee, EmployeeDTO.class);
        employeeDto.setAnnualBalanceLeave(employee.getAnnualBalanceLeave().stream()
                .map(annualBalance -> toDto(annualBalance)).collect(Collectors.toList()));
        
        return employeeDto;
    }

    public Employee toEntity(EmployeeDTO employeeDto) {
        Employee employee = mapper.map(employeeDto, Employee.class);
        return employee;
    }
    
    public EmployeeAccountDTO toEmployeeAccountDto(Employee employee) {
        EmployeeDTO employeeDto = mapper.map(employee, EmployeeDTO.class);
        employeeDto.setAnnualBalanceLeave(employee.getAnnualBalanceLeave().stream()
                .map(annualBalance -> toDto(annualBalance)).collect(Collectors.toList()));
        UserDTO userDTO = mapper.map(employee.getUser(), UserDTO.class);
        
        final EmployeeAccountDTO employeeAccountDTO = new EmployeeAccountDTO();
        employeeAccountDTO.setEmployee(employeeDto);
        employeeAccountDTO.setUserDTO(userDTO);
        
        return employeeAccountDTO;
    }

    private AnnualBalanceLeaveDTO toDto(AnnualBalanceLeave balanceLeave) {
        AnnualBalanceLeaveDTO annualBalanceDto = mapper.map(balanceLeave, AnnualBalanceLeaveDTO.class);
        annualBalanceDto.setLeaveTypeId(balanceLeave.getLeaveType().getId());
        annualBalanceDto.setLeaveTypeName(balanceLeave.getLeaveType().getLeaveTypeName());
        return annualBalanceDto;
    }
}
