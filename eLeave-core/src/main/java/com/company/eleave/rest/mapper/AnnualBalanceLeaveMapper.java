package com.company.eleave.rest.mapper;

import com.company.eleave.leave.entity.AnnualBalanceLeave;
import com.company.eleave.rest.dto.AnnualBalanceLeaveDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class AnnualBalanceLeaveMapper implements ModelMapping<AnnualBalanceLeaveDTO, AnnualBalanceLeave> {

    private ModelMapper mapper = new ModelMapper();

    @Override
    public AnnualBalanceLeaveDTO toDto(AnnualBalanceLeave e) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public AnnualBalanceLeave toEntity(AnnualBalanceLeaveDTO d) {
        return null;
    }

}
