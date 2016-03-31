package com.company.eleave.rest.exception;

public class ElementNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private long elementId;

    private String code;

    public ElementNotFoundException() {
    }

    public ElementNotFoundException(final Long id, final String code) {
        this.elementId = id;
        this.code = code;
    }

    public long getElementId() {
        return elementId;
    }

    public String getCode() {
        return code;
    }

}
