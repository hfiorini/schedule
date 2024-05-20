package com.teladoc.model.exception;

import lombok.Getter;

@Getter
public class InvalidAppointmentException extends Exception{

    private final String message;

    public InvalidAppointmentException(String message) {
        this.message = message;
    }
}
