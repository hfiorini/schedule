package com.teladoc.service;

import com.teladoc.model.Appointment;
import com.teladoc.model.Doctor;
import com.teladoc.model.Patient;
import com.teladoc.model.exception.AppointmentNotFoundException;
import com.teladoc.model.exception.DoctorNotFoundException;
import com.teladoc.model.exception.PatientNotFoundException;
import com.teladoc.model.request.AppointmentRequest;
import com.teladoc.model.request.UpdateAppointmentRequest;
import com.teladoc.repository.AppointmentRepository;
import com.teladoc.repository.DoctorRepository;
import com.teladoc.repository.PatientRepository;
import com.teladoc.service.impl.AppointmentServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class AppointmentServiceTest {

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private DoctorRepository doctorRepository;

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private AppointmentServiceImpl appointmentService;

    @Test
    void bookAppointment_Success() {

        Doctor mockDoctor = new Doctor();
        mockDoctor.setId(1L);
        when(doctorRepository.findById(any())).thenReturn(Optional.of(mockDoctor));

        Patient mockPatient = new Patient();
        mockPatient.setId(1L);
        when(patientRepository.findById(any())).thenReturn(Optional.of(mockPatient));

        Appointment appointment = new Appointment();
        appointment.setId(1L);
        when(appointmentRepository.save(any())).thenReturn(appointment);

        AppointmentRequest request = new AppointmentRequest();
        request.setDoctorId(1L);
        request.setPatientId(1L);
        request.setAppointmentTime(LocalDateTime.now());
        Long appointmentId = null;
        try {
            appointmentId = appointmentService.bookAppointment(request);
        } catch (DoctorNotFoundException | PatientNotFoundException e) {
            throw new RuntimeException(e);
        }

        assertNotNull(appointmentId);
    }

    @Test
    void bookAppointment_DoctorNotFound() {

        when(doctorRepository.findById(any())).thenReturn(Optional.empty());

        AppointmentRequest request = new AppointmentRequest();
        request.setDoctorId(1L);
        request.setPatientId(1L);
        request.setAppointmentTime(LocalDateTime.now());

        assertThrows(DoctorNotFoundException.class, () -> appointmentService.bookAppointment(request));
    }

    @Test
    void bookAppointment_PatientNotFound() {
        Doctor mockDoctor = new Doctor();
        mockDoctor.setId(1L);
        when(doctorRepository.findById(any())).thenReturn(Optional.of(mockDoctor));

        when(patientRepository.findById(any())).thenReturn(Optional.empty());


        AppointmentRequest request = new AppointmentRequest();
        request.setDoctorId(1L);
        request.setPatientId(1L);
        request.setAppointmentTime(LocalDateTime.now());

        assertThrows(PatientNotFoundException.class, () -> appointmentService.bookAppointment(request));
    }


    @Test
    void updateAppointment_Success() {

        Doctor mockDoctor = new Doctor();
        mockDoctor.setId(1L);
        when(doctorRepository.findById(any())).thenReturn(Optional.of(mockDoctor));

        Appointment mockAppointment = new Appointment();
        mockAppointment.setId(1L);
        when(appointmentRepository.findByIdAndDoctorId(any(), any())).thenReturn(Optional.of(mockAppointment));

        Patient mockPatient = new Patient();
        mockPatient.setId(1L);
        when(patientRepository.findById(any())).thenReturn(Optional.of(mockPatient));

        UpdateAppointmentRequest updateRequest = new UpdateAppointmentRequest();
        updateRequest.setPatientId(1L);
        updateRequest.setAppointmentTime(LocalDateTime.now());

        assertDoesNotThrow(() -> appointmentService.updateAppointment(1L, 1L, updateRequest));
    }

    @Test
    void updateAppointment_DoctorNotFound() {

        when(doctorRepository.findById(any())).thenReturn(Optional.empty());


        UpdateAppointmentRequest updateRequest = new UpdateAppointmentRequest();
        updateRequest.setPatientId(1L);
        updateRequest.setAppointmentTime(LocalDateTime.now());

        assertThrows(DoctorNotFoundException.class, () -> appointmentService.updateAppointment(1L, 1L, updateRequest));
    }

    @Test
    void updateAppointment_AppointmentNotFound() {

        Doctor mockDoctor = new Doctor();
        mockDoctor.setId(1L);
        when(doctorRepository.findById(any())).thenReturn(Optional.of(mockDoctor));

        Appointment mockAppointment = new Appointment();
        mockAppointment.setId(1L);
        when(appointmentRepository.findByIdAndDoctorId(any(), any())).thenReturn(Optional.empty());


        Patient mockPatient = new Patient();
        mockPatient.setId(1L);
        when(patientRepository.findById(any())).thenReturn(Optional.of(mockPatient));

        UpdateAppointmentRequest updateRequest = new UpdateAppointmentRequest();
        updateRequest.setPatientId(1L);
        updateRequest.setAppointmentTime(LocalDateTime.now());

        assertThrows(AppointmentNotFoundException.class, () -> appointmentService.updateAppointment(1L, 1L, updateRequest));
    }

    @Test
    void updateAppointment_PatientNotFound() {

        Doctor mockDoctor = new Doctor();
        mockDoctor.setId(1L);
        when(doctorRepository.findById(any())).thenReturn(Optional.of(mockDoctor));

        Appointment mockAppointment = new Appointment();
        mockAppointment.setId(1L);
        when(appointmentRepository.findByIdAndDoctorId(any(), any())).thenReturn(Optional.of(mockAppointment));

        Patient mockPatient = new Patient();
        mockPatient.setId(1L);
        when(patientRepository.findById(any())).thenReturn(Optional.empty());

        UpdateAppointmentRequest updateRequest = new UpdateAppointmentRequest();
        updateRequest.setPatientId(1L);
        updateRequest.setAppointmentTime(LocalDateTime.now());

        assertThrows(PatientNotFoundException.class, () -> appointmentService.updateAppointment(1L, 1L, updateRequest));
    }

    @Test
    void deleteAppointment_Success() {

        Appointment mockAppointment = new Appointment();
        mockAppointment.setId(1L);
        when(appointmentRepository.findById(any())).thenReturn(Optional.of(mockAppointment));


        assertDoesNotThrow(() -> appointmentService.deleteAppointment(1L));
    }

    @Test
    void deleteAppointment_AppointmentNotFound() {

        Appointment mockAppointment = new Appointment();
        mockAppointment.setId(1L);
        when(appointmentRepository.findById(any())).thenReturn(Optional.empty());


        assertThrows(AppointmentNotFoundException.class, () -> appointmentService.deleteAppointment(1L));
    }
}