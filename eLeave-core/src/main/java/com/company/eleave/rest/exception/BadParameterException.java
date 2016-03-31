package com.company.eleave.rest.exception;

public class BadParameterException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private String value;

    private String type;

    private String element;

    private ExceptionType exceptionType;
    
    private String message;

    public BadParameterException() {

    }

    private BadParameterException(final String value, String type, String element, ExceptionType exceptionType, String message) {
        this.value = value;
        this.type = type;
        this.element = element;
        this.exceptionType = exceptionType;
        this.message = message;
    }

    public String getValue() {
        return value;
    }

    public String getType() {
        return type;
    }

    public String getElement() {
        return element;
    }

    public ExceptionType getExceptionType() {
        return exceptionType;
    }
    
    public String getMessage() {
        return message;
    }

    public enum ExceptionType {
        BAD_VALUE, NOT_POSSIBLE_TO_REMOVE;
    }

    public static class BadParameterExceptionBuilder {

        private String value;

        private String type;

        private String element;

        private ExceptionType exceptionType;
        
        private String message;

        public BadParameterExceptionBuilder() {
        }

        public BadParameterExceptionBuilder withValue(String value) {
            this.value = value;
            return this;
        }

        public BadParameterExceptionBuilder withType(String type) {
            this.type = type;
            return this;
        }

        public BadParameterExceptionBuilder withElement(String element) {
            this.element = element;
            return this;
        }

        public BadParameterExceptionBuilder withExceptionType(ExceptionType exceptionType) {
            this.exceptionType = exceptionType;
            return this;
        }
        
        public BadParameterExceptionBuilder withMessage(String message) {
            this.message = message;
            return this;
        } 

        public BadParameterException createException() {
            return new BadParameterException(value, type, element, exceptionType, message);
        }
    }

}
