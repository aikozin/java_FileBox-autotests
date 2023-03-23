package org.example.filebox.api.helpers;

public enum ErrorsEnum {
    ERROR_REQUEST_PARAMETERS("Error in request parameters"),
    SESSION_WITH_ID_DOES_NOT_EXIST("Session with such ID does not exist"),
    LIFE_TIME_DOES_NOT_EXIST("Life time");

    private final String value;

    ErrorsEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
