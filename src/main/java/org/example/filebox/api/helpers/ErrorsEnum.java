package org.example.filebox.api.helpers;

public enum ErrorsEnum {
    ERROR_REQUEST_PARAMETERS("Error in request parameters");

    private final String value;

    ErrorsEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
