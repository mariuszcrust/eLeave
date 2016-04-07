/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.eleave.leave.rest;

import com.company.eleave.leave.entity.Holiday;
import com.company.eleave.leave.service.HolidayService;
import com.company.eleave.rest.dto.HolidayDTO;
import com.company.eleave.rest.exception.ElementNotFoundException;
import com.company.eleave.rest.exception.ErrorCode;
import com.company.eleave.rest.mapper.HolidayMapper;
import com.google.common.annotations.VisibleForTesting;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

/**
 *
 * @author mdaniel
 */
@RestController
@RequestMapping(path = "/holidays")
public class HolidayController {

    @Autowired
    private HolidayService holidayService;

    @Autowired
    private HolidayMapper mapper;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<HolidayDTO>> getAll() {
        List<HolidayDTO> holidays = holidayService.getAll().stream().map(holiday -> mapper.toDto(holiday)).collect(Collectors.toList());

        return new ResponseEntity<>(holidays, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<HolidayDTO> getById(@PathVariable("id") final Long holidayId) {
        Holiday holiday = holidayService.getById(holidayId);
        if (holiday == null) {
            throw new ElementNotFoundException(holidayId, ErrorCode.LEAVE_TYPE_NOT_FOUND.getCode());
        }

        return new ResponseEntity<>(mapper.toDto(holiday), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Long> createNew(@RequestBody HolidayDTO holiday) {
        final long holidayId = holidayService.createNew(mapper.toEntity(holiday));

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(UriComponentsBuilder.fromPath("/leaveTypes/{id}").buildAndExpand(holidayId).toUri());

        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<HolidayDTO> update(@PathVariable("id") final long holidayId, final @RequestBody HolidayDTO holidayDTO) {
        Holiday holiday = holidayService.getById(holidayId);
        if (holiday == null) {
            throw new ElementNotFoundException(holidayId, ErrorCode.LEAVE_TYPE_NOT_FOUND.getCode());
        }

        holiday.setName(holidayDTO.getName());
        holiday.setComment(holidayDTO.getComment());
        holiday.setDate(holiday.getDate());
        holiday.setYear(holiday.getYear());
        holiday.setMovable(holiday.isMovable());

        holidayService.update(holiday);

        return new ResponseEntity<>(mapper.toDto(holiday), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable("id") final Long holidayId) {
        final Holiday holiday = holidayService.getById(holidayId);

        if (holiday == null) {
            throw new ElementNotFoundException(holidayId, ErrorCode.LEAVE_TYPE_NOT_FOUND.getCode());
        }

        holidayService.delete(holidayId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @VisibleForTesting
    public void setHolidayService(final HolidayService holidayService) {
        this.holidayService = holidayService;
    }
    
    @VisibleForTesting
    public void setHolidayMapper(final HolidayMapper holidayMapper) {
        this.mapper = holidayMapper;
    }

}
