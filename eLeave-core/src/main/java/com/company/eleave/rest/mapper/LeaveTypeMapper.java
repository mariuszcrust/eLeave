package com.company.eleave.rest.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.company.eleave.leave.entity.LeaveType;
import com.company.eleave.rest.dto.LeaveTypeDTO;
import javax.annotation.PostConstruct;
import org.modelmapper.PropertyMap;

@Component
public class LeaveTypeMapper implements Mapper<LeaveTypeDTO, LeaveType> {

    private ModelMapper mapper = new ModelMapper();

/*
    @PostConstruct
    public void before() {
        mapper.addMappings(new DtoToEntity());
        mapper.addMappings(new EntityToDTO());
    }
*/

    public LeaveTypeDTO toDto(LeaveType leaveType) {
        return mapper.map(leaveType, LeaveTypeDTO.class);
    }

    public LeaveType toEntity(LeaveTypeDTO leaveType) {
        return mapper.map(leaveType, LeaveType.class);
    }

/*
    static class DtoToEntity extends PropertyMap<LeaveTypeDTO, LeaveType> {

        @Override
        protected void configure() {
            System.out.println("DtoToEntity");
            map().setComment("dtoToEntity");
            map().setDefaultDaysAllowed(source.getDefaultDaysAllowed());
            map().setLeaveTypeName(source.getLeaveTypeName());
        }

    }

    static class EntityToDTO extends PropertyMap<LeaveType, LeaveTypeDTO> {

        @Override
        protected void configure() {
            System.out.println("EntityToDTO");
            map().setComment("EntityToDTO");
            map().setDefaultDaysAllowed(source.getDefaultDaysAllowed());
            map().setId(source.getId());
            map().setLeaveTypeName(source.getLeaveTypeName());
        }

    }
*/

}
