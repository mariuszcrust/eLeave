package com.company.eleave.rest.exception;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ElementNotFoundException.class)
    protected ResponseEntity<Object> notFoundException(ElementNotFoundException ex, WebRequest request) {
        ElemendNotFoundExceptionDTO exception = new ElemendNotFoundExceptionDTO();
        exception.setCode(ex.getCode());
        exception.setElementId(ex.getElementId());
        
        return handleExceptionInternal(ex, exception, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(BadParameterException.class)
    protected ResponseEntity<Object> badParameterException(BadParameterException ex, WebRequest request) {
        BadParameterExceptionDTO exception = new BadParameterExceptionDTO();
        exception.setCode(ex.getCode());
        exception.setMessage(ex.getMessage());
        exception.setValue(ex.getValue());
        
        return handleExceptionInternal(ex, exception, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    //Created because passing instance of exception send in response stacktrace ...
    class ElemendNotFoundExceptionDTO {

        private long elementId;

        private String code;

        public long getElementId() {
            return elementId;
        }

        public String getCode() {
            return code;
        }

        public void setElementId(long elementId) {
            this.elementId = elementId;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }

    class BadParameterExceptionDTO {

        private String value;

        private String code;

        private String message;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

    }

}
