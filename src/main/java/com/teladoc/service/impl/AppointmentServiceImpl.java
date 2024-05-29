package com.teladoc.service.impl;

import com.teladoc.controller.request.AppointmentRequest;
import com.teladoc.controller.request.UpdateAppointmentRequest;
import com.teladoc.model.Appointment;
import com.teladoc.model.Doctor;
import com.teladoc.model.Patient;
import com.teladoc.model.exception.AppointmentNotFoundException;
import com.teladoc.model.exception.DoctorNotFoundException;
import com.teladoc.model.exception.InvalidAppointmentException;
import com.teladoc.model.exception.PatientNotFoundException;
import com.teladoc.repository.AppointmentRepository;
import com.teladoc.repository.DoctorRepository;
import com.teladoc.repository.PatientRepository;
import com.teladoc.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppointmentServiceImpl implements AppointmentService {
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private PatientRepository patientRepository;

    @Override
    public Long bookAppointment(AppointmentRequest request) throws DoctorNotFoundException, PatientNotFoundException {
        Appointment appointment = new Appointment();
        Doctor doctor = doctorRepository.findById(request.getDoctorId()).orElseThrow(() -> new DoctorNotFoundException("Doctor not found"));
        Patient patient = patientRepository.findById(request.getPatientId()).orElseThrow(() -> new PatientNotFoundException("Patient not found"));
        appointment.setDoctor(doctor);
        appointment.setPatient(patient);
        appointment.setStartTime(request.getAppointmentTime());
        appointment.setEndTime(request.getAppointmentTime().plusHours(1));
        Appointment newAppointment = appointmentRepository.save(appointment);
        return newAppointment.getId();
    }

    @Override
    public void updateAppointment(Long doctorId, Long appointmentId, UpdateAppointmentRequest updateRequest) throws DoctorNotFoundException, AppointmentNotFoundException, InvalidAppointmentException, PatientNotFoundException {

        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow(() -> new DoctorNotFoundException("Doctor not found"));


        Appointment appointment = appointmentRepository.findByIdAndDoctorId(doctor.getId(), appointmentId)
                .orElseThrow(() -> new AppointmentNotFoundException("Appointment not found"));


        if (!isValidAppointment(updateRequest)) {
            throw new InvalidAppointmentException("Invalid appointment details");
        }


        appointment.setStartTime(updateRequest.getAppointmentTime());
        appointment.setEndTime(updateRequest.getAppointmentTime().plusHours(1));
        Patient patient = patientRepository.findById(updateRequest.getPatientId()).orElseThrow(() -> new PatientNotFoundException("Doctor not found"));
        appointment.setPatient(patient);


        appointmentRepository.save(appointment);
    }

    @Override
    public void deleteAppointment(Long appointmentId) throws AppointmentNotFoundException {

        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new AppointmentNotFoundException("Appointment not found"));

        appointmentRepository.delete(appointment);
    }

    private boolean isValidAppointment(UpdateAppointmentRequest updateRequest) {

        return updateRequest.getDoctorId() != null && updateRequest.getPatientId() != null && updateRequest.getAppointmentTime() != null; // Placeholder validation for demonstration
    }

}
