package com.company.eleave.rest.mapper;

import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.company.eleave.employee.entity.Employee;
import com.company.eleave.leave.entity.AnnualBalanceLeave;
import com.company.eleave.rest.dto.AnnualBalanceLeaveDTO;
import com.company.eleave.rest.dto.EmployeeDTO;
import com.company.eleave.rest.dto.LeaveTypeDTO;

@Component
public class EmployeeMapper implements ModelMapping<EmployeeDTO, Employee> {

    private ModelMapper mapper = new ModelMapper();

    public EmployeeDTO toDto(Employee employee) {
        EmployeeDTO employeeDto = mapper.map(employee, EmployeeDTO.class);
        employeeDto.setAnnualBalanceLeave(employee.getAnnualBalanceLeave().stream()
                .map(annualBalance -> toDto(annualBalance)).collect(Collectors.toList()));
        return employeeDto;
    }

    public Employee toEntity(EmployeeDTO employeeDto) {
        Employee employee = mapper.map(employeeDto, Employee.class);

        //skip for now
        //employee.setAnnualBalanceLeave(employee.getAnnualBalanceLeave().stream()
        //        .map(annualBalance -> toDto(annualBalance)).collect(Collectors.toList()));
        return employee;
    }

    private AnnualBalanceLeaveDTO toDto(AnnualBalanceLeave balanceLeave) {
        AnnualBalanceLeaveDTO annualBalanceDto = mapper.map(balanceLeave, AnnualBalanceLeaveDTO.class);
        annualBalanceDto.setLeaveType(mapper.map(balanceLeave.getLeaveType(), LeaveTypeDTO.class));
        return annualBalanceDto;
    }
}
