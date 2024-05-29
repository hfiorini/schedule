package com.teladoc.model.exception;

import lombok.Getter;

@Getter
public class PatientNotFoundException extends Exception {

    private final String message;

    public PatientNotFoundException(String message) {
        this.message = message;
    }
}
