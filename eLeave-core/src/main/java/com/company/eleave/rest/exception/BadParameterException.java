package com.company.eleave.rest.exception;

import java.text.MessageFormat;

public class BadParameterException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private String value;

    private String code;

    private String message;

    public BadParameterException() {

    }

    private BadParameterException(final String value, String code, String message) {
        this.value = value;
        this.code = code;
        this.message = message;
    }

    public String getValue() {
        return value;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public static class BadParameterExceptionBuilder {

        private String value;

        private String code;

        private String message;

        public BadParameterExceptionBuilder() {
        }

        public BadParameterExceptionBuilder withValue(String value) {
            this.value = value;
            return this;
        }

        public BadParameterExceptionBuilder withCode(String code) {
            this.code = code;
            return this;
        }

        public BadParameterExceptionBuilder withMessage(String message, Object... parameters) {
            this.message = MessageFormat.format(message, parameters);
            return this;
        }

        public BadParameterException createException() {
            return new BadParameterException(value, code, message);
        }
    }

}
