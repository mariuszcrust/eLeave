package com.company.eleave.employee.rest.dto;

import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

import com.company.eleave.employee.entity.Employee;
import com.company.eleave.leave.entity.AnnualBalanceLeave;

public class EmployeeDTOBuilder {
	
	private static ModelMapper mapper = new ModelMapper();

	public static EmployeeDTO convertToDto(Employee employee) {
		EmployeeDTO employeeDto = mapper.map(employee, EmployeeDTO.class);
		employeeDto.setAnnualBalanceLeave(employee.getAnnualBalanceLeave().stream()
				.map(annualBalance -> convertToDto(annualBalance)).collect(Collectors.toList()));
		return employeeDto;
	}

	private static AnnualBalanceLeaveDTO convertToDto(AnnualBalanceLeave balanceLeave) {
		AnnualBalanceLeaveDTO annualBalanceDto = mapper.map(balanceLeave, AnnualBalanceLeaveDTO.class);
		annualBalanceDto.setLeaveType(mapper.map(balanceLeave.getLeaveType(), LeaveTypeDTO.class));
		return annualBalanceDto;
	}
}
