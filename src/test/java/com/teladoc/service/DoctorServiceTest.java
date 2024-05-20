package com.teladoc.service;

import com.teladoc.model.*;
import com.teladoc.model.exception.DoctorNotFoundException;
import com.teladoc.repository.AppointmentRepository;
import com.teladoc.repository.DoctorRepository;
import com.teladoc.service.impl.DoctorServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class DoctorServiceTest {

    @Mock
    private DoctorRepository doctorRepository;

    @Mock
    private AppointmentRepository appointmentRepository;

    @InjectMocks
    private DoctorServiceImpl doctorService;

    @Test
    void getDoctorAvailability_Success() {

        Doctor doctor = new Doctor();
        Doctor mockDoctor = doctor;
        mockDoctor.setId(1L);
        List<WorkingHours> workingHours = new ArrayList<>();
        workingHours.add(new WorkingHours(LocalTime.now(), LocalTime.now().plusHours(1)));
        workingHours.add(new WorkingHours(LocalTime.now(), LocalTime.now().plusHours(1)));
        workingHours.add(new WorkingHours(LocalTime.now(), LocalTime.now().plusHours(1)));
        workingHours.add(new WorkingHours(LocalTime.now(), LocalTime.now().plusHours(1)));
        workingHours.add(new WorkingHours(LocalTime.now(), LocalTime.now().plusHours(1)));
        mockDoctor.setWorkingHours(workingHours);
        when(doctorRepository.findById(any())).thenReturn(Optional.of(mockDoctor));


        List<Appointment> mockAppointments = new ArrayList<>();

        LocalDateTime now = LocalDateTime.now();
        Patient patient = new Patient();
        mockAppointments.add(new Appointment(1L, doctor, now.with(DayOfWeek.MONDAY), now.with(DayOfWeek.MONDAY).plusHours(1), patient));
        mockAppointments.add(new Appointment(1L, doctor, now.with(DayOfWeek.TUESDAY), now.with(DayOfWeek.TUESDAY).plusHours(1), patient));
        when(appointmentRepository.findByDoctorAndStartTimeBetween(any(), any(), any())).thenReturn(mockAppointments);

        List<AvailableSlot> availableSlots = assertDoesNotThrow(() -> doctorService.getDoctorAvailability(1L));

        assertNotNull(availableSlots);
        assertEquals(5, availableSlots.size()); // Assuming doctor works on weekdays
    }

    @Test
    void getDoctorAvailability_DoctorNotFound() {

        when(doctorRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(DoctorNotFoundException.class, () -> doctorService.getDoctorAvailability(1L));
    }

    @Test
    void getDoctorById_DoctorNotFound() {

        when(doctorRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(DoctorNotFoundException.class, () -> doctorService.getDoctorById(1L));
    }

    @Test
    void getDoctorById_Success() {

        when(doctorRepository.findById(any())).thenReturn(Optional.of(new Doctor()));

        assertDoesNotThrow( () -> doctorService.getDoctorById(1L));
    }
}

