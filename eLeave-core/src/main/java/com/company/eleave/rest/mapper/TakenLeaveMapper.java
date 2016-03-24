/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.eleave.rest.mapper;

import com.company.eleave.employee.entity.Employee;
import com.company.eleave.employee.repository.EmployeeRepository;
import com.company.eleave.leave.entity.AnnualBalanceLeave;
import com.company.eleave.leave.entity.LeaveStatus;
import com.company.eleave.leave.entity.TakenLeave;
import com.company.eleave.leave.repository.AnnualBalanceLeaveRepository;
import com.company.eleave.rest.dto.LeaveStatusDTO;
import com.company.eleave.rest.dto.TakenLeaveDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TakenLeaveMapper implements Mapper<TakenLeaveDTO, TakenLeave> {

    private final ModelMapper mapper = new ModelMapper();

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    AnnualBalanceLeaveRepository annualBalanceLeaveRepository;

    @Override
    public TakenLeaveDTO toDto(TakenLeave takenLeave) {
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        TakenLeaveDTO takenLeaveDTO = mapper.map(takenLeave, TakenLeaveDTO.class);

        takenLeaveDTO.setApproverId(takenLeave.getApprover().getId());
        takenLeaveDTO.setApproverName(takenLeave.getApprover().getFirstName() + " " + takenLeave.getApprover().getLastName());

        takenLeaveDTO.setAnnualBalanceLeaveId(takenLeave.getAnnualBalanceLeave().getId());
        takenLeaveDTO.setLeaveType(takenLeave.getAnnualBalanceLeave().getLeaveType().getLeaveTypeName());
        takenLeaveDTO.setLeaveTypeId(takenLeave.getAnnualBalanceLeave().getLeaveType().getId());

        LeaveStatusDTO leaveStatusDTO = new LeaveStatusDTO();
        if (takenLeave.getLeaveStatus() != null) {
            leaveStatusDTO.setComment(takenLeave.getLeaveStatus().getComment());
            leaveStatusDTO.setStatus(takenLeave.getLeaveStatus().getStatusName().toString());
        }

        takenLeaveDTO.setStatus(leaveStatusDTO);

        return takenLeaveDTO;
    }

    @Override
    public TakenLeave toEntity(TakenLeaveDTO takenLeaveDTO) {
        TakenLeave takenLeave = mapper.map(takenLeaveDTO, TakenLeave.class);

        LeaveStatus leaveStatus = new LeaveStatus();
        leaveStatus.setComment(takenLeaveDTO.getStatus().getComment());
        leaveStatus.setStatusName(takenLeaveDTO.getStatus().getStatus());
        takenLeave.setLeaveStatus(leaveStatus);

        AnnualBalanceLeave annualBalanceLeave = annualBalanceLeaveRepository.findOne(takenLeaveDTO.getAnnualBalanceLeaveId());
        takenLeave.setAnnualBalanceLeave(annualBalanceLeave);

        Employee approver = employeeRepository.findOne(takenLeaveDTO.getApproverId());
        takenLeave.setApprover(approver);

        return takenLeave;
    }

}
