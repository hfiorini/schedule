package com.teladoc.model.exception;

import lombok.Getter;

@Getter
public class DoctorNotFoundException extends Exception {

    private final String message;

    public DoctorNotFoundException(String message) {
        this.message = message;
    }
}
