package org.example.filebox.api.helpers;

public enum ErrorsEnum {
    ERROR_REQUEST_PARAMETERS("Error in request parameters"),
    SESSION_WITH_ID_DOES_NOT_EXIST("Session with such ID does not exist"),
    LIFE_TIME_DOES_NOT_EXIST("Life time"),
    FILE_MISSING_OR_ERROR_GETTING_FILE("File missing or error getting file"),
    INVALID_FILE_SOURCE_TYPE("Invalid file source type");

    private final String value;

    ErrorsEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
