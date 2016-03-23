package com.company.eleave.rest.exception;

import java.text.MessageFormat;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String MESSAGE_FORMAT_NOT_FOUND = "Element with id: {0} of type: {1} has not been found";

    private static final String MESSAGE_FORMAT_BAD_PARAMETER = "Element with value: {0} of type: {1} has invalid value. Root cause: {2}";

    private static final String MESSAGE_ELEMENT_CANNOT_BE_REMOVED = "Element with id: {0} cannot be removed because of db constraints";

    @ExceptionHandler(ElementNotFoundException.class)
    protected ResponseEntity<Object> notFoundException(ElementNotFoundException ex, WebRequest request) {
        return handleExceptionInternal(ex, MessageFormat.format(MESSAGE_FORMAT_NOT_FOUND, ex.getElementId().toString(), ex.getClazzType()), new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(BadParameterException.class)
    protected ResponseEntity<Object> badParameterException(BadParameterException ex, WebRequest request) {

        //fucked up, needs to be changed ...
        if (BadParameterException.ExceptionType.NOT_POSSIBLE_TO_REMOVE.equals(ex.getExceptionType())) {
            return handleExceptionInternal(ex, MessageFormat.format(MESSAGE_ELEMENT_CANNOT_BE_REMOVED, ex.getValue()), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
        }

        return handleExceptionInternal(ex, MessageFormat.format(MESSAGE_FORMAT_BAD_PARAMETER, ex.getValue(), ex.getType(), ex.getElement(), ex.getMessage()), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

}
