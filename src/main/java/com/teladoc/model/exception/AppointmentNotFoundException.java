package com.teladoc.model.exception;

import lombok.Getter;

@Getter
public class AppointmentNotFoundException extends Exception{

    private final String message;

    public AppointmentNotFoundException(String message) {
        this.message = message;
    }
}
