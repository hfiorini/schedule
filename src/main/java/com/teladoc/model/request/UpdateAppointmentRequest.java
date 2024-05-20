package com.teladoc.model.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class UpdateAppointmentRequest {
    private Long doctorId;
    private Long patientId;
    private LocalDateTime appointmentTime;
}
