package com.teladoc.service;

import com.teladoc.controller.request.AppointmentRequest;
import com.teladoc.controller.request.UpdateAppointmentRequest;
import com.teladoc.model.exception.AppointmentNotFoundException;
import com.teladoc.model.exception.DoctorNotFoundException;
import com.teladoc.model.exception.InvalidAppointmentException;
import com.teladoc.model.exception.PatientNotFoundException;
import org.springframework.stereotype.Service;

@Service
public interface AppointmentService {

    Long bookAppointment(AppointmentRequest request) throws DoctorNotFoundException, PatientNotFoundException;

    void updateAppointment(Long doctorId, Long appointmentId, UpdateAppointmentRequest updateRequest) throws DoctorNotFoundException, AppointmentNotFoundException, InvalidAppointmentException, PatientNotFoundException;

    void deleteAppointment(Long appointmentId) throws AppointmentNotFoundException;
}
